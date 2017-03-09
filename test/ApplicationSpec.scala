
import org.scalatestplus.play._
import play.api.test._
import play.api.test.Helpers.{contentAsString, _}

/**
 * Add your spec here.
 * You can mock out a whole application including requests, plugins etc.
 * For more information, consult the wiki.
 */
class ApplicationSpec extends PlaySpec with OneAppPerTest {

  "Routes" should {

    "send 404 on a bad request" in  {
      route(app, FakeRequest(GET, "/boum")).map(status(_)) mustBe Some(NOT_FOUND)
    }

    "Should test /login" in  {
     val result= route(app, FakeRequest(GET, "/login")).get
      status(result) mustBe OK
      contentType(result) mustBe Some("text/html")
      contentAsString(result) must include ("Login")
    }

    "Should check /signup" in  {
      val result= route(app, FakeRequest(GET, "/signup")).get
      status(result) mustBe OK
      contentType(result) mustBe Some("text/html")
      contentAsString(result) must include ("Create an Account")
    }

  }

  "HomeController" should {

    "render the index page" in {
      val home = route(app, FakeRequest(GET, "/")).get

      status(home) mustBe OK
      contentType(home) mustBe Some("text/html")
      contentAsString(home) must include ("Welcome to Play App")
    }

  }

  "SignupController" should {

    "render signup page" in {
      status(route(app, FakeRequest(GET, "/signup")).get) mustBe OK
      contentAsString(route(app, FakeRequest(GET, "/signup")).get) must include("Create an Account")
    }

  }
  "LoginController" should {

    "render login page" in {
      status(route(app, FakeRequest(GET, "/login")).get) mustBe OK
      contentAsString(route(app, FakeRequest(GET, "/login")).get) must include("Login")
    }

  }
  "ProfileController" should {

    "render logout page" in {
      status(route(app, FakeRequest(GET, "/logout")).get) mustBe OK
      contentAsString(route(app, FakeRequest(GET, "/login")).get) must include("Login")
    }

  }

}
