package zio.test

import com.dimafeng.testcontainers.{DockerComposeContainer, SingleContainer}
import org.testcontainers.containers.{GenericContainer => JavaGenericContainer}
import org.testcontainers.lifecycle.Startable
import zio._
import zio.blocking.Blocking

package object testcontainers {

  implicit final class DockerComposeContainerOps(private val container: DockerComposeContainer)
      extends AnyVal {

    def getHostAndPort(serviceName: String)(servicePort: Int): Task[(String, Int)] =
      ZIOTestcontainers.getHostAndPort(container)(serviceName)(servicePort)

  }

  implicit final class SingleContainerOps[T <: JavaGenericContainer[_]](
      private val container: SingleContainer[T]
  ) extends AnyVal {

    def getHostAndPort(port: Int): Task[(String, Int)] =
      ZIOTestcontainers.getHostAndPort(container)(port)

  }

  implicit final class StartableOps[T <: Startable](private val startable: T) extends AnyVal {

    def toManaged: RManaged[Blocking, T] =
      ZIOTestcontainers.toManaged(startable)

    def toLayer(implicit ev: Tag[T]): RLayer[Blocking, Has[T]] =
      ZIOTestcontainers.toLayer(startable)

  }

}
