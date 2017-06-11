package dao

import model.{Airport, Country, Runaway}

import scala.concurrent.Future

trait DAO {

  def allAirports(): Future[List[Airport]]

  def allCountries(): Future[List[Country]]

  def allRunaways(): Future[List[Runaway]]

  def findAirportsByName(countryCode: String): Future[List[(Airport, List[Runaway])]]

  def allCountriesSortedByNumberOfAirports(): Future[List[(Country, Int)]]

  def typeOfSurfacesPerCountry(): Future[List[(Country, List[String])]]

  def topIdentifications(): Future[List[(String, Int)]]

}
