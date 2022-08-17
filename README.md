# ZIO testcontainers

| CI | Release |
| --- | --- |
| [![Build Status][Badge-GitHubActions]][Link-GitHubActions] | [![Release Artifacts][Badge-SonatypeReleases]][Link-SonatypeReleases] |

ZIO wrapper for [Testcontainers](https://github.com/testcontainers/testcontainers-scala).

```scala
"com.github.sideeffffect" %% "zio-testcontainers" % "<version>" % Test
```

This library comes with `toLayer` extension method (behind `zio.testcontainers._` import) that will turn any TestContainer into a layer that you can use in your tests.
```scala
import zio.testcontainers._

lazy val dockerCompose: ULayer[DockerComposeContainer] = ZLayer.fromTestContainer {
  new DockerComposeContainer(
    new File("docker-compose.yml"),
    List(
      ExposedService("mariadb_1", 3306),
      ExposedService("mailhog_1", 1025),
      ExposedService("mailhog_1", 8025),
    )
  )
}
...
lazy val layer = ZLayer.fromZIO {
  for {
    docker <- ZIO.service[DockerComposeContainer]
    (host, port) <- docker.getHostAndPort("mariadb_1")(3306)
    config = ...
  } yield config
}
```


[Link-GitHubActions]: https://github.com/sideeffffect/zio-testcontainers/actions?query=workflow%3ARelease+branch%3Amaster "GitHub Actions link"
[Badge-GitHubActions]: https://github.com/sideeffffect/zio-testcontainers/workflows/Release/badge.svg?branch=master "GitHub Actions badge"

[Link-SonatypeReleases]: https://oss.sonatype.org/content/repositories/releases/com/github/sideeffffect/zio-testcontainers_2.13/ "Sonatype Releases link"
[Badge-SonatypeReleases]: https://maven-badges.herokuapp.com/maven-central/com.github.sideeffffect/zio-testcontainers_2.13/badge.svg "Sonatype Releases badge"
