package zio.testcontainers

import com.dimafeng.testcontainers.{DockerComposeContainer, SingleContainer}
import org.testcontainers.containers.{GenericContainer => JavaGenericContainer}
import org.testcontainers.lifecycle.Startable
import zio.{RIO, RLayer, Scope, Tag, UIO, ZIO, ZLayer}

object ZIOTestcontainers {

  def getHostAndPort(
      container: DockerComposeContainer
  )(serviceName: String)(servicePort: Int): UIO[(String, Int)] = for {
    host <- ZIO.succeed(container.getServiceHost(serviceName, servicePort))
    port <- ZIO.succeed(container.getServicePort(serviceName, servicePort))
  } yield (host, port)

  def getHostAndPort[T <: JavaGenericContainer[_]](
      container: SingleContainer[T]
  )(port: Int): UIO[(String, Int)] = for {
    host <- ZIO.succeed(container.host)
    port <- ZIO.succeed(container.mappedPort(port))
  } yield (host, port)

  def toZIO[T <: Startable](startable: T): RIO[Scope, T] = {
    val acquire = ZIO.succeedBlocking(startable.start()).as(startable)
    val release = (container: T) => ZIO.succeedBlocking(container.stop())
    ZIO.acquireRelease(acquire)(release)
  }

  def toLayer[T <: Startable: Tag](container: T): RLayer[Scope, T] =
    ZLayer.fromZIO(toZIO(container))

}
