package com.cyril.service

import javax.inject.Inject

import com.cyril.entity.User
import com.google.inject.Provides
import com.cyril.thriftscala.Hello
import com.twitter.util.Future

/**
  * Created by liaochangyu on 1/12/16.
  */
@Provides
class RemoteService @Inject()(client :Hello.FutureIface) {


  def hi(): Future[String]={
    client.hi
  }
}
