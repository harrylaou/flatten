package flatten
import scala.concurrent.{ExecutionContext, Future}
import scalaz.{\/, \/-, _}
import Scalaz._
import scalaz.syntax.std.option._
import scalaz.syntax.std.either._
import scala.concurrent.ExecutionContext.Implicits.global
import scalaz.std.option.optionSyntax._
import scalaz.std.either._
trait Part14 extends Part13 {
  implicit val ec: ExecutionContext = scala.concurrent.ExecutionContext.Implicits.global
  // In the previous part, we had constructs like

  // OptionT(Future.successful(theThing)).

  // The `OptionT` and `Future.successful` parts are not so interesting, they're just to make the container right.

  // Scalaz has a function application operator, that reverses function and parameter.

  // This:
  val y1 = double(5)
  // Is equivalent to this:
  val y2 = 5 |> double

  // Exercise, rewrite the for-comprehension from part 13, but use `|>` for applying Future.successful and EitherT.apply

  def double(i: Int) = i * 2


  val res2: OptionT[Future, Boolean] =
    for {userName <- getUserName(data)  |> Future.successful _ |> OptionT.apply _
         user <- getUser(userName) |> OptionT.apply _
           email = getEmail(user)
         validatedEmail <- validateEmail(email) |> Future.successful _  |> OptionT.apply _
         sendEmail <- sendEmail(validatedEmail) |> OptionT.apply _
    } yield {
      sendEmail
    }

  val res3 =
    for {userName <- getUserName(data).toRight( "bla") |> \/.fromEither _ |> Future.successful _ |> EitherT.apply _
         user <- getUser(userName).map{_.toRight( "bla")|> \/.fromEither _} |> EitherT.apply _
         email = getEmail(user)
         validatedEmail <- validateEmail(email).toRight( "bla")|> \/.fromEither _|> Future.successful _  |> EitherT.apply _
         sendEmail <- sendEmail(validatedEmail).map{_ .toRight( "bla")|> \/.fromEither _} |> EitherT.apply _
    } yield {
      sendEmail
    }
}
