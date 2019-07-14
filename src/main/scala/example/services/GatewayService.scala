package example.services

import java.net.URI

import example.ui.Commands

// import cats.syntax.either._
import cats.effect.ContextShift
import cats.syntax.functor._
import cats.{Functor, Monad}
import example.config.ElevioConfig
import example.domain._
import example.infra.HttpClient
import io.chrisdavenport.log4cats.Logger
import io.circe.generic.auto._
import io.circe.parser._

import scala.concurrent.ExecutionContext

trait GatewayService[F[_]] {
  def articleList(page: Option[Int] = None, pageSize: Option[Int] = None, status: Option[String] = None)(
      implicit F: Functor[F]): F[Either[io.circe.Error, ListAllArticlesResponse]]

  def articleDetails(id: Int)(implicit F: Functor[F]): F[Either[io.circe.Error, ArticleDetails]]
}

object GatewayService {

  def apply[F[_]: ContextShift: Monad: Logger](client: HttpClient[F], ec: ExecutionContext, appcf: ElevioConfig): GatewayService[F] =
    new GatewayServiceImpl[F](client, ec, appcf)

  private class GatewayServiceImpl[F[_]: ContextShift: Monad: Logger](httpClient: HttpClient[F], ec: ExecutionContext, appcf: ElevioConfig)
      extends GatewayService[F] {

    def articleList(page: Option[Int] = None, pageSize: Option[Int] = None, status: Option[String] = None)(
        implicit F: Functor[F]): F[Either[io.circe.Error, ListAllArticlesResponse]] = {

      val params =
        paramsStringify(
          Array(
            buildParam(Commands.paramPage, page),
            buildParam(Commands.paramPageSize, pageSize),
            buildParam(Commands.paramStatus, status)))

      val io = for {
        string <- httpClient.getResponseString(
          new URI(appcf.apiUrls.base + appcf.apiUrls.articleList + params),
          appcf.apiAuth.key,
          appcf.apiAuth.token)
        result = decode[ListAllArticlesResponse](string) //.leftMap(new IllegalStateException(_): Throwable).raiseOrPure
      } yield result
      ContextShift[F].evalOn(ec)(io)
    }

    def articleDetails(id: Int)(implicit F: Functor[F]): F[Either[io.circe.Error, ArticleDetails]] = {
      val io = for {
        string <- httpClient.getResponseString(
          new URI(appcf.apiUrls.base + appcf.apiUrls.articleDetails + id),
          appcf.apiAuth.key,
          appcf.apiAuth.token)
        result = decode[ArticleDetails](string) //.leftMap(new IllegalStateException(_): Throwable).raiseOrPure
      } yield result
      ContextShift[F].evalOn(ec)(io)
    }

  }

  private def buildParam[T](name: String, value: Option[T]): Option[String] =
    value.map(v => s"${name}=${v.toString}")
  private def paramsStringify(array: Array[Option[String]]) = {
    array.collect { case Some(v) => v } match {
      case Array() => ""
      case array @ Array(h, _*) => array.mkString("?", "&", "")
    }
  }
}
