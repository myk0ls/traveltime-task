package spatial

import models._
import Math._
import Double._

object PointInPolygon {
  def rayCastingAlgorithm(locations: List[Location], polygon: Polygon) = {
    val insideLocationNames = locations.flatMap { location =>
      val intersections = polygon.edges.count(edge => rayIntersectsSegment(location.coordinates, edge))
      val isInside = intersections % 2 == 1
      //println(location.name + " inside??: " + isInside)

      if (isInside) Some(location.name) else None
    }

    insideLocationNames
  }

  def rayIntersectsSegment(point: GeoPoint, edge: Edge): Boolean = {
    //check the y's, if not bottom to top, reverse the edges
    if (edge._1.coordinates._2 > edge._2.coordinates._2) 
      return rayIntersectsSegment(point, Edge(edge._2, edge._1))

    // if ray on vertex, move it using epsilon
    if (point.coordinates._2 == edge._1.coordinates._2 || point.coordinates._2 == edge._2.coordinates._2) 
      return rayIntersectsSegment(GeoPoint(point.coordinates._1, point.coordinates._2 + epsilon), edge)
    
    //ray below or above
    if (point.coordinates._2 < edge._1.coordinates._2 || point.coordinates._2 > edge._2.coordinates._2) 
      return false

    //if point is to the right of polyg
    if (point.coordinates._1 >= max(edge._1.coordinates._1, edge._2.coordinates._1)) 
      return false

    //if point is to the left
    if (point.coordinates._1 < min(edge._1.coordinates._1, edge._2.coordinates._1)) 
      return true

    //otherwise calculate and compare slopes
    val blue = 
      if (abs(edge._1.coordinates._1 - point.coordinates._1) > MinValue) 
        (point.coordinates._2 - edge._1.coordinates._2) / (point.coordinates._1 - edge._1.coordinates._1) 
      else MaxValue
    val red = 
      if (abs(edge._1.coordinates._1 - edge._2.coordinates._1) > MinValue) 
        (edge._2.coordinates._2 - edge._1.coordinates._2) / (edge._2.coordinates._1 - edge._1.coordinates._1) 
      else MaxValue
    blue >= red
  }

  final val epsilon = 0.00001
}
