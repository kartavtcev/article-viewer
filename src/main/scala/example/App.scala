package example

import cats.effect._
import example.infra.{HttpClient, Pools}
import io.chrisdavenport.log4cats.Logger
import io.chrisdavenport.log4cats.slf4j.Slf4jLogger

trait App extends {

  def run[F[_]: ConcurrentEffect: ContextShift: Timer](implicit logger: Logger[F]) = {

    /**
      * EC should be different for different services. OK for the demo.
      * Also init all the resources that should be free on exit here.
      */
    val resources = for {
      bc <- HttpClient.sttpBackend
      ec <- Pools.fixedThreadPool
    } yield (bc, ec)
    resources // TODO: fix temp
    /*
    resources.flatMap { case (bc, ec) =>
      implicit val backend = bc
      val client = HttpClient[F]
      client
    }
   */
  }

  def program[F[_]: ConcurrentEffect: ContextShift: Timer] = {
    implicit val logger = Slf4jLogger.getLogger[F]
    run[F]
  }
}
