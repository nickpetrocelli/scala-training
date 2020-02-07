package lectures.basicscala.part1basics

object ValuesVariablesTypes extends App {
  // VALs are immutable
  // Type annotation of VALs are optional, compiler can infer types
  val x: Int = 42
  val y = 42
  println(y)

  val aString: String = "hello"
  val anotherString = "goodbye"

  val aBoolean: Boolean = false
  val aChar: Char = 'a'
  val anInt: Int = x
  val aShort: Short = 4613
  val aLong: Long = 9234567890L
  val aFloat: Float = 2.0f
  val aDouble: Double = 3.14

  // scala variables
  var aVar: Int = 4
  aVar = 5 // use for side effects
}
