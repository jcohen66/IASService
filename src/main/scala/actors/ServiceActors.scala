package actors

import akka.util.Timeout

import scala.concurrent.duration._

object ServiceActors {
  val resolveTimeout = Timeout(5 seconds)

  val ServiceBasePath = "akka://ias-service-system/user/service-actor/"
  val PrintActorPath = ServiceBasePath + "print-actor"
  val WebActorPath = ServiceBasePath + "web-actor"
  val DataActorPath = ServiceBasePath + "data-actor"
}
