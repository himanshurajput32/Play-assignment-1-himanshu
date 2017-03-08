package models
import com.google.inject.AbstractModule
import play.api.{Configuration, Environment}
import services.{MyService, UserStorage}

class GuiceBind(environment:Environment, configuration:Configuration) extends AbstractModule {
  override def configure() = {
    bind(classOf[MyService]).to(classOf[UserStorage])
  }
}