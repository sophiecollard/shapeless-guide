package chapter3

sealed trait Shape

final case class Rectangle(height: Double, width: Double) extends Shape

final case class Circle(radius: Double) extends Shape
