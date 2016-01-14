package com.cyril.filter

import com.fasterxml.jackson.annotation.JsonProperty
import com.google.common.net.MediaType._
import com.twitter.finatra.http.marshalling.{MessageBodyWriter, DefaultMessageBodyWriter, WriterResponse}
import com.twitter.finatra.json.FinatraObjectMapper
import javax.inject.Inject
import com.twitter.inject.{ Logging}


class JsonMessageBodyWriter @Inject()(
                                       mapper: FinatraObjectMapper)
  extends DefaultMessageBodyWriter with Logging {

  /* Public */

  override def write(obj: Any): WriterResponse = {

    print(obj.getClass)
    obj match {
      case string:String =>
        toJson(Map("data"->string))
      case product: Product=>
        toJson(product)
      case _ if isCollectionType(obj) =>
        toJson(obj)
      case bytes: Array[Byte] =>
        WriterResponse(APPLICATION_BINARY, bytes)
      case _ =>
        WriterResponse(PLAIN_TEXT_UTF_8, obj.toString)
    }
  }

  /* Private */

  private def toJson(obj: Any): WriterResponse = {
    WriterResponse(
      JSON_UTF_8,
      mapper.writeValueAsBytes(new SohuVideoResp(200,"OK",obj)))
  }

  private def isCollectionType(obj: Any): Boolean = {
    classOf[Iterable[Any]].isAssignableFrom(obj.getClass) ||
      classOf[Array[Any]].isAssignableFrom(obj.getClass)
  }


}
class SohuVideoResp(@JsonProperty(value = "status")  val status:Int,@JsonProperty(value = "statusText")  val statusText:String,@JsonProperty(value = "data")  val data:Any)
