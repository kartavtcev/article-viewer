package example.infra

import java.util.concurrent.{ExecutorService, Executors}

import cats.effect.{Resource, Sync}

import scala.concurrent.ExecutionContext

/**
  * Utility object to create ec as a Resource
  */
object Pools {
  def fixedThreadPool[F[_] : Sync]: Resource[F, ExecutionContext] = {
    val alloc = Sync[F].delay(Executors.newCachedThreadPool()) // many short-lived asynchronous tasks
    val free = (es: ExecutorService) => Sync[F].delay(es.shutdown())
    Resource.make(alloc)(free).map(ExecutionContext.fromExecutor)
  }
}