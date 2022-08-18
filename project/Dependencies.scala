import sbt._

object Dependencies {

  object Versions {
    val zio = "2.0.1"
    val testcontainers = "0.40.10"
  }

  val zio = "dev.zio" %% "zio" % Versions.zio
  val testcontainers = "com.dimafeng" %% "testcontainers-scala" % Versions.testcontainers

}
