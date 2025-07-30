package spatial
import upickle.default._
import models._

case class GeoPoint(lng: Double, lat: Double) {
  def ===(that: GeoPoint): Boolean = {
    lng == that.lng && lat == that.lat
  }
}

object GeoPoint {
  implicit val rw: ReadWriter[GeoPoint] = readwriter[ujson.Value].bimap[GeoPoint](
    // serialize
    gp => ujson.Arr(gp.lng, gp.lat),
    // deserialize
    json => GeoPoint.create(json(0).num, json(1).num).getOrElse(GeoPoint(0, 0))
  )

  def create(longitude: Double, latitude: Double): Option[GeoPoint] = {
    if (
      validateLongitude(longitude) &&
      validateLatitude(latitude)
    ) Some(GeoPoint(longitude, latitude))
    else
      None
  }

  def validateLongitude(longitude: Double): Boolean = {
    if (longitude >= -180 && longitude <= 180) true
    else
      false
  }

  def validateLatitude(latitude: Double): Boolean = {
    if (latitude >= -90 && latitude <= 90) true
    else
      false
  }
}
