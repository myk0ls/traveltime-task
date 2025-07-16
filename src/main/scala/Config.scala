import org.rogach.scallop.ScallopConf

class Config(arguments: Seq[String]) extends ScallopConf(arguments) {
  val locations = opt[String](required = true)
  val regions = opt[String](required = true)
  val output = opt[String](required = true)
  verify()
}