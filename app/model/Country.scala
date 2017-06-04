package model

/**
  * "id" - int code
  * "code" - string
  * "name" - string
  * "continent" - string
  * "wikipedia_link" - link
  * "keywords" - string separated by comma
  */
case class Country(id: Int, code: String, name: String, continent: String, wikipediaLink: String, keywords: String) {

}
