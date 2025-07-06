import upickle.default._
import os._
import models._
import logic.IOProcessor

object Main extends App {
  val locationsJson = IOProcessor.readJson("src/main/resources/input/locations.json")
  val locations = IOProcessor.decodeJson[Location](locationsJson)

  val regionsJson = IOProcessor.readJson("src/main/resources/input/regions.json")
  val regions = IOProcessor.decodeJson[Region](regionsJson)
  
  locations.foreach(println)
  regions.foreach(println)

}