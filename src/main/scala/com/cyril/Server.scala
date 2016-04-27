package com.cyril

import com.cyril.controller.UserLoginController
import com.cyril.filter._
import com.cyril.module.{KestrelModule, JsonExceptionMapperModule, MysqlModule, RemoteModule}
import com.twitter.finagle.http.{Request, Response}
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.finagle.tracing.DefaultTracer
import com.twitter.finagle.zipkin.thrift.RawZipkinTracer
import com.twitter.finagle.{Http, ListeningServer, param}
import com.twitter.finatra.http.HttpServer
import com.twitter.finatra.http.filters.{CommonFilters, ExceptionMappingFilter}
import com.twitter.finatra.http.routing.HttpRouter
import com.twitter.finatra.logging.filter.{LoggingMDCFilter, TraceIdMDCFilter}
import com.twitter.finatra.logging.modules.Slf4jBridgeModule
import com.twitter.inject.server.PortUtils
import com.twitter.util.Await

object Server extends HttpServer {

  override def messageBodyModule = JsonMessageBodyModule

  override def jacksonModule = CustomJacksonModule

  override def modules = Seq(Slf4jBridgeModule, MysqlModule, RemoteModule,KestrelModule)

  override def exceptionMapperModule = JsonExceptionMapperModule

  import com.twitter.finagle.server.StackServer

  override def configureHttp(router: HttpRouter) {

    router
      .filter[LoggingMDCFilter[Request, Response]]
      .filter[TraceIdMDCFilter[Request, Response]]
      .filter[CommonFilters]
      .filter[ExceptionMappingFilter[Request]]
      .add[UserLoginController]
      .exceptionMapper[JsonExceptionMapper]
  }

  private var httpServer: ListeningServer = _

  /** If the span transport is set, trace accordingly, or disable tracing. */
  premain {

    //    ZipkinTracer.mk(host = "10.16.12.137", port = 1463,statsReceiver=DefaultStatsReceiver, sampleRate = 1.0f)
    DefaultTracer.self = RawZipkinTracer("10.16.12.137", 1463)
  }




  override def postWarmup() {
    if (disableAdminHttpServer) {
      info("Disabling the Admin HTTP Server since disableAdminHttpServer=true")
      adminHttpServer.close()
    }

    /** Httpx.server will trace all paths. Disable tracing of POST. */
    httpServer = Http.Server(StackServer.newStack
      .replace(FilteredHttpEntrypointTraceInitializer.role, FilteredHttpEntrypointTraceInitializer))
      .configured(param.Label("finatra-rest"))
      .configured(param.Stats(injector.instance[StatsReceiver]))
      .serve(defaultFinatraHttpPort, httpService)
    info("http server started on port: " + httpExternalPort.get)

    onExit {
      Await.result(httpServer.close(defaultShutdownTimeout.fromNow))
    }
  }

  override def httpExternalPort = Option(httpServer).map(PortUtils.getPort)

  override def waitForServer() = Await.ready(httpServer)
}

