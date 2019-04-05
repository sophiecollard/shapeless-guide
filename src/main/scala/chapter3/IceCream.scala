package chapter3

import shapeless.Generic

final case class IceCream(name: String, numCherries: Int, inCone: Boolean)

object IceCream {

  implicit val csvEncoder: CsvEncoder[IceCream] = {
    val generic = Generic[IceCream]
    val genericCsvEncoder = CsvEncoder[generic.Repr]
    CsvEncoder.instance { iceCream =>
      genericCsvEncoder.encode(generic.to(iceCream))
    }
  }

}
