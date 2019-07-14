package example.infra

import example.domain._
import io.circe.generic.semiauto._
import io.circe.{Decoder, Encoder}

object JsonModule {
  implicit lazy val decodeAuthor: Decoder[Author] = deriveDecoder
  implicit lazy val encodeAuthor: Encoder[Author] = deriveEncoder

  implicit lazy val decodeSmartGroups: Decoder[Smart_groups] = deriveDecoder
  implicit lazy val encodeSmartGroups: Encoder[Smart_groups] = deriveEncoder

  implicit lazy val decodeTranslations: Decoder[Translations] = deriveDecoder
  implicit lazy val encodeTranslations: Encoder[Translations] = deriveEncoder

  implicit lazy val decodeContributors: Decoder[Contributors] = deriveDecoder
  implicit lazy val encodeContributors: Encoder[Contributors] = deriveEncoder

  implicit lazy val decodeRevision: Decoder[Revision] = deriveDecoder
  implicit lazy val encodeRevision: Encoder[Revision] = deriveEncoder

  implicit lazy val decodeArticle: Decoder[Article] = deriveDecoder
  implicit lazy val encodeArticle: Encoder[Article] = deriveEncoder

  implicit lazy val decodeArticleDetails: Decoder[ArticleDetails] = deriveDecoder
  implicit lazy val encodeArticleDetails: Encoder[ArticleDetails] = deriveEncoder

  implicit lazy val decodeListAllArticlesResponse: Decoder[ListAllArticlesResponse] = deriveDecoder
  implicit lazy val encodeListAllArticlesResponse: Encoder[ListAllArticlesResponse] = deriveEncoder

  implicit lazy val decodeResults: Decoder[Results] = deriveDecoder
  implicit lazy val encodeResults: Encoder[Results] = deriveEncoder

  implicit lazy val decodeSearchForArticles: Decoder[SearchForArticles] = deriveDecoder
  implicit lazy val encodeSearchForArticles: Encoder[SearchForArticles] = deriveEncoder
}