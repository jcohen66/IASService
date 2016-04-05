package actors

import akka.actor.SupervisorStrategy.Restart
import akka.actor.{Actor, ActorLogging, OneForOneStrategy, Props}
import deathwatch.Reaper.WatchMe


object ServiceActor {
  val BasePath = "akka://user/service-actor/"
}

class ServiceActor extends Actor with ActorLogging {

  val serviceReaper = context.actorOf(Props[ProductionReaper], "service-reaper")

  val printActor = context.actorOf(Props[PrintActor], "print-actor")
  val webActor = context.actorOf(Props[WebActor], "web-actor")

  serviceReaper ! WatchMe(printActor)
  serviceReaper ! WatchMe(webActor)


  def receive = {
    case _ =>
  }

  // Perform restart of supervised actors in the event of a problem.
  override val supervisorStrategy = OneForOneStrategy(loggingEnabled = true) {
    case _: Exception => Restart
  }
}
