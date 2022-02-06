import sbt._

object Dependencies {

  object Versions {
    val zio = "1.0.13"
    val testcontainers = "0.40.1"
  }

  val zio = "dev.zio" %% "zio" % Versions.zio
  val testcontainers = "com.dimafeng" %% "testcontainers-scala" % Versions.testcontainers

}
