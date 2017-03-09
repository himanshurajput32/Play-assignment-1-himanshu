package models

import play.api.data.Form
import play.api.data.Forms._

import scala.collection.mutable.ListBuffer


/**
  * Created by knoldus on 6/3/17.
  */
case class authorizedUser(email: String, password: String)

case class User(firstname: String, middlename: String, lastname: String, email: String, password: String, mobile: Int, gender: String, age: Int, userRole: Boolean,isSuspend:Boolean)

object UserData {
  val signUp: Form[User] = Form(
    mapping(
      "firstname" -> text,
      "middlename" -> text,
      "lastname" -> text,
      "email" -> text,
      "password" -> text,
      "mobile" -> number,
      "gender" -> text,
      "age" -> number,
      "userRole" -> boolean,
      "isSuspend"->boolean
    )(User.apply)(User.unapply)
  )
  val login_in: Form[authorizedUser] = Form(
    mapping(
      "email" -> text,
      "password" -> text
    )(authorizedUser.apply)(authorizedUser.unapply)
  )
  val userEmails=new ListBuffer[String]()
}
