import sbt._

object Dependencies {

  object Versions {
    val zio = "2.0.6"
    val testcontainers = "0.40.12"
  }

  val zio = "dev.zio" %% "zio" % Versions.zio
  val testcontainers = "com.dimafeng" %% "testcontainers-scala" % Versions.testcontainers

}
