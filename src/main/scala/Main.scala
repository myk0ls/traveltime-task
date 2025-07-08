import spatial.GeoPoint
import upickle.default._
import os._
import models._
import logic._
import logic.models.GeoProcessor

object Main extends App{

  if (args.length == 3) {
    val locationsJson = IOProcessor.readJson(args(0))
    val locations = IOProcessor.decodeJson[Location](locationsJson)

    val regionsJson = IOProcessor.readJson(args(1))
    val regions = IOProcessor.decodeJson[Region](regionsJson)

    val results = GeoProcessor.process(locations, regions)
    
    val resultsJson = IOProcessor.encodeJson[Result](results)
    val write = IOProcessor.writeJson(args(2), resultsJson)
  } 
  else
    println("Invalid arguments")
}