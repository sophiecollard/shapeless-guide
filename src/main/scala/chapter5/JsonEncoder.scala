package chapter5

trait JsonEncoder[A] {
  def encode(value: A): Json
}

object JsonEncoder {

  def apply[A](implicit ev: JsonEncoder[A]): JsonEncoder[A] = ev

  def instance[A](f: A => Json): JsonEncoder[A] =
    new JsonEncoder[A] {
      def encode(value: A): Json = f(value)
    }

  import Json._

  implicit val stringEncoder: JsonEncoder[String] =
    instance(JsonString)

  implicit val intEncoder: JsonEncoder[Int] =
    instance(n => JsonNumber(n))

  implicit val doubleEncoder: JsonEncoder[Double] =
    instance(JsonNumber)

  implicit val booleanEncoder: JsonEncoder[Boolean] =
    instance(JsonBoolean)

  implicit def listEncoder[A](implicit ev: JsonEncoder[A]): JsonEncoder[List[A]] =
    instance(as => JsonArray(as.map(ev.encode)))

  implicit def optionEncoder[A](implicit ev: JsonEncoder[A]): JsonEncoder[Option[A]] =
    instance(aOpt => aOpt.map(ev.encode).getOrElse(JsonNull))

}
