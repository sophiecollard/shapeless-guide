import shapeless.{::, Generic, HList, HNil}
import shapeless.ops.hlist.{IsHCons, Last}

package object chapter4 {

  def getRepr[A](value: A)(implicit gen: Generic[A]) =
    gen.to(value)

  import Second._

  implicit def hListSecond[A, B, Rest <: HList]: Aux[A :: B :: Rest, B] =
    new Second[A :: B :: Rest] {
      type Out = B
      def apply(value: A :: B :: Rest): B =
        value.tail.head
    }

  def lastField[A, Repr <: HList](input: A)(
    implicit
    gen: Generic.Aux[A, Repr],
    last: Last[Repr]
  ): last.Out =
    last.apply(gen.to(input))
  // final case class Test(first: String, second: Int, third: Boolean)
  // val test = Test("sundae", 1, false)
  // lastField(test)

  def getWrappedValue[A, Repr <: HList, Head](input: A)(
    implicit
    gen: Generic.Aux[A, Repr],
    isHCons: IsHCons.Aux[Repr, Head, HNil]
  ): Head =
    gen.to(input).head
  // final case class Wrapped(value: Int)
  // val wrapped = Wrapped(15)
  // getWrappedValue(wrapped)

}
