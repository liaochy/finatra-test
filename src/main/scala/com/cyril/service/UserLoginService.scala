package com.cyril.service

import javax.inject.Inject
import com.cyril.dao.UserDao
import com.cyril.entity.User
import com.google.inject.Provides
import com.twitter.util.Future

/**
  * Created by liaochangyu on 1/7/16.
  */
@Provides
class UserLoginService @Inject()(userDao: UserDao) {

  def allContacts(): Future[Seq[User]]={
    userDao.allContacts()
  }
}
