import sbt._

object Dependencies {

  object Versions {
    val zio = "1.0.11"
    val testcontainers = "0.39.5"
  }

  val zio = "dev.zio" %% "zio" % Versions.zio
  val testcontainers = "com.dimafeng" %% "testcontainers-scala" % Versions.testcontainers

}
