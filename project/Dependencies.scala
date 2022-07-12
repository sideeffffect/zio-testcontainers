import sbt._

object Dependencies {

  object Versions {
    val zio = "2.0.0"
    val testcontainers = "0.40.8"
  }

  val zio = "dev.zio" %% "zio" % Versions.zio
  val testcontainers = "com.dimafeng" %% "testcontainers-scala" % Versions.testcontainers

}
