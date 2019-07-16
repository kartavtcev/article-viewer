package example.view

import cats.data.NonEmptyList
import example.services.CommandParamTitles

import scala.util.Try

object ConsoleCommands {
  def help: String = {
    s"""Input format: \ncommand --parameter1 value1,value2 \n
Available commands: article_list, article_search, article_details \n
article_list parameters: --page 1 --page_size 10 --status published \n
article_search parameters: --lang_code en --query keyword1,keyword2 \n
article_details parameters: --id 1 \n
:""".stripMargin
  }

  def parseCommandWithParams(input: String): Try[ApiCommand] = { // Error output...
    def findParameterWithValues(values: List[String], paramName: String): Option[List[String]] = {
      val result = values.find(_.contains(paramName + ' ')).map(_.split(Array(' ', ',')).filter(_ != "").toList)
      result.map(_.tail)
    }

    Try {
      val splitted = input.toLowerCase().split("--").filter(_ != "").toList
      if (splitted.length > 0) {
        splitted(0).replaceAll("\\s", "") match {
          case CommandParamTitles.articleList => {
            if (splitted.length > 1) {
              val page = findParameterWithValues(splitted.tail, CommandParamTitles.paramPage)
              val pageSize = findParameterWithValues(splitted.tail, CommandParamTitles.paramPageSize)
              val status = findParameterWithValues(splitted.tail, CommandParamTitles.paramStatus)

              ArticleListCommand(page.map(_(0).toInt), pageSize.map(_(0).toInt), status.map(_(0)))
            } else {
              ArticleListCommand()
            }
          }
          case CommandParamTitles.articleDetails => {
            val id = findParameterWithValues(splitted.tail, CommandParamTitles.paramId)

            ArticleDetailsCommand(id.get(0).toInt) // throws
          }
          case CommandParamTitles.articleSearchByKeyword => {

            val lang = findParameterWithValues(splitted.tail, CommandParamTitles.paramLangCode)
            val query = findParameterWithValues(splitted.tail, CommandParamTitles.paramQuery)

            ArticleSearchByKeyword(
              NonEmptyList.of(query.map(_.mkString(",")).get),
              lang.map(_(0)).getOrElse(CommandParamTitles.langCodeDefault))
          }
          case _ => throw new NotImplementedError()
        }
      } else {
        throw new NotImplementedError()
      }
    }
  }
}

sealed trait ApiCommand
case class ArticleListCommand(page: Option[Int] = None, pageSize: Option[Int] = None, status: Option[String] = None) extends ApiCommand
case class ArticleDetailsCommand(id: Int) extends ApiCommand
case class ArticleSearchByKeyword(query: NonEmptyList[String], langCode: String = CommandParamTitles.langCodeDefault) extends ApiCommand
