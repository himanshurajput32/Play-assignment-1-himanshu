import models.User
import org.scalatest.mock.MockitoSugar
import org.scalatestplus.play._
import org.scalatest._
import services.MyService
import play.api.test.Helpers._
import org.mockito.Mockito._
import play.api.cache.CacheApi

/**
  * Created by knoldus on 9/3/17.
  */
class ServicesSpec extends PlaySpec with MockitoSugar{

"UserStorage Service" should{
  "add user" in{
    val user=User("demo", "demo", "demo", "abc@m.com", "dddd", 1, "dd", 11, false,false)
    val mockService=mock[MyService]
    val cache=mock[CacheApi]
    mockService.addUser(user)

    when(cache.get[User]("abc@m.com")) thenReturn Option(User("demo", "demo", "demo", "abc@m.com", "dddd", 1, "dd", 11, false,false))


  }
  "Check Existing User" in{
    val mockService=mock[MyService]
    when(mockService.checkUserAvailability("abc@gmail.com")) thenReturn false

  }
  "Find a User" in{
    val mockService=mock[MyService]
    val user=User("demo", "demo", "demo", "abc@m.com", "dddd", 1, "dd", 11, false,false)
    val cache=mock[CacheApi]
    cache.set(user.email,user)
    when(mockService.findUser(user.email)) thenReturn User("demo", "demo", "demo", "abc@m.com", "dddd", 1, "dd", 11, false,false)

  }
  
}

}
