import spatial._
import upickle.default._
import os._
import models._
import logic._
import scala.util.Success
import scala.util.Failure
import java.awt.image.ImagingOpException


// object Main extends App{
//   if (args.length == 3) {
//     val locationsJson = IOProcessor.readJson(args(0))

//     val locations = IOProcessor.decodeJson[Location](locationsJson.getOrElse(""))

//     val regionsJson = IOProcessor.readJson(args(1))
//     val regions = IOProcessor.decodeJson[Region](regionsJson.getOrElse(""))

//     val results = GeoProcessor.process(locations.get, regions.get)
    
//     val resultsJson = IOProcessor.encodeJson[Result](results)
//     IOProcessor.writeJson(args(2), resultsJson.get)
//   } 
//   else
//     println("Invalid arguments")
// }

// object Main extends App{
//   if (args.length != 3) {
//     println("Invalid parameters")
//     System.exit(1)
//   }

//   val locationsJson = IOProcessor.readJson(args(0))
//   val regionsJson = IOProcessor.readJson(args(1))

//   (locationsJson, regionsJson) match {
//     case (Success(c1), Success(c2)) =>
//       val locations = IOProcessor.decodeJson[Location](locationsJson.getOrElse(""))

//       val regions = IOProcessor.decodeJson[Region](regionsJson.getOrElse(""))

//       val results = GeoProcessor.process(locations.get, regions.get)
      
//       val resultsJson = IOProcessor.encodeJson[Result](results)
//       IOProcessor.writeJson(args(2), resultsJson.get)
//     case (Failure(e1), Failure(e2)) =>
//       println(e1.getMessage())
//       println(e2.getMessage())
//       Failure(e1)
//     case (Success(c1), Failure(e2)) =>
//       println(e2.getMessage())
//       Failure(e2)
//     case (Failure(e1), Success(c2)) =>
//       println(e1.getMessage())
//       Failure(e1)
//   }
// }

object Main extends App {
  //println(s"Raw args: ${args.mkString(" | ")}")
  val conf = new Config(args)

  val locationsJson = IOProcessor.readJson(conf.locations())
  val regionsJson = IOProcessor.readJson(conf.regions())

  val locations = locationsJson.map(IOProcessor.decodeJson[Location]).toOption
  val regions = regionsJson.map(IOProcessor.decodeJson[Region]).toOption

  (locationsJson, regionsJson) match {
    case (Success(locJson), Success(regJson)) =>
      for {
        locations <- IOProcessor.decodeJson[Location](locJson)
        regions <- IOProcessor.decodeJson[Region](regJson)
        results = GeoProcessor.process(locations, regions)
        resultJson <- IOProcessor.encodeJson[Result](results)
        _ <- IOProcessor.writeJson(conf.output(), resultJson)
      } yield ()

    case (Failure(e), _) => println(s"Location file error: ${e.getMessage}")
    case (_ , Failure(e)) => println(s"Region file error: ${e.getMessage}")
  }
}