package example.domain

/**
  * Auto-generated classes
  */
// TODO: check Maximum length for strings, when querying write API
// TODO: proper case naming.

case class Author(
    id: Int,
    name: String,
    gravatar: String,
    email: String
)
case class Smart_groups(
    id: Int,
    name: String
)
case class Translations(
    id: Int,
    title: Option[String],
    body: Option[String],
    summary: Option[String],
    machine_summary: Option[String],
    language_id: String,
    created_at: Option[String],
    updated_at: String,
    featured_image_url: Option[String]
)
case class Article(
    id: Int,
    title: Option[String],
    author: Option[Author], // TODO: required by doc, yet in response with default article is NOT present
    source: String,
    external_id: Option[String],
    order: Int,
    category_id: Int,
    access: String,
    smart_groups: Option[List[Smart_groups]],
    revision: Option[Revision],
    keywords: Option[List[String]], // TODO: check it
    notes: Option[String],
    status: String,
    last_publisher: Option[Author],
    last_published_at: Option[String], // TODO: required by doc, yet in response with default article is NOT present
    contributors: Option[List[Author]], // TODO: required by doc, yet in response with default article is NOT present
    editor_version: String,
    created_at: Option[String], // TODO: required by doc, yet in response with default article is NOT present
    updated_at: String,
    translations: List[Translations]
)

case class ArticleDetails(article: Article)

case class Contributors(
    name: String,
    id: Int,
    gravatar: String,
    email: String
)
case class Revision(
    updated_at: String,
    translations: List[Translations],
    status: String,
    source: String,
    smart_groups: List[Smart_groups],
    order: Int,
    notes: Option[String],
    last_publisher: Option[String],
    last_published_at: Option[String],
    keywords: Option[List[String]],
    id: Int,
    external_id: Option[String],
    editor_version: String,
    created_at: String,
    contributors: List[Contributors],
    category_id: Int,
    author: Contributors,
    access: String
)
