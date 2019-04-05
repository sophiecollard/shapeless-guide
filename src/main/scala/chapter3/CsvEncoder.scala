package chapter3

import shapeless.{Coproduct, CNil, Generic, HList, HNil, Inl, Inr, Lazy, ::, :+:}

trait CsvEncoder[A] {

  def encode(value: A): List[String]

}

object CsvEncoder {

  // "Summoner" or "Materializer" method
  def apply[A](implicit ev: CsvEncoder[A]): CsvEncoder[A] = ev

  // "Constructor" method
  def instance[A](f: A => List[String]): CsvEncoder[A] = {
    new CsvEncoder[A] {
      def encode(value: A): List[String] = f(value)
    }
  }

  implicit val stringCsvEncoder: CsvEncoder[String] =
    CsvEncoder.instance { string =>
      List(string)
    }

  implicit val intCsvEncoder: CsvEncoder[Int] =
    CsvEncoder.instance { int =>
      List(int.toString)
    }

  implicit val doubleCsvEncoder: CsvEncoder[Double] =
    CsvEncoder.instance { double =>
      List(double.toString)
    }

  implicit val booleanCsvEncoder: CsvEncoder[Boolean] =
    CsvEncoder.instance { boolean =>
      List(boolean.toString)
    }

  implicit val hNilCsvEncoder: CsvEncoder[HNil] =
    CsvEncoder.instance(_ => Nil)

  implicit def hListCsvEncoder[H, T <: HList](
    implicit
    hCsvEncoder: Lazy[CsvEncoder[H]],
    tCsvEncoder: CsvEncoder[T]
  ): CsvEncoder[H :: T] =
    CsvEncoder.instance { case h :: t =>
      hCsvEncoder.value.encode(h) ++ tCsvEncoder.encode(t)
    }

  implicit val cnilCsvEncoder: CsvEncoder[CNil] =
    CsvEncoder.instance(_ => throw new RuntimeException("Impossible to encode CNil!"))

  implicit def coproductCsvEncoder[H, T <: Coproduct](
    implicit
    hCsvEncoder: Lazy[CsvEncoder[H]],
    tCsvEncoder: CsvEncoder[T]
  ): CsvEncoder[H :+: T] =
    CsvEncoder.instance {
      case Inl(h) => hCsvEncoder.value.encode(h)
      case Inr(t) => tCsvEncoder.encode(t)
    }

  implicit def genericCsvEncoder[A, R](
    implicit
    gen: Generic.Aux[A, R],
    csvEncoder: Lazy[CsvEncoder[R]]
  ): CsvEncoder[A] =
    CsvEncoder.instance { a =>
      csvEncoder.value.encode(gen.to(a))
    }

}
