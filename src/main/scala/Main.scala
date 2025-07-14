import spatial._
import upickle.default._
import os._
import models._
import logic._

object Main extends App{
  if (args.length == 3) {
    val locationsJson = IOProcessor.readJson(args(0))

    val locations = IOProcessor.decodeJson[Location](locationsJson.getOrElse(""))

    val regionsJson = IOProcessor.readJson(args(1))
    val regions = IOProcessor.decodeJson[Region](regionsJson.getOrElse(""))

    val results = GeoProcessor.process(locations.get, regions.get)
    
    val resultsJson = IOProcessor.encodeJson[Result](results)
    IOProcessor.writeJson(args(2), resultsJson.get)
  } 
  else
    println("Invalid arguments")
}