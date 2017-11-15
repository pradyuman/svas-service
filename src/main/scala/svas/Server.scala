package svas

import akka.actor.ActorSystem
import com.twitter.finagle.http.Request
import com.twitter.finatra.http.{ Controller, HttpServer }
import com.twitter.finatra.http.routing.HttpRouter
import redis.RedisClient
import scala.concurrent.ExecutionContext.Implicits.global

class Server extends HttpServer {

  override val defaultFinatraHttpPort = s":${sys.env("PORT")}"
  override def jacksonModule = SvasJacksonModule

  val acao = "*"

  override def configureHttp(router: HttpRouter): Unit =
    router.add(controller)

  implicit val actorSystem = ActorSystem("redisActorSystem")
  val redis = RedisClient(
    name = "svas",
    host = sys.env.get("REDIS_HOST").getOrElse("localhost"),
    port = sys.env.get("REDIS_PORT").map(_.toInt).getOrElse(6379),
    password = sys.env.get("REDIS_PASSWORD")
  )
  val state = new State(redis)
  val h = new Handlers(state)

  private def controller = new Controller {

    get("/ping") { req: Request => response.ok }

    get("/status") { req: Request =>
      for {
        res <- h.getStatus(req)
      } yield response.ok
        .header("Access-Control-Allow-Origin", acao)
        .json(res)
    }

    post("/status") { req: proto.Status =>
      for {
        _ <- h.postStatus(req)
      } yield response.ok
    }

    post("/request") { req: proto.Request =>
      for {
        res <- h.postRequest(req)
      } yield response.ok
        .header("Access-Control-Allow-Origin", acao)
        .json(res)
    }

  }

}
