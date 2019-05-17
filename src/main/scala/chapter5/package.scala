import shapeless.Witness
import shapeless.labelled.FieldType
import shapeless.syntax.singleton._

package object chapter5 {

  val numCherries = "numCherries" ->> 123

  /**
    * TBC
    */
  def getFieldName[K, V](value: FieldType[K, V])(
    implicit witness: Witness.Aux[K]
  ): K =
    witness.value
  // getFieldName(numCherries)
  // res0: String = numCherries

  def getFieldValue[K, V](value: FieldType[K, V]): V =
    value
  // getFieldValue(numCherries)
  // res1: Int = 123

}
