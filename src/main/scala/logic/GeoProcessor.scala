package logic

import models._
import spatial.Polygon
import spatial.GeoPoint
import spatial.Edge
import spatial._
import _root_.models._

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
  }

  def toPolygon(name: String, points: List[GeoPoint]): Polygon = {
    val edges = points.zip(points.tail :+ points.head).map { case (a, b) => Edge(a, b) }
    val polygon = Polygon(name, edges)

    polygon
  }
}
