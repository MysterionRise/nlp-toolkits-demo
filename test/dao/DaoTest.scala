package dao

import org.scalatestplus.play.{OneAppPerTest, PlaySpec}

import scala.concurrent.Await
import scala.concurrent.duration.Duration

class DaoTest extends PlaySpec with OneAppPerTest {

  "CSV DAO" should {

    "number of runways should be equal to 39536" in {
      val csvDAO = new CsvDAO
      val res = Await.result(csvDAO.allRunaways(), Duration.create(10, scala.concurrent.duration.SECONDS))
      res.length mustBe 39536
    }

    "number of airports should be equal to 46505" in {
      val csvDAO = new CsvDAO
      val res = Await.result(csvDAO.allAirports(), Duration.create(10, scala.concurrent.duration.SECONDS))
      res.length mustBe 46505
    }

    "number of countries should be equal to 247" in {
      val csvDAO = new CsvDAO
      val res = Await.result(csvDAO.allCountries(), Duration.create(10, scala.concurrent.duration.SECONDS))
      res.length mustBe 247
    }

    "number of airports for russia should be equal to 920" in {
      val csvDAO = new CsvDAO
      val res = Await.result(csvDAO.findAirportsByName("russia"), Duration.create(10, scala.concurrent.duration.SECONDS))
      res.length mustBe 920
    }

    "number of airports for country should be equal to both lowercase and uppercase" in {
      val csvDAO = new CsvDAO
      val res1 = Await.result(csvDAO.findAirportsByName("netherlands"), Duration.create(10, scala.concurrent.duration.SECONDS))
      val res2 = Await.result(csvDAO.findAirportsByName("NETHERLANDS"), Duration.create(10, scala.concurrent.duration.SECONDS))
      res1.length mustBe res2.length
    }

    "number of airports for top-3 countries" in {
      val csvDAO = new CsvDAO
      val res = Await.result(csvDAO.allCountriesSortedByNumberOfAirports(), Duration.create(10, scala.concurrent.duration.SECONDS))
      res.length mustBe 247
      res.apply(0)._1.code mustBe "US"
      res.apply(0)._2 mustBe 21501
      res.apply(1)._1.code mustBe "BR"
      res.apply(1)._2 mustBe 3839
      res.apply(2)._1.code mustBe "CA"
      res.apply(2)._2 mustBe 2454
    }
  }

}
