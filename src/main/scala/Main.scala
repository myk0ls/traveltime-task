import spatial._
import upickle.default._
import os._
import models._
import logic._
import scala.util.Success
import scala.util.Failure
import java.awt.image.ImagingOpException

object Main extends App {
  //println(s"Raw args: ${args.mkString(" | ")}")
  val conf = new Config(args)

  val result = for {
    locJson <- IOProcessor.readJson(conf.locations())
    regJson <- IOProcessor.readJson(conf.regions())
    locations <- IOProcessor.decodeJson[Location](locJson)
    regions <- IOProcessor.decodeJson[Region](regJson)
  } yield {
    GeoProcessor.process(locations, regions)
  }

  result match {
    case Success(processed) =>
      IOProcessor.writeJson(conf.output(), IOProcessor.encodeJson(processed).get)
    case Failure(e) =>
      println(s"Processing failed: ${e.getMessage}")
  }
}