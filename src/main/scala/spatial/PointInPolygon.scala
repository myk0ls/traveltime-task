package spatial

import models._


case class Edge(_1: (Double, Double), _2: (Double, Double)) {
    import Math._
    import Double._

    def raySegI(p: (Double, Double)): Boolean = {
        if (_1._2 > _2._2) return Edge(_2, _1).raySegI(p)
        if (p._2 == _1._2 || p._2 == _2._2) return raySegI((p._1, p._2 + epsilon))
        if (p._2 > _2._2 || p._2 < _1._2 || p._1 > max(_1._1, _2._1)) return false
        if (p._1 < min(_1._1, _2._1)) return true
        val blue = if (abs(_1._1 - p._1) > MinValue) (p._2 - _1._2) / (p._1 - _1._1) else MaxValue
        val red = if (abs(_1._1 - _2._1) > MinValue) (_2._2 - _1._2) / (_2._1 - _1._1) else MaxValue
        blue >= red
    }

    val epsilon = 0.00001
}

case class Figure(name: String, edges: Seq[Edge]) {
    def contains(p: (Double, Double)) = edges.count(_.raySegI(p)) % 2 != 0
}

object PointInPolygon {
  def rayCastingAlgorithm(locations: List[Location], regions: List[Region]) {
    // val points: Seq[(Double, Double)] = locations.map(_.coordinates)

    // val figures: Seq[Figure] = regions.map(regionToFigure)

    
    // for (f <- figures) {
    //     println("figure: " + f.name)
    //     println("        " + f.edges)
    //     println("result: " + (points map f.contains))
    // }
  }

  // Converts a polygon (List of points) into edges
  // def polygonToEdges(points: List[(Double, Double)]): Seq[Edge] = {
  //   points.zip(points.tail :+ points.head).map { case (a, b) => Edge(a, b) }
  // }

  // // Convert Region to Figure
  // def regionToFigure(region: models.Region): Figure = {
  //   val allEdges = region.coordinates.flatMap(polygonToEdges)
  //   Figure(region.name, allEdges)
  // }
}
