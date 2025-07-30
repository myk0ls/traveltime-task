package logic

import scala.util.Try
import scala.util.Success
import scala.util.Failure

case class JsonContent(val content: String)

object JsonContent {
  def apply(content: String): Option[JsonContent] = {
    if (!content.isEmpty)
      JsonContent(content)
    else
      None
  }
}
