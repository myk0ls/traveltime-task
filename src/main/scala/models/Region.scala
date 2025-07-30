package models
import upickle.default._
import spatial.GeoPoint
import models.GeoArea

case class Region(name: String, coordinates: List[GeoArea])
object Region {
  implicit val reader: ReadWriter[Region] = upickle.default.macroRW[Region]
}
