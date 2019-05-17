package chapter5

sealed trait Json

object Json {

  final case object JsonNull extends Json

  final case class JsonString(value: String) extends Json

  final case class JsonNumber(value: Double) extends Json

  final case class JsonBoolean(value: Boolean) extends Json

  final case class JsonArray(items: List[Json]) extends Json

  final case class JsonObject(fields: List[(String, Json)]) extends Json

}
