package module

import com.google.inject.AbstractModule
import dao.{CachingDAO, CsvDAO, DAO}
import net.codingwell.scalaguice.ScalaModule

class BaseModule extends AbstractModule with ScalaModule {

  /**
    * Configures the module.
    */
  def configure(): Unit = {
    bind[DAO].annotatedWithName("appDAO").to[CsvDAO]
    bind[DAO].annotatedWithName("cachedDAO").to[CachingDAO]
  }

}