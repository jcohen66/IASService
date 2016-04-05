
import actors.{ProductionReaper, ServiceActor}
import akka.actor.{Props, ActorSystem}
import deathwatch.Reaper.WatchMe

object IASService extends App {

  val system = ActorSystem("ias-service-system")

  // Shutdown monitoring
  val reaper = system.actorOf(Props[ProductionReaper], "reaper")

  // Supervising actor will employ restart strategy in the event of an exception.
  val serviceActor = system.actorOf(Props[ServiceActor], "service-actor")

  // Start to monitor top-level actors
  reaper ! WatchMe(serviceActor)


}
