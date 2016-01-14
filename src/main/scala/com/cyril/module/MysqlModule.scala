package com.cyril.module

import com.google.inject.Provides
import com.twitter.finagle.client.DefaultPool
import com.twitter.finagle.exp.Mysql
import com.twitter.finagle.exp.mysql.Client
import com.twitter.inject.TwitterModule
import javax.inject.{Inject, Singleton}
import com.twitter.conversions.time._


/**
  * Created by liaochangyu on 1/11/16.
  */
object MysqlModule extends TwitterModule{
  @Singleton
  @Provides
  def providesClient: Client = {
    Mysql.client
      .withCredentials("platform", "xk0LGa_Iy224Drl")
      .withDatabase("platform")
      .configured(DefaultPool.Param(
        low = 0, high = 10,
        idleTime = 5.minutes,
        bufferSize = 0,
        maxWaiters = Int.MaxValue))
      .newRichClient("10.10.57.243:3306")
  }
}

