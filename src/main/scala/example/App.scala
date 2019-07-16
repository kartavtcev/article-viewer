package example

import cats.effect._
import cats.syntax.flatMap._
import cats.syntax.functor._
import example.config.ElevioConfig
import example.infra.{HttpClient, Pools}
import example.services.GatewayService
import example.view._
import io.chrisdavenport.log4cats.Logger
import io.circe.config.parser

import scala.util.{Failure, Success}

trait App extends {

  def run[F[_]: ConcurrentEffect: ContextShift](implicit logger: Logger[F]) = {

    /**
      * EC should be different for different services. OK for the demo.
      * Also init all the resources that should be free on exit here.
      */
    val resources = for {
      bc <- HttpClient.sttpBackend
      ec <- Pools.fixedThreadPool
      appcf <- Resource.liftF(parser.decodePathF[F, ElevioConfig]("elevio"))
    } yield (bc, ec, appcf)

    resources.flatMap {
      case (bc, ec, appcf) =>
        implicit val backend = bc

        val service = GatewayService[F](HttpClient[F], ec, appcf)

        Resource.liftF(for {
          _ <- ConsoleIO.putStrLn(ConsoleCommands.help)
          input <- ConsoleIO.readLn
            .map(ConsoleCommands.parseCommandWithParams(_))
            .map {
              case Success(ArticleListCommand(page, pageSize, status)) =>
                service
                  .articleList(page, pageSize, status)
                  .flatMap(response => Logger[F].info(s"Response text: ${response}")) // TODO: remove
              case Success(ArticleDetailsCommand(id)) =>
                service
                  .articleDetails(id)
                  .flatMap(response => Logger[F].info(s"Response text: ${response}")) // TODO: remove
              case Success(ArticleSearchByKeyword(query, lang)) =>
                service
                  .articleSearchByKeyword(query, lang)
                  .flatMap(response => Logger[F].info(s"Response text: ${response}")) // TODO: remove
              case Failure(e) => Logger[F].error(e)("Error happened while parsing command.")
              case other @ _ => Logger[F].warn(other.toString)
            }
            .flatten
        } yield (input))
    }
  }

  def program[F[_]: ConcurrentEffect: ContextShift](implicit logger: Logger[F]) = run[F]

}
