package logic

import scala.util.Try
import os._
import scala.util.Success
import scala.util.Failure

case class PathFile(path: String)

object PathFile {
  def apply(path: String): Option[PathFile] = {
    if (
      !path.isEmpty &&
      os.exists(Path(path, os.pwd))
    )
      PathFile(path)
    else
      None
  }
}
