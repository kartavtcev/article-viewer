package example.domain

//sealed trait Message
/*
/**
  * Auto-generated classes
  */
case class Author(
    id: Double,
    name: String,
    gravatar: String,
    email: String
)// extends Message

case class Smart_groups(
    id: Double,
    name: String
)// extends Message

case class Translations(
    id: Double,
    title: String,
    body: String,
    summary: String,
    machine_summary: String,
    language_id: String,
    created_at: String,
    updated_at: String
)// extends Message

case class Article(
    id: Double,
    title: String,
    author: Author,
    source: String,
    external_id: String,
    order: Double,
    category_id: Double,
    access: String,
    smart_groups: List[Smart_groups],
    keywords: List[String],
    notes: String,
    status: String,
    last_publisher: Author,
    last_published_at: String,
    contributors: List[Author],
    editor_version: String,
    created_at: String,
    updated_at: String,
    translations: List[Translations]
) //extends Message

case class ListAllArticlesResponse(
    articles: List[Article],
    page_number: Double,
    page_size: Double,
    total_pages: Double,
    total_entries: Double
) //extends Message

case class SearchForArticles(
    queryTerm: String,
    totalResults: Double,
    totalPages: Double,
    currentPage: Double,
    count: Double,
    results: List[Results]
) //extends Message

case class Results(
    id: String,
    title: String
) //extends Message
*/