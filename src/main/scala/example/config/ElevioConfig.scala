package example.config

final case class ElevioConfig(apiAuth: ApiAuth, apiUrls: ApiUrls)

final case class ApiAuth(key: String, token: String)
final case class ApiUrls(base: String, allArticles: String)