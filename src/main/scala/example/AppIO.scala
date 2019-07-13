package example

import cats.effect.{ExitCode, IO, IOApp}
import cats.syntax.functor._

object AppIO extends IOApp with App {

  override def run(args: List[String]): IO[ExitCode] = program[IO].use(_ => IO.never.as(ExitCode.Success))
  //.handleErrorWith(_ => IO.unit)  // TODO: handle & log errors.

}