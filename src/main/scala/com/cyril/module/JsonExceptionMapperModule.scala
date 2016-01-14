package com.cyril.module

import com.cyril.filter.JsonExceptionMapper
import com.twitter.finatra.http.exceptions.DefaultExceptionMapper
import com.twitter.finatra.http.internal.exceptions.ExceptionManager
import com.twitter.finatra.http.internal.exceptions.json.{CaseClassExceptionMapper, JsonParseExceptionMapper}
import com.twitter.inject.{Injector, InjectorModule, TwitterModule}

/**
  * Created by liaochangyu on 1/11/16.
  */

object JsonExceptionMapperModule extends TwitterModule {
  override val modules = Seq(InjectorModule)

  override def configure() {
    bindSingleton[DefaultExceptionMapper].to[JsonExceptionMapper]
  }

  override def singletonStartup(injector: Injector) {
    val manager = injector.instance[ExceptionManager]
    manager.add[JsonParseExceptionMapper]
    manager.add[CaseClassExceptionMapper]
  }
}