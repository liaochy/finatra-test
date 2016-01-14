package com.cyril.module

import javax.inject.Singleton

import com.google.inject.Provides
import com.twitter.inject.TwitterModule
import com.cyril.thriftscala.Hello
import com.twitter.finagle.ThriftMux


/**
  * Created by liaochangyu on 1/12/16.
  */
object RemoteModule  extends TwitterModule{


  @Singleton
  @Provides
  def providesClient: Hello.FutureIface = {
    ThriftMux.newIface[Hello.FutureIface]("zk2!10.10.76.176:2281!/mux/calc")
  }
}



