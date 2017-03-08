package controllers

import javax.inject.Inject

import models.{User, UserData, authorizedUser}
import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc.{Action, Controller}
import services.UserStorage

/**
  * Created by knoldus on 6/3/17.
  */
class LoginController @Inject()(service:UserStorage) extends Controller {


  def login = Action {
    Ok(views.html.login())
  }
  def processform= Action { implicit request =>
    UserData.login_in.bindFromRequest.fold(
      formWithErrors => {
        Redirect(routes.LoginController.login()).flashing("msg"->"Incorrect details")
      },
      Data => {

        if(service.checkUserAvailability(Data.email)){
          Redirect(routes.LoginController.login()).flashing("msg"->"user not found")
        }

        else {
          Redirect(routes.ProfileController.home(Data.email,Data.password)).withSession("email"->Data.email)
        }
      })
  }

}
