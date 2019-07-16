package example.view

import cats.effect.Sync

object ConsoleIO {
  def putStrLn[F[_]: Sync](value: String) = Sync[F].delay(println(value))
  def readLn[F[_]: Sync] = Sync[F].delay(scala.io.StdIn.readLine)
}
