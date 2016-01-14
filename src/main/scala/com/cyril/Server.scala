package com.cyril

import com.cyril.controller.UserLoginController
import com.cyril.filter._
import com.cyril.module.{RemoteModule, JsonExceptionMapperModule, MysqlModule}
import com.twitter.finagle.http.{Request}
import com.twitter.finatra.http.filters.{ ExceptionMappingFilter, CommonFilters}
import com.twitter.finatra.http.routing.HttpRouter
import com.twitter.finatra.http.{HttpServer}
import com.twitter.finatra.logging.filter.{TraceIdMDCFilter, LoggingMDCFilter}
import com.twitter.finatra.logging.modules.Slf4jBridgeModule

object ServerMain extends Server

class Server extends HttpServer {

  override val disableAdminHttpServer = true
  override def messageBodyModule  = JsonMessageBodyModule
  override def jacksonModule = CustomJacksonModule
  override def modules = Seq(Slf4jBridgeModule,MysqlModule,RemoteModule)
  override def exceptionMapperModule = JsonExceptionMapperModule

  override def configureHttp(router: HttpRouter) {

    router
//      .filter[LoggingMDCFilter[Request, Response]]
//      .filter[TraceIdMDCFilter[Request, Response]]
      .filter[CommonFilters]
      .filter[ExceptionMappingFilter[Request]]
      .add[UserLoginController]
      .exceptionMapper[JsonExceptionMapper]
  }
}

