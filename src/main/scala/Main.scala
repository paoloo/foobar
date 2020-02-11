import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import akka.http.scaladsl.model.{ContentType, HttpEntity, HttpResponse}
import akka.http.scaladsl.model.ContentTypes

import spray.json._

import scala.concurrent.Await
import scala.util.{Failure, Success, Try}

object Main extends App {

  val host = "0.0.0.0"
  val port = Try(System.getenv("PORT")).map(_.toInt).getOrElse(9000)
  val c_id = scala.util.Properties.envOrElse("COMMIT", "-none-")

  implicit val system: ActorSystem = ActorSystem(name = "testapi")
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  import system.dispatcher

  import akka.http.scaladsl.server.Directives._
  def route = pathPrefix("v1") {
    pathEndOrSingleSlash {
      get {
        complete(HttpEntity(ContentTypes.`application/json`,  "{\"status\":\"ok\"}"))
      }
    } ~ path("version") {
      get {
          complete(HttpEntity(ContentTypes.`application/json`, "{\"status\":\"ok\", \"version\":1.0, \"commit\":\"" + c_id + "\"}"))
        }
    } ~ path("delay" / Segment) { delay =>
      get {
        Thread.sleep(delay.toInt * 1000)
        complete(HttpEntity(ContentTypes.`application/json`, "{\"status\":\"ok\", \"delay\":\"" + delay + "sec\"}"))
      }
    }
  }

  val binding = Http().bindAndHandle(route, host, port)
  binding.onComplete {
    case Success(_) => println("[+] server up!")
    case Failure(error) => println(s"[-] server error: ${error.getMessage}")
  }

  import scala.concurrent.duration._
  Await.result(binding, 3.seconds)
}
