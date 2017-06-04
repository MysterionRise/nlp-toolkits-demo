package dao

import model.{Airport, Country, Runaway}

import scala.concurrent.Future

trait DAO {

  def allAirports(): Future[List[Airport]]

  def allCountries(): Future[List[Country]]

  def allRunaways(): Future[List[Runaway]]

  def findAirportsByCountryCode(countryCode: String): Future[List[(Airport, List[Runaway])]]

  def findAirportsByCountryName(countryName: String): Future[List[(Airport, List[Runaway])]]

  def allCountriesSortedByNumberOfAirports(): Future[List[(Country, Int)]]

  def typeOfSurfacesPerCountry(): Future[List[(Country, List[String])]]

}
