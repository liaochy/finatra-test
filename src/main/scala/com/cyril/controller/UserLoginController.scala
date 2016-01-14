package com.cyril.controller


import javax.inject.Inject

import com.cyril.service.{RemoteService, UserLoginService}
import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller

/**
  * Created by liaochangyu on 1/7/16.
  */
class UserLoginController @Inject()(remote:RemoteService,userLoginService: UserLoginService) extends Controller {

  get("/ping") { request: Request =>
   remote.hi()
//    userLoginService.allContacts()
  }

  get("/ping2") { request: Request =>

    Seq("aaa","bbb")
  }


  get("/name") { request: Request =>
    Map("red" -> "#FF0000", "azure" -> "#F0FFFF")
//    "sde"
  }

  get("/excep") { request: Request =>
    throw new RuntimeException
  }

//  post("/foo") { request: Request =>
//    userLoginService.do (request)
//    "bar"
//  }
}
