package spatial

import models._
import Math._
import Double._

object PointInPolygon {
  def rayCastingAlgorithm(locations: List[Location], polygon: Polygon) = {
    val insideLocationNames = locations.flatMap { location =>
      val intersections =
        polygon.edges.count(edge => rayIntersectsSegment(location.coordinates, edge))
      val isInside = intersections % 2 == 1

      if (isInside) Some(location.name) else None
    }

    insideLocationNames
  }

  def rayIntersectsSegment(point: GeoPoint, edge: Edge): Boolean = {
    val (pX, pY) = (point.lng, point.lat)
    val (aX, aY) = (edge.pointA.lng, edge.pointA.lat)
    val (bX, bY) = (edge.pointB.lng, edge.pointB.lat)

    // check if the edge is valid by checking if it has length
    if (edge.pointA.===(edge.pointB))
      return false

    // doing a check for edge's pointA and pointB y property,
    // to ensure proper calculation by rearranging them
    if (aY > bY)
      return rayIntersectsSegment(point, Edge(edge.pointB, edge.pointA))

    // if point position is in on a vertex, we do a check to see if it aligns with the top or
    // bottom of the edge, incase its the top, we return false,
    // if its a bottom - we move it upwards to hit any edges.
    // this way we're counting only bottom vertex only and dismissing the top.
    if (pY == aY || pY == bY) {
      val upperY = max(aY, bY)
      if (pY == upperY)
        return false
      else
        return rayIntersectsSegment(GeoPoint(pX, pY + epsilon), edge)
    }

    // checking if the point is above or below the edge's vertices
    if (pY < aY || pY > bY)
      return false

    // checking if the point is to the right of the edge
    if (pX >= max(aX, bX))
      return false

    // Horizontal edge, if pointA.y is equal to pointB.y means that it could lie along the ray
    if (aY == bY)
      return false

    // if point is to the left of the edge
    if (pX < min(aX, bX))
      return true

    // otherwise calculate and compare slopes
    val blue =
      if (abs(aX - pX) > MinValue)
        (pY - aY) / (pX - aX)
      else MaxValue
    val red =
      if (abs(aX - bX) > MinValue)
        (bY - aY) / (bX - aX)
      else MaxValue
    blue >= red
  }

  final val epsilon = 0.00001
}
