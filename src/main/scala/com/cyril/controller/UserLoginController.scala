package com.cyril.controller


import javax.inject.Inject

import com.cyril.service.{RemoteService, UserLoginService}
import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller
import com.twitter.io.Buf
import com.twitter.util.Future

/**
  * Created by liaochangyu on 1/7/16.
  */
class UserLoginController @Inject()(remote: RemoteService, userLoginService: UserLoginService) extends Controller {


  get("/ping") { request: Request =>
    info("hi")
    remote.hi()
    userLoginService.allContacts()
  }

  get("/ping3") { request: Request =>
    val f: Future[Option[Buf]] = remote.get()
    f.onSuccess {
      res: Option[Buf] =>
        res.getOrElse(None)

    }
    f.onFailure {
      cause: Throwable =>
        None
    }
  }

  get("/put") { request: Request =>
    val a = request.getParam("key")
    remote.put(a)
  }

  get("/ping2") { request: Request =>

    Seq("aaa", "bbb")
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
