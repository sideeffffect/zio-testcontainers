package zio

import org.testcontainers.containers.{ComposeContainer, ContainerState, DockerComposeContainer}
import org.testcontainers.lifecycle.Startable

package object testcontainers {

  implicit final class ComposeContainerOps(private val container: ComposeContainer) extends AnyVal {

    def getHostAndPort(serviceName: String)(servicePort: Int): UIO[(String, Int)] =
      ZIOTestcontainers.getHostAndPort(container)(serviceName)(servicePort)

  }

  implicit final class DockerComposeContainerOps(private val container: DockerComposeContainer[_]) extends AnyVal {

    def getHostAndPort(serviceName: String)(servicePort: Int): UIO[(String, Int)] =
      ZIOTestcontainers.getHostAndPort(container)(serviceName)(servicePort)

  }

  implicit final class ContainerStateOps(private val self: ContainerState) extends AnyVal {

    def getHostAndPort(port: Int): UIO[(String, Int)] =
      ZIOTestcontainers.getHostAndPort(self)(port)

  }

  implicit final class StartableOps[T <: Startable](private val self: T) extends AnyVal {

    def toZIO: URIO[Scope, T] =
      ZIOTestcontainers.toZIO(self)

    def toLayer(implicit ev: Tag[T]): ULayer[T] =
      ZIOTestcontainers.toLayer(self)

  }

  implicit final class ZLayerTestContainerOps(private val self: ZLayer.type) extends AnyVal {

    def fromTestContainer[T <: Startable](startable: T)(implicit ev: Tag[T]): ULayer[T] =
      ZIOTestcontainers.toLayer(startable)

  }

}
