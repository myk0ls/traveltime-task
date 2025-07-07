package models
import upickle.default._
import spatial.GeoPoint

case class Region(name: String, coordinates: List[List[GeoPoint]])
  object Region {
    implicit val reader: ReadWriter[Region] = upickle.default.macroRW[Region]
  }