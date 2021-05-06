package zio.testcontainers

import com.dimafeng.testcontainers.{DockerComposeContainer, SingleContainer}
import org.testcontainers.containers.{GenericContainer => JavaGenericContainer}
import org.testcontainers.lifecycle.Startable
import zio.blocking.{Blocking, effectBlocking}
import zio.{Has, RLayer, RManaged, Tag, Task, ZIO, ZManaged}

object ZIOTestcontainers {

  def getHostAndPort(
      container: DockerComposeContainer
  )(serviceName: String)(servicePort: Int): Task[(String, Int)] = for {
    host <- ZIO.effect(container.getServiceHost(serviceName, servicePort))
    port <- ZIO.effect(container.getServicePort(serviceName, servicePort))
  } yield (host, port)

  def getHostAndPort[T <: JavaGenericContainer[_]](
      container: SingleContainer[T]
  )(port: Int): Task[(String, Int)] = for {
    host <- ZIO.effect(container.host)
    port <- ZIO.effect(container.mappedPort(port))
  } yield (host, port)

  def toManaged[T <: Startable](startable: T): RManaged[Blocking, T] =
    ZManaged.make(effectBlocking(startable.start()).as(startable))(container =>
      effectBlocking(container.stop()).orDie
    )

  def toLayer[T <: Startable: Tag](container: T): RLayer[Blocking, Has[T]] =
    toManaged(container).toLayer

}
