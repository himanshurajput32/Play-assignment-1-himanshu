package services

import javax.inject.Inject

import models.{User, UserData}
import play.api.cache
import play.api.cache.CacheApi

import scala.collection.mutable.ListBuffer
import scala.concurrent.duration.Duration

/**
  * Created by knoldus on 7/3/17.
  */
trait MyService {
  def checkUserAvailability(email: String): Boolean

  def md5Hash(text: String): String

  def addUser(user: User): Boolean

  def findUser(email: String): User

  def suspend(email: String): Boolean

  def resume(email: String): Boolean
}


class UserStorage @Inject()(cache: CacheApi) extends MyService {

  def checkUserAvailability(email: String): Boolean = {
    val user = cache.get[User](email)
    if (user == Some(User)) {
      false
    }
    else
      true
  }

  //Encryption Method
  def md5Hash(text: String): String = java.security.MessageDigest.getInstance("MD5").digest(text.getBytes()).map(0xFF & _).map {
    "%02x".format(_)
  }.foldLeft("") {
    _ + _
  }

  def addUser(user: User):Boolean = {
    if (checkUserAvailability(user.email)) {
      println(user)
      val user_new = user.copy(password = md5Hash(user.password))
      println(user_new)
      UserData.userEmails.append(user.email)
      cache.set(user.email, user_new)
      println(cache)
      true
    }
    else
      false
  }

  def findUser(email: String): User = {
    val userDemo = User("demo", "demo", "demo", "abc@m.com", "dddd", 1, "dd", 11, false, false)
    val user = cache.getOrElse[User](email)(userDemo)
    println(user)
    user
  }

  def suspend(email: String): Boolean = {
    val user = cache.getOrElse[User](email)(User("demo", "demo", "demo", "abc@m.com", "dddd", 1, "dd", 11, false, false))
    val user_new = user.copy(isSuspend = true)
    cache.remove(email)
    cache.set(email, user_new)
    true
  }

  def resume(email: String): Boolean = {
    val user = cache.getOrElse[User](email)(User("demo", "demo", "demo", "abc@m.com", "dddd", 1, "dd", 11, false, false))
    val user_new = user.copy(isSuspend = false)
    cache.remove(email)
    cache.set(email, user_new)
    true
  }
}

