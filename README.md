# ZIO testcontainers

| CI | Release |
| --- | --- |
| [![Build Status][Badge-GitHubActions]][Link-GitHubActions] | [![Release Artifacts][Badge-SonatypeReleases]][Link-SonatypeReleases] |

ZIO wrapper for [Testcontainers](https://github.com/testcontainers/testcontainers-java/).

```scala
"com.github.sideeffffect" %% "zio-testcontainers" % "<version>" % Test
```

This library comes with `toLayer` extension method (behind `zio.testcontainers._` import) that will turn any TestContainer into a layer that you can use in your tests.
```scala
import org.testcontainers.containers.ComposeContainer
import zio.testcontainers._

lazy val dockerCompose: ULayer[ComposeContainer] = ZLayer.fromTestContainer {
  new ComposeContainer(new File("docker-compose.yml"))
    .withExposedService("mariadb", 3306)
    .withExposedService("mailhog", 1025)
    .withExposedService("mailhog", 8025)
}
...
lazy val layer = ZLayer.fromZIO {
  for {
    docker <- ZIO.service[ComposeContainer]
    (host, port) <- docker.getHostAndPort("mariadb")(3306)
    config = ...
  } yield config
}
```


[Link-GitHubActions]: https://github.com/sideeffffect/zio-testcontainers/actions?query=workflow%3ARelease+branch%3Amaster "GitHub Actions link"
[Badge-GitHubActions]: https://github.com/sideeffffect/zio-testcontainers/workflows/Release/badge.svg?branch=master "GitHub Actions badge"

[Link-SonatypeReleases]: https://oss.sonatype.org/content/repositories/releases/com/github/sideeffffect/zio-testcontainers_2.13/ "Sonatype Releases link"
[Badge-SonatypeReleases]: https://maven-badges.herokuapp.com/maven-central/com.github.sideeffffect/zio-testcontainers_2.13/badge.svg "Sonatype Releases badge"
