import munit.Assertions
import munit._
import spatial.PointInPolygon
import spatial.GeoPoint
import spatial.Edge
import models.Location
import spatial.Polygon
import models.Region
import logic.GeoProcessor

class GeoTests extends munit.FunSuite {
  test("rayIntersectsSegment - point is to the left of an edge") {
    val point  = GeoPoint(0, 0)
    val edge   = Edge(GeoPoint(1, 1), GeoPoint(1, -1))
    val result = PointInPolygon.rayIntersectsSegment(point.get, edge)

    assert(result)
  }

  test("rayIntersectsSegment - point is to the right of an edge") {
    val point  = GeoPoint(2, 0)
    val edge   = Edge(GeoPoint(1, 1), GeoPoint(1, -1))
    val result = PointInPolygon.rayIntersectsSegment(point.get, edge)

    assert(!result)
  }

  test("rayIntersectsSegment - point is above the edge") {
    val point  = GeoPoint(0, 2)
    val edge   = Edge(GeoPoint(1, 1), GeoPoint(1, -1))
    val result = PointInPolygon.rayIntersectsSegment(point.get, edge)

    assert(!result)
  }

  test("rayIntersectsSegment - point is below the edge") {
    val point  = GeoPoint(0, -2)
    val edge   = Edge(GeoPoint(1, 1), GeoPoint(1, -1))
    val result = PointInPolygon.rayIntersectsSegment(point.get, edge)

    assert(!result)
  }

  test("rayIntersectsSegment - point is on the starting edge vertex") {
    val point  = GeoPoint(0, 0)
    val edge   = Edge(GeoPoint(0, 0), GeoPoint(-2, -2))
    val result = PointInPolygon.rayIntersectsSegment(point.get, edge)

    assert(!result)
  }

  test("rayIntersectsSegment - point is on the end edge vertex") {
    val point  = GeoPoint(-2, -2)
    val edge   = Edge(GeoPoint(0, 0), GeoPoint(-2, -2))
    val result = PointInPolygon.rayIntersectsSegment(point.get, edge)

    assert(result)
  }

  test("rayIntersectsSegment - point very close to the edge (within epsilon)") {
    val point  = GeoPoint(0, -0.00001)
    val edge   = Edge(GeoPoint(0, 0), GeoPoint(-2, -2))
    val result = PointInPolygon.rayIntersectsSegment(point.get, edge)

    assert(!result)
  }

  test("rayIntersectsSegment - point on the diagonal edge") {
    val point  = GeoPoint(2, 2)
    val edge   = Edge(GeoPoint(1, 1), GeoPoint(3, 3))
    val result = PointInPolygon.rayIntersectsSegment(point.get, edge)

    assert(result)
  }

  test("rayIntersectsSegment - point on the horizontal edge") {
    val point  = GeoPoint(0, 0)
    val edge   = Edge(GeoPoint(-2, 0), GeoPoint(2, 0))
    val result = PointInPolygon.rayIntersectsSegment(point.get, edge)

    assert(!result)
  }

  test("rayIntersectsSegment - point on the vertical edge") {
    val point  = GeoPoint(0, 0)
    val edge   = Edge(GeoPoint(0, -2), GeoPoint(0, 2))
    val result = PointInPolygon.rayIntersectsSegment(point.get, edge)

    assert(!result)
  }

  test("rayIntersectsSegment - ray aligns with horizontal edge") {
    val point  = GeoPoint(0, 0)
    val edge   = Edge(GeoPoint(2, 0), GeoPoint(4, 0))
    val result = PointInPolygon.rayIntersectsSegment(point.get, edge)

    assert(!result)
  }

  test("rayIntersectsSegment - ray aligns with edge vertex") {
    val point  = GeoPoint(0, 0)
    val edge   = Edge(GeoPoint(2, 0), GeoPoint(4, 4))
    val result = PointInPolygon.rayIntersectsSegment(point.get, edge)

    assert(result)
  }

  test("rayCastingAlgorithm - square polygon, center as location") {
    val location = Location("location", GeoPoint(1, 1))
    val locList  = List[Location](location)

    val bottomLeft  = GeoPoint(0, 0).get
    val topLeft     = GeoPoint(0, 2).get
    val topRight    = GeoPoint(2, 2).get
    val bottomRight = GeoPoint(2, 0).get

    val edges = Seq(
      Edge(bottomLeft, topLeft),
      Edge(topLeft, topRight),
      Edge(topRight, bottomRight),
      Edge(bottomRight, bottomLeft)
    )
    val polygon = Polygon("square", edges)

    val results  = PointInPolygon.rayCastingAlgorithm(locList, polygon)
    val expected = List[String]("location")

    assertEquals(results, expected)
  }

  test("rayCastingAlgorithm - diamond polygon, bottom vertex as location") {
    val location = Location("location", GeoPoint(0, -2))
    val locList  = List[Location](location)

    val left   = GeoPoint(-1, 0).get
    val top    = GeoPoint(0, 2).get
    val right  = GeoPoint(1, 0).get
    val bottom = GeoPoint(0, -2).get

    val edges = Seq(
      Edge(left, top),
      Edge(top, right),
      Edge(right, bottom),
      Edge(bottom, left)
    )
    val polygon = Polygon("diamond", edges)

    val results  = PointInPolygon.rayCastingAlgorithm(locList, polygon)
    val expected = List[String]("location")

    assertEquals(results, expected)
  }

  test("toPolygons - two valid polygons creation") {
    val region = Region(
      name = "TestRegion",
      coordinates = List(
        List(
          GeoPoint(0.0, 0.0),
          GeoPoint(1.0, 0.0),
          GeoPoint(0.5, 1.0),
          GeoPoint(0.0, 0.0)
        ),
        List(
          GeoPoint(2.0, 2.0),
          GeoPoint(4.0, 2.0),
          GeoPoint(4.0, 4.0),
          GeoPoint(2.0, 4.0),
          GeoPoint(2.0, 2.0)
        )
      )
    )

    val expected: List[Polygon] = List(
      Polygon(
        "TestRegion",
        List(
          Edge(GeoPoint(0.0, 0.0), GeoPoint(1.0, 0.0)),
          Edge(GeoPoint(1.0, 0.0), GeoPoint(0.5, 1.0)),
          Edge(GeoPoint(0.5, 1.0), GeoPoint(0.0, 0.0))
        )
      ),
      Polygon(
        "TestRegion",
        List(
          Edge(GeoPoint(2.0, 2.0), GeoPoint(4.0, 2.0)),
          Edge(GeoPoint(4.0, 2.0), GeoPoint(4.0, 4.0)),
          Edge(GeoPoint(4.0, 4.0), GeoPoint(2.0, 4.0)),
          Edge(GeoPoint(2.0, 4.0), GeoPoint(2.0, 2.0))
        )
      )
    )

    val result = GeoProcessor.toPolygons(region)

    assertEquals(result, expected)
  }

  test("toPolygons - two invalid polygons") {
    val region = Region(
      name = "TestRegion",
      coordinates = List(
        List(
          GeoPoint(0.0, 0.0),
          GeoPoint(1.0, 0.0),
          GeoPoint(0.5, 1.0)
        ),
        List(
          GeoPoint(2.0, 2.0),
          GeoPoint(4.0, 2.0),
          GeoPoint(4.0, 4.0),
          GeoPoint(2.0, 4.0)
        )
      )
    )

    val expected: List[Polygon] = List[Polygon]()

    val result = GeoProcessor.toPolygons(region)

    assertEquals(result, expected)
  }

}
