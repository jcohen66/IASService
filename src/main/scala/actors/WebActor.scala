package actors


import actors.PrintActor.Print
import akka.actor._
import akka.event.Logging
import akka.http.Http
import akka.http.model.HttpMethods._
import akka.http.model._
import akka.stream.FlowMaterializer
import akka.stream.scaladsl.Flow
import akka.util.Timeout
import caching.CacheAsMap

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}


class WebActor extends Actor with ActorLogging {
  override val log = Logging(context.system, this)

  implicit val system = context.system
  implicit val materializer = FlowMaterializer()

  implicit val resolveTimeout = Timeout(5 seconds)

  val printActor = Await.result(context.actorSelection(ServiceActors.PrintActorPath).resolveOne(), ServiceActors.resolveTimeout.duration)

  // start the server on the specified interface and port.
  val serverBinding2 = Http().bind(interface = "localhost", port = 8090)
  serverBinding2.connections.foreach { connection =>
    connection.handleWith(Flow[HttpRequest].mapAsync(asyncHandler))
  }


  def receive = {
    case _ =>
  }

  val cache = new CacheAsMap[String, String]()

  // With an async handler, we use futures. Threads aren't blocked.
  def asyncHandler(request: HttpRequest): Future[HttpResponse] = {
    // we match the request, and some simple path checking
    request match {

      case HttpRequest(GET, Uri.Path("/proxy/ias"), _, _, _) => {
        request.uri.query.get("adsafe_url") match {
          case Some(k) =>

            printActor ! Print(s"Rec'd req: ${k}")
            Future[HttpResponse] {

              var content = cache.getOrElse(k, "SEGMENT NOT FOUND")
              if(content.equals("SEGMENT NOT FOUND"))
              {
                content = api.IASClient.fetchSegments(k)
                cache.put(k, content)
              }

              HttpResponse(entity = content.toString, status = StatusCodes.OK)
            }
          case None => Future[HttpResponse] {
            HttpResponse(status = StatusCodes.NotFound)
          }
        }
      }

      // Simple case that matches everything, just return a not found
      case HttpRequest(_, _, _, _, _) => {
        Future[HttpResponse] {
          HttpResponse(status = StatusCodes.NotFound)
        }
      }

    }
  }



}
