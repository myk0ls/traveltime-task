package spatial
import upickle.default._
import models._

final case class GeoPoint (coordinates: (Double, Double))

object GeoPoint {
    //implicit val reader: ReadWriter[GeoPoint] = upickle.default.macroRW[GeoPoint]

    implicit val rw: ReadWriter[GeoPoint] = readwriter[ujson.Value].bimap[GeoPoint](
      //serialize
      gp => ujson.Arr(gp.coordinates._1, gp.coordinates._2),
      //deserialize
      json => GeoPoint((json(0).num), json(1).num)
    )

    def apply(longitude: Double, latitude: Double): Option[GeoPoint] = {
        if (validateLongitude(longitude) &&
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



