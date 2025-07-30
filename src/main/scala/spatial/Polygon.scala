package spatial

case class Polygon(name: String, edges: Seq[Edge])

object Polygon {
  def create(name: String, edges: Seq[Edge]): Option[Polygon] = {
    if (edges.size >= 3 && isClosed(edges)) {
      Some(Polygon(name, edges))
    } else
      None
  }

  def isClosed(edges: Seq[Edge]): Boolean = {
    edges.head.pointA.===(edges.last.pointB)
  }
}
