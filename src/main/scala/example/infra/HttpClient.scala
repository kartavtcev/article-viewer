package example.infra

import cats.Functor
import cats.effect._
import cats.syntax.either._
import cats.syntax.flatMap._
import cats.syntax.functor._
import com.softwaremill.sttp.asynchttpclient.cats.AsyncHttpClientCatsBackend
import com.softwaremill.sttp.{SttpBackend, _}
import example.domain._
import io.chrisdavenport.log4cats.Logger
import io.circe.generic.auto._
import io.circe.parser._

trait HttpClient[F[_]] {

  def getString(uri: java.net.URI, key: String, token: String): F[String]

  def listAllArticles(uri: java.net.URI, key: String, token: String)(
      implicit F: Functor[F]): F[Either[io.circe.Error, ListAllArticlesResponse]] =
    for {
      string <- getString(uri, key, token)
      result = decode[ListAllArticlesResponse](string)
    } yield result

}

object HttpClient {

  def sttpBackend[F[_]: Async]: Resource[F, SttpBackend[F, Nothing]] = {
    val alloc = Sync[F].delay(AsyncHttpClientCatsBackend[F]())
    val free = (bc: SttpBackend[F, Nothing]) => Sync[F].delay(bc.close())
    Resource.make(alloc)(free)
  }

  def apply[F[_]: cats.MonadError[?[_], Throwable]: Logger](implicit sttpBackend: SttpBackend[F, Nothing]): HttpClient[F] =
    (uri: java.net.URI, key: String, token: String) =>
      for {
        _ <- Logger[F].info(s"Url call: ${uri.toString}")
        resp <- sttp
          .headers(Map("Authorization" -> s"Bearer ${token}", "x-api-key" -> key))
          .get(Uri(uri))
          .send()
        result <- resp.body.leftMap(new IllegalStateException(_): Throwable).raiseOrPure
      } yield result
}
