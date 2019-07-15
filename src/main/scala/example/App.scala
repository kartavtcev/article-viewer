package example

import cats.effect._
import cats.syntax.flatMap._
import example.config.ElevioConfig
import example.infra.{HttpClient, Pools}
import example.services.GatewayService
import io.chrisdavenport.log4cats.Logger
import io.chrisdavenport.log4cats.slf4j.Slf4jLogger
import io.circe.config.parser

trait App extends {

  def run[F[_]: ConcurrentEffect: ContextShift: Timer](implicit logger: Logger[F]) = {

    /**
      * EC should be different for different services. OK for the demo.
      * Also init all the resources that should be free on exit here.
      */
    val resources = for {
      bc <- HttpClient.sttpBackend
      ec <- Pools.fixedThreadPool
      appcf <- Resource.liftF(parser.decodePathF[F, ElevioConfig]("elevio"))
    } yield (bc, ec, appcf)
    //resources // TODO: fix temp

    resources.flatMap {
      case (bc, ec, appcf) =>
        implicit val backend = bc

        val service = GatewayService[F](HttpClient[F], ec, appcf)
        Resource.liftF(
          //service.articleSearchByKeyword(NonEmptyList.of("article"))
          service.articleList()
            .flatMap(response => Logger[F].info(s"Response text: ${response}")) // TODO: remove
        )
    }
  }

  def program[F[_]: ConcurrentEffect: ContextShift: Timer] = {
    implicit val logger = Slf4jLogger.getLogger[F]
    run[F]
  }
}
