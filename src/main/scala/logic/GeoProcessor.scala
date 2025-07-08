package logic
package models

import _root_.models.Region
import spatial.Polygon
import _root_.models.Location
import spatial.GeoPoint
import spatial.Edge
import spatial._

object GeoProcessor {
  def process(locations: List[Location], regions: List[Region]) = {
    val polygons = regions.flatMap(toPolygons)

    val results = PointInPolygon.rayCastingAlgorithm(locations, polygons)
    results
  } 
  
  def toPolygons(region: Region): List[Polygon] = {
    region.coordinates.map(polygon => toPolygon(region.name, polygon))
  }

  def toPolygon(name: String, points: List[GeoPoint]): Polygon = {
    val edges = points.zip(points.tail :+ points.head).map { case (a, b) => Edge(a, b) }
    val polygon = Polygon(name, edges)

    //println("name of polygon: " + polygon.name)
    //polygon.edges.foreach(println)

    polygon
  }
}
