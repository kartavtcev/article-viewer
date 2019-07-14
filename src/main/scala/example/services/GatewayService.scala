package example.services

import java.net.URI

import cats.syntax.functor._
import cats.Monad
import cats.effect.ContextShift
import example.infra.HttpClient
import io.chrisdavenport.log4cats.Logger

import scala.concurrent.ExecutionContext

trait HeadlinesService[F[_]] {
  def getReply(uri: URI, key: String, token: String): F[String]
}

object GatewayService {
  def apply[F[_]: ContextShift: Monad: Logger](client: HttpClient[F], ec: ExecutionContext): HeadlinesService[F] =
    new GatewayServiceImpl[F](client, ec)

  private class GatewayServiceImpl[F[_]: ContextShift: Monad: Logger](httpClient: HttpClient[F], ec: ExecutionContext)
      extends HeadlinesService[F] {

    def getReply(uri: URI, key: String, token: String): F[String] = {
      val io = for {
        result <- httpClient.getString(uri, key, token)
      } yield result
      ContextShift[F].evalOn(ec)(io)
    }
  }
}