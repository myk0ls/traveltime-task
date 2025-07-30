package logic

import upickle.default._
import os._
import models._
import scala.util.Try
import scala.util.Success
import scala.util.Failure

object IOProcessor {
  def readJson(filePath: Path): Try[String] = {
    val result = Try(os.read(filePath))

    result match {
      case Success(value) if value.isEmpty =>
        Failure(new RuntimeException("Empty File"))
      case Success(value) =>
        Success(value)
      case Failure(exception) =>
        Failure(exception)
    }
  }

  def decodeJson[A: ReadWriter](jsonStr: String): Try[List[A]] = {
    val result = Try(upickle.default.read[List[A]](ujson.read(jsonStr)))

    result
  }

  def encodeJson[A: ReadWriter](data: List[A]): Try[String] = {
    val result = Try(upickle.default.write(data))

    result
  }

  def writeJson(filePath: Path, data: String): Try[Unit] = {
    val result = Try(os.write.over(filePath, data))

    result
  }
}
