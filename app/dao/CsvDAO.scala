package dao

import model.{Airport, Country, Runaway}

import scala.concurrent.Future

class CsvDAO extends DAO {

  override def allAirports(): Future[List[Airport]] = ???

  override def allCountries(): Future[List[Country]] = ???

  override def allRunaways(): Future[List[Runaway]] = ???

  override def findAirportsByCountryCode(countryCode: String): Future[List[(Airport, List[Runaway])]] = ???

  override def findAirportsByCountryName(countryName: String): Future[List[(Airport, List[Runaway])]] = ???

  override def allCountriesSortedByNumberOfAirports(): Future[List[(Country, Int)]] = ???

  override def typeOfSurfacesPerCountry(): Future[List[(Country, List[String])]] = ???
}
