package chapter3

final case class Employee(name: String, id: Int, isManager: Boolean)

object Employee {

  implicit val csvEncoder: CsvEncoder[Employee] =
    CsvEncoder.instance[Employee] { employee =>
      List(employee.name, employee.id.toString, employee.isManager.toString)
    }

}
