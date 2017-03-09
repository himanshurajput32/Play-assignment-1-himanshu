import models.User
import org.scalatest.mock.MockitoSugar
import org.scalatestplus.play._
import org.scalatest._
import play.cache.CacheApi
import services.MyService
import play.api.test.Helpers.{contentAsString, contentType}

/**
  * Created by knoldus on 9/3/17.
  */
class ServicesSpec extends PlaySpec with MockitoSugar{
"UserStorage Service" should{
  "add user" in{
    val mockService=mock[MyService]
    val user=User("demo", "demo", "demo", "abc@m.com", "dddd", 1, "dd", 11, false,false)
    val result=mockService.addUser(user)

  }
}
}
