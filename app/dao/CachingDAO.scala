package dao

import javax.inject.{Inject, Named}

import model.{Airport, Country, Runaway}
import play.api.cache._
import scala.concurrent.Future

class CachingDAO @Inject()(
                            @Named("appDAO") dao: DAO,
                            cache: CacheApi
                          ) extends DAO {


  override def allAirports(): Future[List[Airport]] = {
    val data = cache.getOrElse("allAirports")(dao.allAirports())
    data
  }

  override def allCountries(): Future[List[Country]] ={
    val data = cache.getOrElse("allCountries")(dao.allCountries())
    data
  }

  override def allRunaways(): Future[List[Runaway]] = {
    val data = cache.getOrElse("allRunaways")(dao.allRunaways())
    data
  }

  override def findAirportsByName(countryCode: String): Future[List[(Airport, List[Runaway])]] = {
    val data = cache.getOrElse(countryCode)(dao.findAirportsByName(countryCode))
    data
  }

  override def allCountriesSortedByNumberOfAirports(): Future[List[(Country, Int)]] = {
    val data = cache.getOrElse("allCountriesSortedByNumberOfAirports")(dao.allCountriesSortedByNumberOfAirports())
    data
  }

  override def typeOfSurfacesPerCountry(): Future[List[(Country, List[String])]] = {
    val data = cache.getOrElse("typeOfSurfacesPerCountry")(dao.typeOfSurfacesPerCountry())
    data
  }

  override def topIdentifications(): Future[List[(String, Int)]] = {
    val data = cache.getOrElse("topIdentifications")(dao.topIdentifications())
    data
  }
}
