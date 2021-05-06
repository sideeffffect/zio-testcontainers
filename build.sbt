Global / onChangedBuildSource := ReloadOnSourceChanges
ThisBuild / turbo := true

lazy val zioTestcontainers = project
  .in(file("."))
  .settings(commonSettings)
  .settings(
    name := "zio-testcontainers",
    libraryDependencies ++= List(
      Dependencies.zio,
      Dependencies.testcontainers
    )
  )

lazy val commonSettings: List[Def.Setting[_]] = DecentScala.decentScalaSettings ++ List(
  organization := "com.github.sideeffffect",
  homepage := Some(url("https://github.com/sideeffffect/ideal-voting-backend")),
  licenses := List("APLv2" -> url("https://www.apache.org/licenses/LICENSE-2.0")),
  developers := List(
    Developer(
      "sideeffffect",
      "Ondra Pelech",
      "ondra.pelech@gmail.com",
      url("https://github.com/sideeffffect")
    )
  ),
  crossScalaVersions := List(
    DecentScala.decentScalaVersion213,
    DecentScala.decentScalaVersion212,
    DecentScala.decentScalaVersion211
  ),
  testFrameworks += new TestFramework("zio.test.sbt.ZTestFramework"),
  missinglinkExcludedDependencies ++= List(
    moduleFilter(organization = "org.slf4j", name = "slf4j-api"),
    moduleFilter(organization = "org.testcontainers", name = "testcontainers")
  ),
  mimaReportBinaryIssues := {}
)

addCommandAlias("ci", "; check; +publishLocal")
