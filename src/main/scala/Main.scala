import spatial.GeoPoint
import upickle.default._
import os._
import models._
import logic._
import spatial.PointInPolygon

object Main extends App {
  val locationsJson = IOProcessor.readJson("src/main/resources/input/locations.json")
  val locations = IOProcessor.decodeJson[Location](locationsJson)

  val regionsJson = IOProcessor.readJson("src/main/resources/input/regions.json")
  val regions = IOProcessor.decodeJson[Region](regionsJson)
  
  locations.foreach(println)
  regions.foreach(println)

  //PointInPolygon.rayCastingAlgorithm(locations, regions)

  val testy = GeoPoint(-180, 89.21564)
  //println(testy)

  // testy match {
  //   case Some(value) =>
  //     val (lon, lat) = value.coordinates
  //     println(s"Longitude: $lon, Latitude: $lat")

  //   case None => 
  //     println("Invalid coordinates!")
  // }

  // val regionTest = IOProcessor.encodeJson[Region](regions)
  // val result = IOProcessor.writeJson("src/main/resources/output/regionsTest.json", regionTest)

}