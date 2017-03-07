package controllers

import javax.inject.Inject
import services.{User, UserData}
import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc.{Action, Controller}

/**
  * Created by knoldus on 6/3/17.
  */
class SignupController  @Inject() extends Controller {
  val signUp: Form[User] = Form(
    mapping(
      "firstname" -> text,
      "middlename" -> text,
      "lastname" -> text,
      "email" -> text,
      "password" -> text,
      "mobile" -> number,
      "gender" -> text,
      "age" -> number
    )(User.apply)(User.unapply)
  )

  def signup = Action {implicit request=>
    Ok(views.html.signup())
  }
  def isUser(email:String):Boolean={
    val user=UserData.users.filter(_.email==email)
    if(user.isEmpty){
      return false
    }
    else
      true
  }

  def processform = Action { implicit request =>
    signUp.bindFromRequest.fold(
      formWithErrors => {
        Redirect(routes.SignupController.signup())
      },
      Data => {
        if(isUser(Data.email)){
         Redirect(routes.SignupController.signup()).flashing("msg"->"user exists")
        }
        else {
          UserData.users.append(Data)
          Redirect(routes.ProfileController.home(Data.email)).withSession("email"->Data.email)
         // Ok(views.html.profile(list,request)).withSession("email" -> Data.email)
        }
      })
  }
}
