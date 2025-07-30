import munit.Assertions
import munit._
import models.Location
import os.Path
import logic.IOProcessor
import models.Region
import models.Result

class IOTests extends munit.FunSuite {
  val testPath: Path = Path("src/test/scala/", os.pwd)

  test("readJson - correct file") {
    val fileContent: String = "This is a test!"
    val tempDir: Path       = os.temp.dir(testPath)
    val testFile: Path      = os.temp(fileContent, tempDir)

    val result = IOProcessor.readJson(testFile)

    os.remove.all(tempDir)

    assertEquals(result.getOrElse(""), fileContent)
  }

  test("readJson - non existant file") {
    val testFile: Path = Path("file123", testPath)
    val result         = IOProcessor.readJson(testFile)

    assert(result.isFailure)
  }

  test("readJson - empty file") {
    val tempDir: Path  = os.temp.dir(testPath)
    val testFile: Path = os.temp("", tempDir)

    val result = IOProcessor.readJson(testFile)

    os.remove.all(tempDir)

    assert(result.isFailure)
  }

  test("readJson - directory instead of file") {
    val tempDir: Path = os.temp.dir(testPath)

    val result = IOProcessor.readJson(tempDir)

    os.remove.all(tempDir)

    assert(result.isFailure)
  }

  test("decodeJson - valid Location JSON") {
    val data   = """[{"name": "location1","coordinates": [25.21051562929364,54.64057937965808]}]"""
    val result = IOProcessor.decodeJson[Location](data)

    assert(result.isSuccess)
  }

  test("decodeJson - valid Region JSON") {
    val data   = """[{"name":"region1","coordinates":[[[25.1,54.6],[25.2,54.7],[25.3,54.8]]]}]"""
    val result = IOProcessor.decodeJson[Region](data)

    assert(result.isSuccess)
  }

  test("decodeJson - invalid JSON (Location)") {
    val data   = """[{"name": "location1","coordinates":54.64057937965808}]"""
    val result = IOProcessor.decodeJson[Location](data)

    assert(result.isFailure)
  }

  test("encodeJson - valid Result data") {
    val data   = List[Result](Result("region1", List[String]("location1", "location2")))
    val result = IOProcessor.encodeJson[Result](data)

    val expected = """[{"region":"region1","matchedLocations":["location1","location2"]}]"""
    assertEquals(result.getOrElse(""), expected)
  }

  test("encodeJson - empty Result matchedLocations") {
    val data   = List[Result](Result("region1", List[String]()))
    val result = IOProcessor.encodeJson[Result](data)

    val expected = """[{"region":"region1","matchedLocations":[]}]"""
    assertEquals(result.getOrElse(""), expected)
  }

  test("writeJson - correct path") {
    val tempDir: Path  = os.temp.dir(testPath)
    val testFile: Path = os.temp("", tempDir)

    val result = IOProcessor.writeJson(testFile, "This is a test!")

    os.remove.all(tempDir)

    assert(result.isSuccess)
  }

  test("writeJson - invalid path") {
    val result = IOProcessor.writeJson("/invalid/\\path/data.json", "This is a test!")
    assert(result.isFailure)
  }
}
