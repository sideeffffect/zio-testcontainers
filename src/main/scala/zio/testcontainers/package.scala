package zio

import com.dimafeng.testcontainers.{DockerComposeContainer, SingleContainer}
import org.testcontainers.containers.{GenericContainer => JavaGenericContainer}
import org.testcontainers.lifecycle.Startable

package object testcontainers {

  implicit final class DockerComposeContainerOps(private val container: DockerComposeContainer) extends AnyVal {

    def getHostAndPort(serviceName: String)(servicePort: Int): UIO[(String, Int)] =
      ZIOTestcontainers.getHostAndPort(container)(serviceName)(servicePort)

  }

  implicit final class SingleContainerOps[T <: JavaGenericContainer[_]](
      private val self: SingleContainer[T]
  ) extends AnyVal {

    def getHostAndPort(port: Int): UIO[(String, Int)] =
      ZIOTestcontainers.getHostAndPort(self)(port)

  }

  implicit final class StartableOps[T <: Startable](private val self: T) extends AnyVal {

    def toZIO: RIO[Scope, T] =
      ZIOTestcontainers.toZIO(self)

    def toLayer(implicit ev: Tag[T]): RLayer[Scope, T] =
      ZIOTestcontainers.toLayer(self)

  }

  implicit final class ZLayerTestContainerOps[T <: Startable](private val self: ZLayer.type) extends AnyVal {

    def fromTestContainer(startable: T)(implicit ev: Tag[T]): RLayer[Scope, T] =
      ZIOTestcontainers.toLayer(startable)

  }

}
