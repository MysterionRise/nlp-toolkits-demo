package dao

import model.{Airport, Country, Runaway}
import com.github.tototoshi.csv._

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import play.Play

class CsvDAO extends DAO {

  private lazy val airports = CSVReader.open(Play.application().getFile("conf\\data\\airports.csv"), "UTF-8").all().drop(1).map(airport => createAirportFromList(airport))
  private lazy val countries = CSVReader.open(Play.application().getFile("conf\\data\\countries.csv"), "UTF-8").all().drop(1).map(country => createCountryFromList(country))
  private lazy val runways = CSVReader.open(Play.application().getFile("conf\\data\\runways.csv"), "UTF-8").all().drop(1).map(runway => createRunwayFromList(runway))

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

  override def allAirports(): Future[List[Airport]] = Future.successful(airports)

  override def allCountries(): Future[List[Country]] = Future.successful(countries)

  override def allRunaways(): Future[List[Runaway]] = Future.successful(runways)

  override def findAirportsByName(query: String): Future[List[(Airport, List[Runaway])]] = {
    Future.successful(findCountryCodeByCountryName(query).map(code => findAirportByName(code)).flatten)
  }

  private def findAirportByName(name: String): List[(Airport, List[Runaway])] = {
    for {
      airport <- airports
      if (airport.isoCountry.equalsIgnoreCase(name) || airport.isoCountry.toLowerCase.contains(name.toLowerCase))
    } yield (airport, runways.filter(_.airportRef == airport.id))
  }

  private def findCountryCodeByCountryName(name: String): List[String] = {
    countries.map(c => (c.code, c.name)).toSet.filter(c =>
      c._2.toLowerCase.contains(name.toLowerCase()) ||
        c._1.equalsIgnoreCase(name) ||
        c._1.toLowerCase.contains((name))).map(_._1).toList
  }

  override def allCountriesSortedByNumberOfAirports(): Future[List[(Country, Int)]] = {
    val res = for {
      country <- countries
    } yield (country, airports.filter(_.isoCountry == country.code).length)
    Future.successful(res.sortBy(-_._2))
  }

  override def typeOfSurfacesPerCountry(): Future[List[(Country, List[String])]] = {
    val res = for {
      country <- countries
      a = airports.filter(_.isoCountry == country.code).map(_.id).toSet
    } yield (country, runways.filter(r => a.contains(r.airportRef)).map(r => r.surface).filterNot(_.isEmpty).toSet.toList)
    Future.successful(res)
  }

  override def topIdentifications(): Future[List[(String, Int)]] = {
    Future.successful(runways.groupBy(_.leIdent).map(x => (x._1, x._2.length)).toList.sortBy(-_._2).map(x => (x._1, x._2)).take(10))
  }
}
