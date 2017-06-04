package module

import com.google.inject.AbstractModule
import dao.{CsvDAO, DAO}
import net.codingwell.scalaguice.ScalaModule

class BaseModule extends AbstractModule with ScalaModule {

  /**
    * Configures the module.
    */
  def configure(): Unit = {
    bind[DAO].annotatedWithName("appDAO").to[CsvDAO]
  }

}