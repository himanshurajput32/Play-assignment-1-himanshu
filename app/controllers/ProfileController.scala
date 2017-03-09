package controllers

import javax.inject.Inject

import models.{User, UserData}
import play.api.cache.CacheApi
import play.api.mvc.{Action, Controller}
import services.{MyService}

import scala.collection.mutable.ListBuffer

/**
  * Created by knoldus on 6/3/17.
  */
class ProfileController @Inject()(cache: CacheApi, service: MyService) extends Controller {

  def logout = Action {implicit request=>
    Ok(views.html.login()).withNewSession
  }


  def adminHome = Action { implicit request =>
    val userList = UserData.userEmails.toList
    val users = new ListBuffer[User]()
    for (ls <- userList) {
      users += cache.get[User](ls).get
    }
    Ok(views.html.maintenance(users.toList, request))
  }

  def suspend(email: String) = Action { implicit request =>
    service.suspend(email)
    Redirect(routes.ProfileController.adminHome())
  }
  def resume(email:String)=Action{implicit request=>
    service.resume(email)
    Redirect(routes.ProfileController.adminHome())

  }


  def home(email: String, password: String) = Action { implicit request =>

    if (service.findUser(email) != User("demo", "demo", "demo", "abc@m.com", "dddd", 1, "dd", 11, false,false)) {
      val user = service.findUser(email)
      if (user.password == service.md5Hash(password)) {
        if(user.isSuspend==true){
          Redirect(routes.LoginController.login()).flashing("msg"->"User Suspended")
        }
        else {
          val list = user.productIterator.toList
          Ok(views.html.profile(list, request))
        }
      }
      else {
        Redirect(routes.LoginController.login()).flashing("msg" -> "pssword or email incorrect")
      }
    }
    else {
      Redirect(routes.LoginController.login()).flashing("msg"->"User not found")
    }

  }


}
