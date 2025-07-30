package logic

import models._
import spatial._
import _root_.models._
import scala.util.Try

object GeoProcessor {
  def process(locations: List[Location], regions: List[Region]) = {
    val polygons = regions.flatMap(toPolygons).flatten

    val results: List[Result] = regions.map { region =>
      val insideLocations = polygons
        .filter(p => p.name == region.name)
        .flatMap(p => PointInPolygon.rayCastingAlgorithm(locations, p))
        .distinct

      Result(region.name, insideLocations)
    }

    results
  }

  def toPolygons(region: Region): List[Option[Polygon]] = {
    region.coordinates
      .map(area => toPolygon(region.name, area))
  }

  def toPolygon(name: String, area: GeoArea): Option[Polygon] = {
    val edges   = area.points.zip(area.points.tail).map { case (a, b) => Edge(a, b) }
    val polygon = Polygon.create(name, edges)

    polygon
  }
}
