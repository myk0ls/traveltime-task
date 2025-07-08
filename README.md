# Location Region Matcher

A simple Scala 2 project to count how many geographic locations fall within each region defined by polygons.

## Run the Project

Make sure you have [sbt](https://www.scala-sbt.org/) installed.

### Run:

```bash
sbt
run input/locations.json input/regions.json output/results.json

//first arg location file
//second arg region file
//third arg result output