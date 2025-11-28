import sbt._

object Dependencies {

  object Versions {
    val zio = "2.1.23"
    val testcontainers = "2.0.2"
  }

  val zio = "dev.zio" %% "zio" % Versions.zio
  val testcontainers = "org.testcontainers" % "testcontainers" % Versions.testcontainers

}
