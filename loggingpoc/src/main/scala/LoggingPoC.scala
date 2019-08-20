import com.typesafe.scalalogging.LazyLogging
import monix.eval.Task
import monix.execution.Scheduler.Implicits.global

import scala.concurrent.duration._

object LoggingPoC extends LazyLogging {

  def main(args: Array[String]): Unit = {
    import org.slf4j.MDC
    val field = classOf[MDC].getDeclaredField("mdcAdapter")
    field.setAccessible(true)
    field.set(null, new MonixMDCAdapter)

    val workers: Seq[Task[List[Unit]]] = (1 to 10).map { n =>
      Task
        .eval(MDC.put("Worker", n.toString))
        .flatMap { _ =>
          Task
            .traverse(0 to 1000 toList) { x =>
              Task(logger.info(s"worker $n, task $x"))
            }
        }
        .executeWithOptions(_.enableLocalContextPropagation)
    }
    Task
      .gatherUnordered(workers)
      .logTimed("completed")
      .runSyncUnsafe(10 minutes)
  }

  implicit class TaskLogTimed[A](task: Task[A]) {

    def logTimed(message: String): Task[A] = {
      for {
        timed <- task.timed
        (duration, t) = timed
        _ <- Task(logger.info(message + s" in ${duration.toMillis} ms"))
      } yield {
        t
      }
    }
  }
}
