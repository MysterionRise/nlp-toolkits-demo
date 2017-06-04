package forms

import play.api.data.Form
import play.api.data.Forms._

object QueryForm {

  /**
    * A play framework form.
    */
  val form = Form(
    mapping(
      "queryString" -> nonEmptyText
    )(Query.apply)(Query.unapply)
  )

  /**
    * The form data.
    *
    * @param queryString Country code or country name
    */
  case class Query(queryString: String)

}
