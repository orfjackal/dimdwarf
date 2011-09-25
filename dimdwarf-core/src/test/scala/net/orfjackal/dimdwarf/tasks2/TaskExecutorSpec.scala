package net.orfjackal.dimdwarf.tasks2

import org.hamcrest.Matchers._
import org.hamcrest.MatcherAssert.assertThat
import org.junit.runner.RunWith
import net.orfjackal.specsy._
import net.orfjackal.dimdwarf.mq.MessageQueue
import net.orfjackal.dimdwarf.net._
import net.orfjackal.dimdwarf.db.Blob
import net.orfjackal.dimdwarf.domain._

@RunWith(classOf[Specsy])
class TaskExecutorSpec extends Spec {
  val toHub = new MessageQueue[Any]("toHub")
  val taskExecutor = new TaskExecutor(toHub)

  val session = DummySessionHandle()
  val message = Blob.fromBytes("hello".getBytes)

  "TEMPORARY INTEGRATION TEST" >> {
    // TODO: deepen the design, split this test into smaller pieces
    val sessionId = SessionId(SimpleTimestamp(42L))
    taskExecutor.processSessionMessage(sessionId, message)

    assertThat(toHub.poll(1000), is(SessionMessageToClient(message, sessionId): Any))
  }

  case class DummySessionHandle() extends SessionHandle
}
