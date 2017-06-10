package dao

import model.{Airport, Country, Runaway}
import com.github.tototoshi.csv._

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.io.Source

class CsvDAO extends DAO {

  lazy val airports = CSVReader.open(Source.fromFile("C:\\Development\\projects\\airport-dangerzone\\public\\data\\airports.csv", "UTF-8")).all().drop(1)
  lazy val countries = CSVReader.open(Source.fromFile("C:\\Development\\projects\\airport-dangerzone\\public\\data\\countries.csv", "UTF-8")).all().drop(1)
  lazy val runways = CSVReader.open(Source.fromFile("C:\\Development\\projects\\airport-dangerzone\\public\\data\\runways.csv", "UTF-8")).all().drop(1)

  private def createAirportFromList(params: List[String]): Airport = {
    params match {
      case List(id: String, ident: String, airportType: String, name: String, lat: String, lon: String, elevation: String,
      continent: String, isoCountry: String, isoRegion: String, municipality: String, scheduledService: String,
      gpsCode: String, iataCode: String, localCode: String, homeLink: String, wikiLink: String, keywords: String) => {
        Airport(parseInt(id), ident, airportType, name, parseDouble(lat), parseDouble(lon), parseInt(elevation),
          continent, isoCountry, isoRegion, municipality, scheduledService, gpsCode, iataCode, localCode, homeLink, wikiLink, keywords.split(" ").toList)
      }
    }
  }

  private def createCountryFromList(params: List[String]): Country = {
    params match {
      case List(id: String, code: String, name: String, continent: String, wikipediaLink: String, keywords: String) => {
        Country(parseInt(id), code, name, continent, wikipediaLink, keywords.split(" ").toList)
      }
    }
  }

  def parseBoolean(boolean: String): Boolean = boolean == "1"

  def parseDouble(double: String): Double = {
    if (double.isEmpty)
      0
    else java.lang.Double.parseDouble(double)
  }

  def parseInt(int: String): Int = {
    if (int.isEmpty)
      0
    else java.lang.Integer.parseInt(int)
  }

  private def createRunwayFromList(params: List[String]): Runaway = {
    params match {
      case List(id: String, airportRef: String, airportIdent: String, length: String, width: String, surface: String,
      lighted: String, closed: String, leIdent: String, leLat: String, leLon: String,
      leElevation: String, leHeading: String, leDisplacedThreshold: String, heIdent: String,
      heLat: String, heLon: String, heElevation: String, heHeading: String, heDisplacedThreshold: String) => {
        Runaway(parseInt(id), parseInt(airportRef), airportIdent, parseInt(length), parseInt(width), surface,
          parseBoolean(lighted), parseBoolean(closed), leIdent, parseDouble(leLat), parseDouble(leLon),
          parseInt(leElevation), parseDouble(leHeading), parseInt(leDisplacedThreshold), heIdent, parseDouble(heLat),
          parseDouble(heLon), parseInt(heElevation), parseDouble(heHeading), parseInt(heDisplacedThreshold))
      }
    }
  }

  override def allAirports(): Future[List[Airport]] = Future.successful(airports.map(airport => createAirportFromList(airport)))

  override def allCountries(): Future[List[Country]] = Future.successful(countries.map(country => createCountryFromList(country)))

  override def allRunaways(): Future[List[Runaway]] = Future.successful(runways.map(runway => createRunwayFromList(runway)))

  override def findAirportsByName(query: String): Future[List[(Airport, List[Runaway])]] = {
    for {
      q <- findCountryCodeByCountryName(query)
    } yield findAirportsByListOfCountryCodes(q)
  }

  private def findAirportByName(name: String): List[(Airport, List[Runaway])] = {
    val data = for {
      airports <- allAirports()
      runways <- allRunaways()
    } yield (airports.filter(airport => airport.isoCountry.equalsIgnoreCase(name) || airport.isoCountry.contains(name)), runways)
    for {
      d <- data
    } yield (d._1.map(a => (a, d._2.filter(_.airportRef == a.id))))
  }

  private def findAirportsByListOfCountryCodes(countryCodes: List[String]): List[(Airport, List[Runaway])] = {
    val x = countryCodes.foldLeft[List[(Airport, List[Runaway])]](List())((list, cc) => list ++ findAirportByName(cc))

  }

  private def findCountryCodeByCountryName(name: String): Future[List[String]] = {
    val countries = for {
      country <- allCountries()
    } yield (country.filter(
      c => c.name.toLowerCase.contains(name.toLowerCase()) ||
        c.code.equalsIgnoreCase(name) ||
        c.code.toLowerCase.contains((name))
    ).map(c => c.code))
    countries
  }

  override def allCountriesSortedByNumberOfAirports(): Future[List[(Country, Int)]] = ???

  override def typeOfSurfacesPerCountry(): Future[List[(Country, List[String])]] = ???
}
