package com.cyril.filter

import com.twitter.finatra.json.FinatraObjectMapper
import com.twitter.inject.{Logging}

import com.twitter.finagle.http.{Request, Response}
import com.twitter.finagle.{CancelledRequestException, Failure}
import com.cyril.filter.JsonExceptionMapper._
import com.twitter.finatra.http.exceptions.{DefaultExceptionMapper, HttpException, HttpResponseException}

import com.twitter.finatra.http.response.{ResponseBuilder}
import javax.inject.{Inject, Singleton}

object JsonExceptionMapper {
  private val MaxDepth = 5

  private def unwrapFailure(failure: Failure, depth: Int): Throwable = {
    if (depth == 0)
      failure
    else
      failure.cause match {
        case Some(inner: Failure) => unwrapFailure(inner, depth - 1)
        case Some(cause) => cause
        case None => failure
      }
  }

}

@Singleton
class JsonExceptionMapper @Inject()( response: ResponseBuilder,mapper: FinatraObjectMapper)
  extends DefaultExceptionMapper with Logging {

  override def toResponse(request: Request, throwable: Throwable): Response = {
    val builder = response.status(200)
    error(throwable)
    throwable match {
      case e: HttpException => //TODO: Optionally pass handled exception
        builder.json(new SohuVideoResp(200,"HttpException",Nil))
      case e: HttpResponseException => //TODO: Optionally pass handled exception
        builder.json(new SohuVideoResp(200,"HttpResponseException",Nil))
      case e: CancelledRequestException =>
        builder.json(new SohuVideoResp(200,"CancelledRequestException",Nil))
      case e: Failure =>
        unwrapFailure(e, MaxDepth) match {
          case cause: Failure =>
            builder.json(new SohuVideoResp(50000,"Failure",cause.getClass.getCanonicalName))
          case cause =>
            toResponse(request, cause)
        }

      case e =>
        builder.json(new SohuVideoResp(50000,"Error",e.getClass.getCanonicalName))
    }
  }
}
