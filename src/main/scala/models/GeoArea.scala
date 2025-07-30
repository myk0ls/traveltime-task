package models
import upickle.default._
import spatial.GeoPoint

case class GeoArea(points: List[GeoPoint])
object GeoArea {
  implicit val rw: ReadWriter[GeoArea] = readwriter[ujson.Value].bimap[GeoArea](
    ga => ujson.Arr(ga.points.map(point => ujson.Arr(point.lng, point.lat))),
    json => GeoArea(json.arr.map(json => GeoPoint.create(json(0).num, json(1).num)).toList.flatten)
  )
}
