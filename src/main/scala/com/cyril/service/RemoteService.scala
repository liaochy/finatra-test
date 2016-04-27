package com.cyril.service

import javax.inject.Inject

import com.cyril.entity.User
import com.google.inject.Provides
import com.cyril.thriftscala.Hello
import com.twitter.finagle.kestrel.Client
import com.twitter.conversions.time._
import com.twitter.util.Future
import com.twitter.io.Buf;

/**
  * Created by liaochangyu on 1/12/16.
  */
@Provides
class RemoteService @Inject()(client: Hello.FutureIface, kestrel: Client) {


  def hi(): Future[String] = {
    client.hi
  }

  def get(): Future[Option[Buf]] = {
    kestrel.get("ss")
  }

  def put(ss:String) ={
    kestrel.set("ss",Buf.Utf8(ss))
  }
}
