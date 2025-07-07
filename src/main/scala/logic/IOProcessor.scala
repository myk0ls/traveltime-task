package logic

import upickle.default._
import os._
import models._

object IOProcessor {
  def readJson(filePath: String): String = {
    val path = Path(filePath, os.pwd)
    os.read(path)
  }

  def decodeJson[A: ReadWriter](jsonStr: String): List[A] = {
    upickle.default.read[List[A]](ujson.read(jsonStr))
  }

  def encodeJson[A: ReadWriter](data: List[A]): String = {
    upickle.default.write(data)
  }

  def writeJson(filePath: String, data: String): Unit = {
    val path = Path(filePath, os.pwd)
    os.write(path, data)
  }
}
