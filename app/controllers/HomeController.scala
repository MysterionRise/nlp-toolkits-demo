package controllers

import javax.inject._

import akka.actor.ActorSystem
import akka.stream.Materializer
import dao.DAO
import play.api.mvc._

import scala.concurrent.ExecutionContext

@Singleton
class HomeController @Inject()(implicit actorSystem: ActorSystem,
                               mat: Materializer,
                               executionContext: ExecutionContext,
                               @Named("appDAO") dao: DAO)
  extends Controller {

  def index: Action[AnyContent] = Action { implicit request =>
    Ok(views.html.index())
  }

  def query(query: String): Action[AnyContent] = Action { implicit request =>

    /**
      * Query Option will ask the user for the country name or code and print the airports & runways at each airport.
      * The input can be country code or country name.
      * For bonus points make the test partial/fuzzy. e.g. entering zimb will result in Zimbabwe :)
      */
    Ok(views.html.query())
  }

  def reports: Action[AnyContent] = Action { implicit request =>

    /**
      * 10 countries with highest number of airports (with count) and countries with lowest number of airports.
      *
      * Type of runways (as indicated in "surface" column) per country
      *
      * Bonus: Print the top 10 most common runway identifications (indicated in "le_ident" column)
      */
    Ok(views.html.reports())
  }

}