package svas

import cats.implicits._
import redis.RedisClient
import redis.api.Limit
import scala.concurrent.ExecutionContext.Implicits.global
import svas.proto._

class State(redis: RedisClient) {

  var request = Request()

  val statusKey = "STATUS"
  val tripKey = "TRIP_START"

  def getLatestStatus() = {
    for {
      res <- redis.zrevrangebyscore(
        key = statusKey,
        min = Limit(Double.PositiveInfinity),
        max = Limit(Double.NegativeInfinity),
        limit = (0L, 1L).some
      )
    } yield res.headOption
      .map(x => Status.parseFrom(x.toArray[Byte]))
      .getOrElse(Status())
  }

  def nextStatus(status: Status) = {
    val s =
      if (status.timestamp === 0)
        status.copy(timestamp = System.currentTimeMillis())
      else
        status

    for {
      res <- redis.zadd(statusKey, (s.timestamp, s.toByteArray))
    } yield s
  }

  def nextTrip(timestamp: Long) = {
    for {
      res <- redis.zadd(tripKey, (timestamp, ""))
    } yield res
  }

  def getRequest() = {
    val t = request
    request = Request()
    t
  }

  def nextRequest(r: Request) = {
    request = r

    for {
      curr <- getLatestStatus()
      next = curr.copy(
        timestamp = System.currentTimeMillis(),
        leftWindowUp = r.leftWindowUp,
        leftWindowDown = r.leftWindowDown,
        rightWindowUp = r.rightWindowUp,
        rightWindowDown = r.rightWindowDown,
        leftDoorLocked = r.leftDoorLock,
        rightDoorLocked = r.rightDoorLock,
        trunkOpen = r.trunkOpen,
        hazardOn = r.hazardOn,
        popupOn = r.popupOn,
        hornOn = r.hornOn,
        hornStrobe = r.hornStrobe,
        mcAcOn = r.mcAcOn,
        mcFanOn = r.fanOn,
        mcAccessoryOn = r.accessoryOn,
        mcIg1On = r.ig1On,
        mcIg2On = r.ig2On,
        carStarted = r.start
      )
      _ <- nextStatus(next)
    } yield next
  }

}
