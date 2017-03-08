package services

import javax.inject.Inject

import models.User
import play.api.cache.CacheApi

import scala.collection.mutable.ListBuffer

/**
  * Created by knoldus on 7/3/17.
  */

class UserStorage  {
val users=ListBuffer[User]()
  def checkUserAvailability(email: String): Boolean = {
    val user=users.toList.filter(_.email==email)
    if(user==Nil)
      true
    else
      false
  }

  //Encryption Method
  def md5Hash(text: String): String = java.security.MessageDigest.getInstance("MD5").digest(text.getBytes()).map(0xFF & _).map {
    "%02x".format(_)
  }.foldLeft("") {
    _ + _
  }

  def addUser(user: User):List[User] = {
    if (checkUserAvailability(user.email)) {
      val password_new = md5Hash(user.password)
      val user_new = user.copy(password = password_new)
      users +=user_new
    }
    println(users)
users.toList
  }

  def findUser(email: String):List[User]= {
    println(users)
    val user=users.filter(_.email==email)
    println(user)
    user.toList
  }

  def remove(email:String):List[User]={
    val user=users.filter(_.email==email)
    users -=user.head
   users.toList
  }

}
