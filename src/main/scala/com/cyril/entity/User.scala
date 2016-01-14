package com.cyril.entity

/**
  * Created by liaochangyu on 1/11/16.
  */
case class User(  name:String,  email:String ,  phone:String) {
  override def toString = {
    def q(s: String) = "'" + s + "'"
    "(" + q(name) + "," + q(email) + "," + q(phone) + ")"
  }
}
