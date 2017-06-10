package model

/**
  * "id",
  * "ident",
  * "type",
  * "name",
  * "latitude_deg",
  * "longitude_deg",
  * "elevation_ft",
  * "continent",
  * "iso_country",
  * "iso_region",
  * "municipality",
  * "scheduled_service",
  * "gps_code",
  * "iata_code",
  * "local_code",
  * "home_link",
  * "wikipedia_link",
  * "keywords"
  *
  */
case class Airport(id: Int, ident: String, airportType: String, name: String, lat: Double, lon: Double, elevation:
Int, continent: String, isoCountry: String, isoRegion: String, municipality: String, scheduledService: String,
                   gpsCode: String, iataCode: String, localCode: String, homeLink: String, wikiLink: String, keywords: List[String]) {

}
