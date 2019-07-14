package example.services

import java.net.URI

import cats.syntax.functor._
import cats.{Functor, Monad}
import cats.effect.ContextShift
import example.config.ElevioConfig
import example.domain.ListAllArticlesResponse
import example.infra.HttpClient
import io.chrisdavenport.log4cats.Logger
import io.circe.generic.auto._
import io.circe.parser._

import scala.concurrent.ExecutionContext

trait GatewayService[F[_]] {
  def listAllArticles(implicit F: Functor[F]): F[Either[io.circe.Error, ListAllArticlesResponse]]
}

object GatewayService {
  def apply[F[_]: ContextShift: Monad: Logger](client: HttpClient[F], ec: ExecutionContext, appcf: ElevioConfig): GatewayService[F] =
    new GatewayServiceImpl[F](client, ec, appcf)

  private class GatewayServiceImpl[F[_]: ContextShift: Monad: Logger](httpClient: HttpClient[F], ec: ExecutionContext, appcf: ElevioConfig)
      extends GatewayService[F] {

    private def getReply(uri: URI, key: String, token: String): F[String] = {
      val io = for {
        result <- httpClient.getResponseString(uri, key, token)
      } yield result
      ContextShift[F].evalOn(ec)(io)
    }

    def listAllArticles(implicit F: Functor[F]): F[Either[io.circe.Error, ListAllArticlesResponse]] =
      for {
        string <- getReply(new URI(appcf.apiUrls.base + appcf.apiUrls.allArticles), appcf.apiAuth.key, appcf.apiAuth.token)
        result = decode[ListAllArticlesResponse](string)
      } yield result
  }
}
