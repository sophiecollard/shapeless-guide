package chapter5

import Json.JsonObject
import shapeless.{Lazy, HList, HNil, Witness, ::}
import shapeless.labelled.FieldType

trait JsonObjectEncoder[A] extends JsonEncoder[A] {
  def encode(value: A): JsonObject
}

object JsonObjectEncoder {

  def instance[A](f: A => JsonObject): JsonObjectEncoder[A] =
    new JsonObjectEncoder[A] {
      def encode(value: A): JsonObject = f(value)
    }

  implicit val hNilEncoder: JsonObjectEncoder[HNil] =
    instance(_ => JsonObject(Nil))

  implicit def hListEncoder[K <: Symbol, H, T <: HList](
    implicit
    witness: Witness.Aux[K],
    hEncoder: Lazy[JsonEncoder[H]],
    tEncoder: JsonObjectEncoder[T]
  ): JsonObjectEncoder[FieldType[K, H] :: T] = {
    val fieldName: String = witness.value.name
    instance { hList =>
      val head = hEncoder.value.encode(hList.head)
      val tail = tEncoder.encode(hList.tail)
      JsonObject((fieldName -> head) :: tail.fields)
    }
  }

}
