package example.domain

/**
  * Auto-generated classes
  */
// TODO: proper case naming.

case class ArticleSearchByKeyword(
    queryTerm: String,
    totalResults: Int,
    totalPages: Int,
    currentPage: Int,
    count: Int,
    results: List[Results]
)

case class Results(
    id: String,
    title: String
)
