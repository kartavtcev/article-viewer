package example

import cats.effect.{ExitCode, IO, IOApp}
import cats.syntax.functor._
import io.chrisdavenport.log4cats.slf4j.Slf4jLogger

object AppIO extends IOApp with App {

  override def run(args: List[String]): IO[ExitCode] = {
    implicit val logger = Slf4jLogger.getLogger[IO]
    program[IO]
      .use(_ => IO.unit)
      .handleErrorWith(ex => logger.error(ex)(ex.getMessage))
      .as(ExitCode.Success)
  }
}
