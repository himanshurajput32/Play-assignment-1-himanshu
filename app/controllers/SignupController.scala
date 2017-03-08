package controllers

import javax.inject.Inject

import models.{User, UserData}
import play.api.cache.CacheApi
import services.UserStorage
import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc.{Action, Controller}

/**
  * Created by knoldus on 6/3/17.
  */
class SignupController  @Inject()(service:UserStorage) extends Controller {


  def signup = Action {implicit request=>
    Ok(views.html.signup())
  }


  def processform = Action { implicit request =>
    UserData.signUp.bindFromRequest.fold(
      formWithErrors => {
        Redirect(routes.SignupController.signup()).flashing("msg"->"error occured")
      },
      Data => {
        if(!service.checkUserAvailability(Data.email)){
         Redirect(routes.SignupController.signup()).flashing("msg"->"user exists")
        }
        else {
          service.addUser(Data)
          Redirect(routes.ProfileController.home(Data.email,Data.password)).withSession("email"->Data.email)

        }
      })
  }
}
