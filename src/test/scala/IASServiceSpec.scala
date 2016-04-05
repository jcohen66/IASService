import akka.actor.ActorSystem
import akka.http.model.StatusCodes
import akka.testkit._
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.DefaultHttpClient
import org.scalatest._

class IASServiceSpec extends TestKit(ActorSystem("IAS-Spec")) with FlatSpecLike
{
    "IASService" should "take a domain name of 'nytimes.com' and reply with a list of 68 segments" in {

      val uri = "http://api.adsafeprotected.com/db2/client/11922/seg?adsafe_url="
      val domain = "nytimes.com"
      val expectedEntries = 68

      val system = ActorSystem("test-ias-system")

      val httpClient = new DefaultHttpClient()
      val httpResponse = httpClient.execute(new HttpGet(uri + domain))
      val entity = httpResponse.getEntity()
      var content = ""
      if (entity != null) {
        val inputStream = entity.getContent()
        content = io.Source.fromInputStream(inputStream).getLines.mkString
        inputStream.close
      }
      httpClient.getConnectionManager().shutdown()

      assert(content.split(",").length == expectedEntries)

    }

  it should "take a domain name empty ('') and reply with only 1 segment ('passed')" in {

    val uri = "http://api.adsafeprotected.com/db2/client/11922/seg?adsafe_url="
    val domain = ""
    val expectedEntries = 1

    val system = ActorSystem("test-ias-system")

    val httpClient = new DefaultHttpClient()
    val httpResponse = httpClient.execute(new HttpGet(uri + domain))
    val entity = httpResponse.getEntity()
    var content = ""
    if (entity != null) {
      val inputStream = entity.getContent()
      content = io.Source.fromInputStream(inputStream).getLines.mkString
      inputStream.close
    }
    httpClient.getConnectionManager().shutdown()

    assert(content.split(",").length == expectedEntries)
  }


  it should "reply with status NOT FOUND if bad url passed in" in {

    val uri = "http://api.adsafeprotected.com/db2/client/11922/"
    val domain = ""

    val system = ActorSystem("test-ias-system")

    val httpClient = new DefaultHttpClient()
    val httpResponse = httpClient.execute(new HttpGet(uri))
    val entity = httpResponse.getEntity()
    var content = ""
    if (entity != null) {
      val inputStream = entity.getContent()
      content = io.Source.fromInputStream(inputStream).getLines.mkString
      inputStream.close
    }
    httpClient.getConnectionManager().shutdown()

    assert(httpResponse.getStatusLine.getStatusCode == StatusCodes.BadRequest)

  }

}
