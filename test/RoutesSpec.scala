import org.scalatestplus.play._
import play.api.test._
import play.api.test.Helpers.{contentAsString, _}
/**
  * Created by knoldus on 9/3/17.
  */
class RoutesSpec extends PlaySpec with OneAppPerTest {
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
    "Should check /logout" in  {
      val result= route(app, FakeRequest(GET, "/logout")).get
      status(result) mustBe OK
      contentType(result) mustBe Some("text/html")
      contentAsString(result) must include ("Login")
    }
  }
}
