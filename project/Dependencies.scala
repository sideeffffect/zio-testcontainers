import sbt._

object Dependencies {

  object Versions {
    val zio = "2.0.2"
    val testcontainers = "0.40.14"
  }

  val zio = "dev.zio" %% "zio" % Versions.zio
  val testcontainers = "com.dimafeng" %% "testcontainers-scala" % Versions.testcontainers

}
