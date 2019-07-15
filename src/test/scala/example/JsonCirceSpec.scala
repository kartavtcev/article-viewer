package example

import example.domain._
import io.circe.generic.auto._
import io.circe.parser._
import org.scalatest._

// TODO: move jsons to resources file!
class JsonCirceSpec extends FlatSpec with Matchers {
  "Sample jsons" should "be parsed to domain models with circe properly" in {
    val articleListJson =
      """{"total_pages":1,"total_entries":2,"page_size":100,"page_number":1,"articles":[{"updated_at":"2019-07-10T08:57:32Z","translations":[{"updated_at":"2019-07-10T08:47:55Z","title":"Introducing our in-app help","language_id":"en","id":4}],"title":"Introducing our in-app help","subcategory_groups":[],"status":"published","source":"custom","smart_groups":[],"revision_status":"wip","order":1,"notes":"This is just a sample article to get you going, feel free to edit or remove this article","keywords":[],"id":1,"has_revision":true,"external_id":null,"editor_version":"2","category_id":1,"category_groups":[],"article_groups":[],"access_emails":[],"access_domains":[],"access":"public"},{"updated_at":"2019-07-10T09:04:25Z","translations":[{"updated_at":"2019-07-10T08:47:55Z","title":"Need a hand?","language_id":"en","id":5}],"title":"Need a hand?","subcategory_groups":[],"status":"published","source":"custom","smart_groups":[],"revision_status":"wip","order":2,"notes":null,"keywords":[],"id":2,"has_revision":true,"external_id":null,"editor_version":"2","category_id":1,"category_groups":[],"article_groups":[],"access_emails":[],"access_domains":[],"access":"public"}]} """

    val resultListAllArticles = decode[ArticleList](articleListJson)
    resultListAllArticles.isRight shouldEqual(true)


    val articleDetailsJson =
      """{"article":{"updated_at":"2019-07-10T09:04:25Z","translations":[{"updated_at":"2019-07-10T08:47:55Z","title":"Need a hand?","summary":null,"machine_summary":null,"language_id":"en","id":5,"featured_image_url":null,"created_at":"2019-07-10T08:47:55Z","body":"<p>Don't forget, we're always here to help. If you get stuck or have any questions, be sure to let us know and we can help you with whatever you need.</p><p>We're here to help</p>"}],"title":"Need a hand?","status":"published","source":"custom","smart_groups":[],"revision":{"updated_at":"2019-07-10T09:04:25Z","translations":[{"updated_at":"2019-07-10T09:04:25Z","title":"Need a hand?","summary":null,"machine_summary":"Don't forget, we're always here to help. If you get stuck or have any questions, be sure to let us know and we can help you with whatever you need. We're here to help ","language_id":"en","id":5,"featured_image_url":null,"created_at":"2019-07-10T09:04:25Z","body":"<p>Don't forget, we're always here to help. If you get stuck or have any questions, be sure to let us know and we can help you with whatever you need.</p><p>We're here to help</p>"}],"status":"wip","source":"custom","smart_groups":[],"order":2,"notes":null,"last_publisher":null,"last_published_at":null,"keywords":[],"id":2,"external_id":null,"editor_version":"2","created_at":"2019-07-10T09:04:25Z","contributors":[{"name":"Sergei Kartavtcev","id":13069,"gravatar":"https://www.gravatar.com/avatar/138e68f7821253f8240553769c91a4a9?e=kartavtcev.sergei@gmail.com&s=100&d=https://static.elev.io/img/elevio-fallback-avatar.png","email":"kartavtcev.sergei@gmail.com"}],"category_id":1,"author":{"name":"Safwan Kamarrudin","id":5103,"gravatar":"https://www.gravatar.com/avatar/657bce6ec668f8846af13cb502581ec1?e=safwan@elev.io&s=300&d=https%3A%2F%2Fui-avatars.com%2Fapi%2F/Safwan%2BKamarrudin/256/2F9AFF/FFF/2/0.40","email":"safwan@elev.io"},"access_groups":[],"access_emails":[],"access_domains":[],"access":"public"},"order":2,"notes":null,"last_publisher":null,"last_published_at":null,"keywords":[],"id":2,"external_id":null,"editor_version":"2","created_at":"2019-07-10T08:47:55Z","contributors":[],"category_id":1,"author":{"name":"Safwan Kamarrudin","id":5103,"gravatar":"https://www.gravatar.com/avatar/657bce6ec668f8846af13cb502581ec1?e=safwan@elev.io&s=300&d=https%3A%2F%2Fui-avatars.com%2Fapi%2F/Safwan%2BKamarrudin/256/2F9AFF/FFF/2/0.40","email":"safwan@elev.io"},"access_groups":[],"access_emails":[],"access_domains":[],"access":"public"}}"""

    val resultArticleDetails = decode[ArticleDetails](articleDetailsJson)
    resultArticleDetails.isRight shouldEqual(true)
  }
}