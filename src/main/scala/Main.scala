import spatial._
import upickle.default._
import os._
import models._
import logic._
import scala.util.Success
import scala.util.Failure
import java.awt.image.ImagingOpException
import scala.util.Try

object Main extends App {
  // println(s"Raw args: ${args.mkString(" | ")}")
  val conf = new Config(args)

  val argsResult = for {
    locationArg <- Try(Path(conf.locations(), os.pwd))
    regionArg   <- Try(Path(conf.regions(), os.pwd))
    outputArg   <- Try(Path(conf.output(), os.pwd))
  } yield {
    Seq[Path](locationArg, regionArg, outputArg)
  }

  argsResult match {
    case Success(arguments) =>
      coreLogic(arguments)
    case Failure(exception) =>
      println(s"Invalid arguments: ${exception.getMessage}")
  }

  def coreLogic(arguments: Seq[Path]): Unit = {
    val result = for {
      locJson   <- IOProcessor.readJson(arguments(0))
      regJson   <- IOProcessor.readJson(arguments(1))
      locations <- IOProcessor.decodeJson[Location](locJson)
      regions   <- IOProcessor.decodeJson[Region](regJson)
    } yield {
      GeoProcessor.process(locations, regions)
    }

    result match {
      case Success(processed) =>
        val endResult = for {
          encoding <- IOProcessor.encodeJson(processed)
          writing  <- IOProcessor.writeJson(arguments(2), encoding)
        } yield writing

        endResult match {
          case Success(value) =>
            println(s"Tasks done successfuly!")
          case Failure(exception) =>
            println(s"Encoding/Writing failed: ${exception.getMessage}")
        }
      case Failure(exception) =>
        println(s"Processing failed: ${exception.getMessage}")
    }
  }
}
