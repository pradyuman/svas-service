package svas

import com.trueaccord.scalapb.GeneratedMessage
import com.trueaccord.scalapb.json.Printer
import com.twitter.finagle.http.Request
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class Handlers(state: State) {

  val jsonPrinter = new Printer(
    includingDefaultValueFields = true,
    formattingLongAsNumber = true
  )

  private def json(proto: Future[GeneratedMessage]): Future[String] = {
    for {
      p <- proto
    } yield jsonPrinter.print(p)
  }

  val noDefaultsJsonPrinter = new Printer(
    formattingLongAsNumber = true
  )

  private def noDefaultsJson(proto: Future[GeneratedMessage]): Future[String] = {
    for {
      p <- proto
    } yield noDefaultsJsonPrinter.print(p)
  }

  def getStatus(req: Request) = json { state.getLatestStatus() }

  def postStatus(req: proto.Status) = json { state.nextStatus(req) }

  def getRequest() = json {
    Future.successful(state.getRequest())
  }

  def postRequest(req: proto.Request) = json { state.nextRequest(req) }

}
