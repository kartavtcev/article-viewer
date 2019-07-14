package example.domain

/**
  * Auto-generated classes
  */
// TODO: proper case naming.

case class ListAllArticlesResponse(
    articles: List[Article],
    page_number: Int,
    page_size: Int,
    total_pages: Int,
    total_entries: Int
)
