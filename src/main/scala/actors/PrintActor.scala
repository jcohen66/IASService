package actors

import actors.PrintActor.Print
import akka.actor.{Actor, ActorLogging}


object PrintActor {
  trait PrintMessage
  case class Print(msg: String) extends PrintMessage
}

class PrintActor extends Actor with ActorLogging {

  def receive = {
    case Print(s) => printToConsole(s)
    case _ => throw new IllegalStateException("Just fail already!")
  }

  def printToConsole(s: String): Unit = {

    println("\n***********************************")
    println(s)
    println("***********************************\n")

  }
}
