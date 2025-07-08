import spatial.GeoPoint
import upickle.default._
import os._
import models._
import logic._
import logic.models.GeoProcessor

object Main extends App {
  val locationsJson = IOProcessor.readJson("src/main/resources/input/locations.json")
  val locations = IOProcessor.decodeJson[Location](locationsJson)

  val regionsJson = IOProcessor.readJson("src/main/resources/input/regions.json")
  val regions = IOProcessor.decodeJson[Region](regionsJson)

  val results = GeoProcessor.process(locations, regions)
  
  val resultsJson = IOProcessor.encodeJson[Result](results)
  val write = IOProcessor.writeJson("src/main/resources/output/resultsTest.json", resultsJson)
}