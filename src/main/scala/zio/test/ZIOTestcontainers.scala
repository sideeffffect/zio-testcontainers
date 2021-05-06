package zio.test

import org.testcontainers.lifecycle.Startable
import zio.blocking.{Blocking, effectBlocking}
import zio.{Has, RLayer, RManaged, Tag, ZManaged}

object ZIOTestcontainers {

  def toManaged[T <: Startable](startable: T): RManaged[Blocking, T] =
    ZManaged.make(effectBlocking(startable.start()).as(startable))(container =>
      effectBlocking(container.stop()).orDie,
    )

  def toLayer[T <: Startable: Tag](container: T): RLayer[Blocking, Has[T]] =
    toManaged(container).toLayer

}
