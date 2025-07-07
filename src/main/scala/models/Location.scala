package models
import upickle.default._
import spatial.GeoPoint

case class Location(name: String, coordinates: GeoPoint)
  object Location {
    implicit val reader: ReadWriter[Location] = upickle.default.macroRW[Location]

    // implicit val readWriter: ReadWriter[Location] = readwriter[ujson.Value]
    //   .bimap[Location](
    //     x => ujson.Arr(x.name, x.name.),
    //     json => new Location(json(1).str, GeoPoint(json(2).num, json(3).num))
    //   )

  }