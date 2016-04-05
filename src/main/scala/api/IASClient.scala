package api

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.fasterxml.jackson.module.scala.experimental.ScalaObjectMapper
import api.IASClient.SegmentList
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.DefaultHttpClient


object IASClient{

  case class SegmentList(var segment_codes: List[String])

  val uri = "http://api.adsafeprotected.com/db2/client/11922/seg?adsafe_url="
  val client = new IASClient(uri)

  def fetchSegments(dom: String): String = {
    client.getRestContent(dom)
  }
}

class IASClient(url: String) {
  /**
    * Returns the text content from a REST URL. Returns a blank String if there
    * is a problem.
    */
  private def getRestContent(dom: String): String = {
    if(dom.isEmpty) {
      return ""
    }

    val httpClient = new DefaultHttpClient()
    val httpResponse = httpClient.execute(new HttpGet(url + dom))
    val entity = httpResponse.getEntity()
    var content = ""
    if (entity != null) {
      val inputStream = entity.getContent()
      content = io.Source.fromInputStream(inputStream).getLines.mkString
      inputStream.close
    }
    httpClient.getConnectionManager().shutdown()
    content
  }

  private def convertJSON(content: String): SegmentList = {

    val mapper = new ObjectMapper() with ScalaObjectMapper
    mapper.registerModule(DefaultScalaModule)

    mapper.readValue(content, classOf[SegmentList])
  }

}

