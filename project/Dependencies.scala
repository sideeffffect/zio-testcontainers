import sbt._

object Dependencies {

  object Versions {
    val zio = "2.1.4"
    val testcontainers = "0.41.3"
  }

  val zio = "dev.zio" %% "zio" % Versions.zio
  val testcontainers = "com.dimafeng" %% "testcontainers-scala-core" % Versions.testcontainers

}
