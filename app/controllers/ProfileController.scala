package controllers

import javax.inject.Inject


import models.{User, UserData}
import play.api.cache
import play.api.mvc.{Action, Controller}
import services.UserStorage

/**
  * Created by knoldus on 6/3/17.
  */
class ProfileController @Inject()(service:UserStorage) extends Controller {

  def logout=Action{
    Ok(views.html.login()).withNewSession
  }

  ///////////////////////////////////////////////
  def adminHome=Action{implicit request=>
    Ok(views.html.maintenance(service.users.toList,request))
  }

  def remove(email:String)=Action{implicit request=>
    service.remove(email)
    Redirect(routes.ProfileController.adminHome())
  }
  /////////////////////////////////////////////////
def home(email:String)=Action { implicit request =>

  if (service.findUser(email)!=Nil) {
    val user = service.findUser(email).head

    val list = user.productIterator.toList

    Ok(views.html.profile(list, request))
  }
  else {
    Ok("some error Occured")
  }
}


}
