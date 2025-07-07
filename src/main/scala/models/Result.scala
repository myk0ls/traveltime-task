package models
import upickle.default._

case class Result(region: String, matchedLocations: List[String])
  object Result {
    implicit val readWriter: ReadWriter[Result] = upickle.default.macroRW[Result]
  }