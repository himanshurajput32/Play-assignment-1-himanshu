package controllers

import javax.inject.Inject

import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc.{Action, Controller}
import services.{User, UserData, authorizedUser}

/**
  * Created by knoldus on 6/3/17.
  */
class LoginController @Inject() extends Controller {
  val login_in:Form[authorizedUser]= Form(
    mapping(
      "email" -> text,
      "password" -> text
    )(authorizedUser.apply)(authorizedUser.unapply)
  )
  def isUser(email:String):Boolean={
    val user=UserData.users.filter(_.email==email)
    if(user.isEmpty){
      return false
    }
    else
      true
  }
  def findUser(email:String):User={
    val res=UserData.users.filter(_.email==email)
    res(0)
  }
  def login = Action {
    Ok(views.html.login())
  }
  def processform= Action { implicit request =>
    login_in.bindFromRequest.fold(
      formWithErrors => {
        Redirect(routes.LoginController.login()).flashing("msg"->"Incorrect details")
      },
      Data => {

        if(!isUser(Data.email)){
          Redirect(routes.LoginController.login()).flashing("msg"->"user not found")
        }

        else {
          Redirect(routes.ProfileController.home(Data.email)).withSession("email"->Data.email)
        }
      })
  }

}
