package logic

import upickle.default._
import os._
import models._

object IOProcessor {
  def readJson(filePath: String): String = {
    val path = Path(filePath, os.pwd)
    os.read(path)
  }
  
  def decodeJson[A: Reader](jsonStr: String): List[A] = {
    upickle.default.read[List[A]](ujson.read(jsonStr))
  }


  // todo
  //def writeResults(filePath: String): List[Result]
}
