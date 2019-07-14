package example.infra

import cats.effect._
import cats.syntax.either._
import cats.syntax.flatMap._
import cats.syntax.functor._
import com.softwaremill.sttp.{SttpBackend, _}
import com.softwaremill.sttp.asynchttpclient.cats.AsyncHttpClientCatsBackend
import io.chrisdavenport.log4cats.Logger

trait  HttpClient[F[_]] {
  def getString(uri: java.net.URI, key: String, token: String): F[String]
}

object HttpClient {

  def sttpBackend[F[_] : Async]: Resource[F, SttpBackend[F, Nothing]] = {
    val alloc = Sync[F].delay(AsyncHttpClientCatsBackend[F]())
    val free = (bc: SttpBackend[F, Nothing]) => Sync[F].delay(bc.close())
    Resource.make(alloc)(free)
  }

  def apply[F[_] : cats.MonadError[?[_], Throwable] : Logger](implicit sttpBackend: SttpBackend[F, Nothing]): HttpClient[F] =
    (uri: java.net.URI, key: String, token: String) => for {
      _ <- Logger[F].debug(s"Url call: ${uri.toString}")
      //resp <- sttp.auth.bearer(token).header("x-api-key", key).get(Uri(uri)).send()
      resp <- sttp.headers(Map("Authorization" -> s"Bearer ${token}", "x-api-key" -> key)).get(Uri(uri)).send()
      result <- resp.body.leftMap(new IllegalStateException(_): Throwable).raiseOrPure
    } yield result
}