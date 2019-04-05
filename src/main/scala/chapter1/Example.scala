package chapter1

import shapeless._

object Example {

  case class Employee(name: String, id: Int, isManager: Boolean)
  case class IceCream(name: String, numCherries: Int, inCone: Boolean)

  val genericEmployee = Generic[Employee].to(Employee("Dave", 123, false))
  val genericIceCream = Generic[IceCream].to(IceCream("Sundae", 1, false))

  def genericSerialize(gen: String :: Int :: Boolean :: HNil): List[String] =
    List(gen(0), gen(1).toString, gen(2).toString)

  val serializedEmployee: List[String] = genericSerialize(genericEmployee)
  val serializedIceCream: List[String] = genericSerialize(genericIceCream)

}
