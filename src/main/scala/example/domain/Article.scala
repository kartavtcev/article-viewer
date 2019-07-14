package example.domain

/**
  * Auto-generated classes
  */

// TODO: check Maximum length for strings, when building write API

case class Author(
    id: Double,
    name: String,
    gravatar: String,
    email: String
)
case class Smart_groups(
    id: Double,
    name: String
)
case class Translations(
    id: Double,
    title: Option[String],
    body: Option[String],
    summary: Option[String],
    machine_summary: Option[String],
    language_id: String, // TODO:
    created_at: Option[String], // TODO: unknown by the doc?
    updated_at: String // TODO:
)
case class Article(
    id: Double,
    title: Option[String],
    author: Option[Author], // TODO: required by doc, yet in response with default article is NOT present
    source: String,
    external_id: Option[String],
    order: Double,
    category_id: Double,
    access: String,
    smart_groups: Option[List[Smart_groups]],
    keywords: Option[List[String]],
    notes: Option[String],
    status: String,
    last_publisher: Option[Author],
    last_published_at: Option[String], // TODO: required by doc, yet in response with default article is NOT present
    contributors: Option[List[Author]],  // TODO: required by doc, yet in response with default article is NOT present
    editor_version: String,
    created_at: Option[String], // TODO: required by doc, yet in response with default article is NOT present
    updated_at: String,
    translations: List[Translations]
)