package example.infra

import example.domain._
import io.circe.generic.semiauto._
import io.circe.{Decoder, Encoder}

object JsonModule {
  implicit lazy val decodeAuthor: Decoder[Author] = deriveDecoder[Author]
  implicit lazy val encodeAuthor: Encoder[Author] = deriveEncoder[Author]

  implicit lazy val decodeSmartGroups: Decoder[Smart_groups] = deriveDecoder[Smart_groups]
  implicit lazy val encodeSmartGroups: Encoder[Smart_groups] = deriveEncoder[Smart_groups]

  implicit lazy val decodeTranslations: Decoder[Translations] = deriveDecoder[Translations]
  implicit lazy val encodeTranslations: Encoder[Translations] = deriveEncoder[Translations]

  implicit lazy val decodeArticle: Decoder[Article] = deriveDecoder[Article]
  implicit lazy val encodeArticle: Encoder[Article] = deriveEncoder[Article]

  implicit lazy val decodeListAllArticlesResponse: Decoder[ListAllArticlesResponse] = deriveDecoder[ListAllArticlesResponse]
  implicit lazy val encodeListAllArticlesResponse: Encoder[ListAllArticlesResponse] = deriveEncoder[ListAllArticlesResponse]

  implicit lazy val decodeResults: Decoder[Results] = deriveDecoder[Results]
  implicit lazy val encodeResults: Encoder[Results] = deriveEncoder[Results]

  implicit lazy val decodeSearchForArticles: Decoder[SearchForArticles] = deriveDecoder[SearchForArticles]
  implicit lazy val encodeSearchForArticles: Encoder[SearchForArticles] = deriveEncoder[SearchForArticles]
}