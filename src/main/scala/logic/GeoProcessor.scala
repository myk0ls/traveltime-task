package logic

import models._
import spatial._
import _root_.models._
import scala.util.Try

object GeoProcessor {
  def process(locations: List[Location], regions: List[Region]) = {
    val polygons = regions.flatMap(toPolygons)

    val results: List[Result] = regions.map { region =>
      val insideLocations = polygons
        .filter(p => p.name == region.name)
        .flatMap(p => PointInPolygon.rayCastingAlgorithm(locations, p))
        .distinct

      Result(region.name, insideLocations)
    }

    results
  }

  def toPolygons(region: Region): List[Polygon] = {
    region.coordinates.map(polygon => toPolygon(region.name, polygon))
      .filter(polygon => polygon.edges.size >= 3)
      .filter(isClosed)
  }

  def isClosed(polygon: Polygon) = {
    polygon.edges.head.pointA.coordinates == polygon.edges.last.pointB.coordinates
  }

  def toPolygon(name: String, points: List[GeoPoint]) = {
    val edges   = points.zip(points.tail).map { case (a, b) => Edge(a, b) }
    val polygon = Polygon(name, edges)

    polygon
  }
}
