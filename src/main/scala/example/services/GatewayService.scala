package example.services

import java.net.URI

// import cats.syntax.either._
import cats.effect.ContextShift
import cats.syntax.functor._
import cats.{Functor, Monad}
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

    def listAllArticles(implicit F: Functor[F]): F[Either[io.circe.Error, ListAllArticlesResponse]] = {
      val io = for {
        string <- httpClient.getResponseString(
          new URI(appcf.apiUrls.base + appcf.apiUrls.allArticles),
          appcf.apiAuth.key,
          appcf.apiAuth.token)
        result = decode[ListAllArticlesResponse](string) //.leftMap(new IllegalStateException(_): Throwable).raiseOrPure
      } yield result
      ContextShift[F].evalOn(ec)(io)
    }
  }
}
