package controllers

import javax.inject._

import akka.actor.ActorSystem
import akka.stream.Materializer
import dao.DAO
import play.api.mvc._
import forms.QueryForm
import play.api.i18n.{I18nSupport, MessagesApi}

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class HomeController @Inject()(
                                val messagesApi: MessagesApi,
                                implicit val actorSystem: ActorSystem,
                                mat: Materializer,
                                executionContext: ExecutionContext,
                                implicit val environment: play.api.Environment,
                                @Named("appDAO") dao: DAO)
  extends Controller with I18nSupport {

  def index = Action { implicit request =>
    Ok(views.html.index(QueryForm.form))
  }

  /**
    * Query Option will ask the user for the country name or code and print the airports & runways at each airport.
    * The input can be country code or country name.
    * For bonus points make the test partial/fuzzy. e.g. entering zimb will result in Zimbabwe :)
    */
  def query = Action.async { implicit request =>
    QueryForm.form.bindFromRequest.fold(
      _ => Future.successful(BadRequest(s"Query shouldn't be empty!")),
      queryForm => {
        //todo get result
        Future.successful(Ok(views.html.query(queryForm.queryString)))
      }
    )


  }

  /**
    * 10 countries with highest number of airports (with count) and countries with lowest number of airports.
    *
    * Type of runways (as indicated in "surface" column) per country
    *
    * Bonus: Print the top 10 most common runway identifications (indicated in "le_ident" column)
    */
  def reports: Action[AnyContent] = Action { implicit request =>
    // todo get result
    Ok(views.html.reports())
  }

}