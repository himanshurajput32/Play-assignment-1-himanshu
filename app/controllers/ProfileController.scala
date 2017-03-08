package controllers

import javax.inject.Inject


import models.{User, UserData}
import play.api.cache
import play.api.mvc.{Action, Controller}
import services.UserStorage

/**
  * Created by knoldus on 6/3/17.
  */
class ProfileController @Inject()(service: UserStorage) extends Controller {

  def logout = Action {
    Ok(views.html.login()).withNewSession
  }

  ///////////////////////////////////////////////
  def adminHome = Action { implicit request =>
    Ok(views.html.maintenance(service.users.toList, request))
  }

  def remove(email: String) = Action { implicit request =>
    service.remove(email)
    Redirect(routes.ProfileController.adminHome())
  }

  /////////////////////////////////////////////////
  def home(email: String, password: String) = Action { implicit request =>

    if (service.findUser(email) != Nil) {
      val user = service.findUser(email).head
      if (user.password == password) {
        val list = user.productIterator.toList
        println(list)
        Ok(views.html.profile(list, request))
      }
      else
        Redirect(routes.LoginController.login()).flashing("msg" -> "pssword or email incorrect")
    }
    else
      Redirect(routes.LoginController.login()).flashing("msg" -> "User does not exists")


  }


}
