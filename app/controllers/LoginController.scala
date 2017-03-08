package controllers

import javax.inject.Inject

import models.UserData
import play.api.Configuration
import play.api.mvc.{Action, Controller}
import services.{MyService, UserStorage}
/**
  * Created by knoldus on 6/3/17.
  */
class LoginController @Inject()(service:MyService) extends Controller {


  def login = Action {
    Ok(views.html.login())
  }
  def processform= Action { implicit request =>
    UserData.login_in.bindFromRequest.fold(
      formWithErrors => {
        Redirect(routes.LoginController.login()).flashing("msg"->"Incorrect details")
      },
      Data => {

        if(!service.checkUserAvailability(Data.email)){
          println("error 2")
          Redirect(routes.LoginController.login()).flashing("msg"->"user not found")
        }

        else {
          println("error 3")
          Redirect(routes.ProfileController.home(Data.email,Data.password)).withSession("email"->Data.email)
        }
      })
  }

}
