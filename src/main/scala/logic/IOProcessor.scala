package logic

import upickle.default._
import os._
import models._
import scala.util.Try
import scala.util.Success
import scala.util.Failure

object IOProcessor {
  def readJson(filePath: String): Try[String] = {
    val result = Try(os.read(Path(filePath, os.pwd)))

    result match {
      case Success(value) if value.isEmpty =>
        println("File is empty")
        Failure(new RuntimeException("Empty File"))
      case Success(value) =>
        Success(value)
      case Failure(exception) =>
        println("Error reading JSON file")
        Failure(exception)
    }
  }

  def decodeJson[A: ReadWriter](jsonStr: String): Try[List[A]] = {
    val result = Try(upickle.default.read[List[A]](ujson.read(jsonStr)))

    result match {
      case Success(value) =>
        Success(value)
      case Failure(exception) =>
        println("Error decoding JSON")
        Failure(exception)
    }
  }

  def encodeJson[A: ReadWriter](data: List[A]): Try[String] = {
    val result = Try(upickle.default.write(data))

    result match {
      case Success(value) =>
        Success(value)
      case Failure(exception) =>
        println("Error encoding JSON")
        Failure(exception)
    }
  }

  def writeJson(filePath: String, data: String): Try[Unit] = {
    val result = Try(os.write.over(Path(filePath, os.pwd), data))

    result match {
      case Success(value) =>
        Success(())
      case Failure(exception) =>
        println("Error writing data")
        Failure(exception)
    }
  }
}
