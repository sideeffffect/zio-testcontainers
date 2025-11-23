package zio.testcontainers

import com.dimafeng.testcontainers.{DockerComposeContainer, SingleContainer}
import org.testcontainers.containers.{
  ComposeContainer,
  ContainerState,
  DockerComposeContainer => JavaDockerComposeContainer,
  GenericContainer => JavaGenericContainer,
}
import org.testcontainers.lifecycle.Startable
import zio.{Scope, Tag, UIO, ULayer, URIO, ZIO, ZLayer}

object ZIOTestcontainers {

  def getHostAndPort(
      container: ComposeContainer,
  )(serviceName: String)(servicePort: Int): UIO[(String, Int)] = for {
    host <- ZIO.succeed(container.getServiceHost(serviceName, servicePort))
    port <- ZIO.succeed(container.getServicePort(serviceName, servicePort))
  } yield (host, port)

  def getHostAndPort(
      container: JavaDockerComposeContainer[_],
  )(serviceName: String)(servicePort: Int): UIO[(String, Int)] = for {
    host <- ZIO.succeed(container.getServiceHost(serviceName, servicePort))
    port <- ZIO.succeed(container.getServicePort(serviceName, servicePort))
  } yield (host, port)

  def getHostAndPort(
      container: ContainerState,
  )(port: Int): UIO[(String, Int)] = for {
    host <- ZIO.succeed(container.getHost)
    port <- ZIO.succeed(container.getMappedPort(port))
  } yield (host, port)

  def getHostAndPort(
      container: DockerComposeContainer,
  )(serviceName: String)(servicePort: Int): UIO[(String, Int)] = for {
    host <- ZIO.succeed(container.getServiceHost(serviceName, servicePort))
    port <- ZIO.succeed(container.getServicePort(serviceName, servicePort))
  } yield (host, port)

  def getHostAndPort[T <: JavaGenericContainer[_]](
      container: SingleContainer[T],
  )(port: Int): UIO[(String, Int)] = for {
    host <- ZIO.succeed(container.host)
    port <- ZIO.succeed(container.mappedPort(port))
  } yield (host, port)

  def toZIO[T <: Startable](startable: T): URIO[Scope, T] = {
    // using `succeedBlocking` because there's no point to try to recover if starting/stopping the container fails
    val acquire = ZIO.succeedBlocking(startable.start()).as(startable)
    val release = (container: T) => ZIO.succeedBlocking(container.stop())
    ZIO.acquireRelease(acquire)(release)
  }

  def toLayer[T <: Startable: Tag](container: T): ULayer[T] =
    ZLayer.scoped(toZIO(container))

}
