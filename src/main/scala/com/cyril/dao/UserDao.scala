package com.cyril.dao

import com.cyril.entity.User

import javax.inject.{Inject, Singleton}
import com.google.inject.Provides
import com.twitter.finagle.exp.mysql.{ StringValue, Client,IntValue}


/**
  * Created by liaochangyu on 1/11/16.
  */

@Singleton
@Provides
class UserDao @Inject()(client:Client){

  def allContacts() = client.select[User]("select * from album limit 10"){ row=>
    val StringValue(name) = row("name").get
    val StringValue(email) = row("intro").get
    val IntValue(phone) = row("source").get
    User(name,email,phone+"")
  }

  def addContact(users: List[User])={

    val sql = """INSERT INTO `users` (name, email, phone)
         VALUES %s;""".format(users.mkString(", "))
    client.query(sql)

  }
}

