package controllers

import javax.inject.Inject

import play.api.mvc.{Action, Controller}
import services.UserData

/**
  * Created by knoldus on 6/3/17.
  */
class ProfileController @Inject() extends Controller {

  def logout=Action{
    Ok(views.html.login()).withNewSession
  }
def home(email:String)=Action{ implicit request=>
  val list=UserData.users.filter(_.email==email).toList
  val user=list(0).productIterator.toList
 Ok(views.html.profile(user,request))
}


}
