package services

import scala.collection.mutable.ListBuffer

/**
  * Created by knoldus on 6/3/17.
  */
case class authorizedUser(email:String,password:String)
case class User(firstname:String,middlename:String,lastname:String,email:String,password:String,mobile:Int,gender:String,age:Int)
object UserData {
  val users=ListBuffer[User]()

}
