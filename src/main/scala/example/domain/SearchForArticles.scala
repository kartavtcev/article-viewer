package example.domain

/**
  * Auto-generated classes
  */

case class SearchForArticles(
    queryTerm: String,
    totalResults: Double,
    totalPages: Double,
    currentPage: Double,
    count: Double,
    results: List[Results]
)

case class Results(
    id: String,
    title: String
)
