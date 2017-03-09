
import controllers.{HomeController, LoginController, ProfileController, SignupController}
import org.scalatest.mock.MockitoSugar
import org.scalatestplus.play._
import play.api.cache.CacheApi
import play.api.test._
import play.api.test.Helpers.{contentAsString, _}
import services.MyService

/**
  * Add your spec here.
  * You can mock out a whole application including requests, plugins etc.
  * For more information, consult the wiki.
  */
class ControllerSpec extends PlaySpec with OneAppPerTest with MockitoSugar {


  "HomeController" should {

    "render the index page" in {

      val home = new HomeController
      val result = home.index().apply(FakeRequest())

      status(result) mustBe OK
      contentType(result) mustBe Some("text/html")
      contentAsString(result) must include("Welcome to Play App")
    }

  }

  "SignupController" should {

    "render signup page" in {
      val service = mock[MyService]
      val home = new SignupController(service)
      val result = home.signup().apply(FakeRequest())
      status(result) mustBe OK
      contentType(result) mustBe Some("text/html")
      contentAsString(result) must include("Create an Account")

    }

  }
  "LoginController" should {

    "render login page" in {
      val service = mock[MyService]
      val home = new LoginController(service)
      val result = home.login().apply(FakeRequest())
      status(result) mustBe OK
      contentType(result) mustBe Some("text/html")
      contentAsString(result) must include("Login")
    }

  }
  "ProfileController" should {

    "render logout page" in {
      val service = mock[MyService]
      val cache=mock[CacheApi]
      val home = new ProfileController(cache,service)
      val result = home.logout().apply(FakeRequest())
      status(result) mustBe OK
      contentType(result) mustBe Some("text/html")
      contentAsString(result) must include("Login")
    }

  }

}
