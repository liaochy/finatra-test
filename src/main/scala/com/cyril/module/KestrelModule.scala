package com.cyril.module

import javax.inject.Singleton

import com.google.inject.Provides
import com.twitter.finagle.kestrel.Client
import com.twitter.inject.TwitterModule

/**
  * Created by liaochangyu on 1/12/16.
  */
object KestrelModule  extends TwitterModule{


  @Singleton
  @Provides
  def providesClient: Client = {

    Client("10.16.4.108:22133")
  }


}



