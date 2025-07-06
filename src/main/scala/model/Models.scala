import upickle.default._

package object models {
  case class Location(name: String, coordinates: (Double, Double))
  object Location {
    implicit val reader: Reader[Location] = upickle.default.macroR[Location]
  }

  case class Region(name: String, coordinates: List[List[(Double, Double)]])
  object Region {
    implicit val reader: Reader[Region] = upickle.default.macroR[Region]
  }
}
