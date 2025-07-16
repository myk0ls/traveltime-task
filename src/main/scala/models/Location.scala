package models
import upickle.default._
import spatial.GeoPoint

case class Location(name: String, coordinates: GeoPoint)
object Location {
  implicit val reader: ReadWriter[Location] = upickle.default.macroRW[Location]
}
