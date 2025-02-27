import sbt._

object Dependencies {

  object Versions {
    val zio = "2.1.16"
    val testcontainers = "0.41.8"
  }

  val zio = "dev.zio" %% "zio" % Versions.zio
  val testcontainers = "com.dimafeng" %% "testcontainers-scala-core" % Versions.testcontainers

}
