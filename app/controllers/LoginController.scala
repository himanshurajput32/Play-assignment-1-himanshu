package controllers

import javax.inject.Inject

import models.UserData
import play.api.mvc.{Action, Controller}
import services.MyService

/**
  * Created by knoldus on 6/3/17.
  */
class LoginController @Inject()(service: MyService) extends Controller {


  def login = Action { implicit request =>
    Ok(views.html.login())
  }

  def processform = Action { implicit request =>
    UserData.login_in.bindFromRequest.fold(
      formWithErrors => {
        Redirect(routes.LoginController.login()).flashing("msg" -> "Incorrect details")
      },
      Data => {

        if (!service.checkUserAvailability(Data.email)) {
          Redirect(routes.LoginController.login()).flashing("msg" -> "User not found")
        }

        else {
          Redirect(routes.ProfileController.home(Data.email, Data.password)).withSession("email" -> Data.email)
        }
      })
  }

}
