package dao

import java.io.File

import model.{Airport, Country, Runaway}
import com.github.tototoshi.csv._

import scala.concurrent.Future

class CsvDAO extends DAO {

  lazy val airports = CSVReader.open(new File("airports.csv"))
  lazy val runways = CSVReader.open(new File("runways.csv"))
  lazy val countries = CSVReader.open(new File("countries.csv"))

  private def createAirportFromList(l: List[String]): Airport = {
    l match {
      case List(id: Int, ident: String, airportType: String, name: String, lat: Double, lon: Double, elevation:
        Int, continent: String, isoCountry: String, isoRegion: String, municipality: String, scheduledService: String,
      gpsCode: String, iataCode: String, homeLink: String, wikiLink: String, keywords: List[String]) => {
        Airport()
      }
    }
  }

  override def allAirports(): Future[List[Airport]] = airports.all().map(airport => Airport.tupled(airport))

  override def allCountries(): Future[List[Country]] = ???

  override def allRunaways(): Future[List[Runaway]] = ???

  override def findAirportsByCountryCode(countryCode: String): Future[List[(Airport, List[Runaway])]] = ???

  override def findAirportsByCountryName(countryName: String): Future[List[(Airport, List[Runaway])]] = ???

  override def allCountriesSortedByNumberOfAirports(): Future[List[(Country, Int)]] = ???

  override def typeOfSurfacesPerCountry(): Future[List[(Country, List[String])]] = ???
}
