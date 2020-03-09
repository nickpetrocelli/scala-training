package lectures.advancedscala.part4implicits

object PimpMyLibrary extends App {

  // 2.isPrime

  // extending AnyVal is a memory optimization
  implicit class RichInt(val value: Int) extends AnyVal {
    def isEven: Boolean = value % 2 == 0
    def sqrt: Double = Math.sqrt(value)

    def times(function: () => Unit): Unit = {
      @scala.annotation.tailrec
      def timesAux(n: Int): Unit = {
        if (n <= 0) ()
        else {
          function()
          timesAux(n - 1)
        }
      }
      timesAux(value)
    }
    def *[T](list: List[T]): List[T] = {
      def concatenate(n: Int): List[T] =
        if (n <= 0) List()
        else concatenate(n - 1) ++ list

      concatenate(value)
    }
  }

  /* WILL CAUSE COMPILER ERROR
  implicit class RicherInt(richInt: RichInt) {
    def isOdd: Boolean = richInt.value % 2 != 0
  }
   */

  new RichInt(42).sqrt

  42.isEven

  // type enrichment = 'pimping' (wow you just posted cringe)

  1 to 10 // intWrapper

  import scala.concurrent.duration._
  3.seconds // DurationInt

  /*
  Enrich the string class
  - asInt
  - encrypt (caesar cypher)
  John -> lqjp

  keep enriching the Int class
  - times(function)
  3.times(() => ...)
  - *
  - 3 * list(1,2) => List(1,2,1,2,1,2)
   */

  implicit class RichString(string: String) {
    def asInt: Int = Integer.valueOf(string) // java.lang.Integer => Int
    def encrypt(cypherDistance: Int): String = string.map(c => (c + cypherDistance).asInstanceOf[Char])
  }
  println("John".encrypt(2))

  3.times(() => println("Scala rocks!"))
  println(4 * List(1,2))

  // "3" / 4
  implicit def stringToInt(string: String): Int = Integer.valueOf(string)
  println("6" / 2)

  // equivalent: implicit class RichAltInt(value: Int)
  class RichAltInt(value: Int)
  implicit def enrich(value: Int): RichAltInt = new RichAltInt(value)

  // danger zone
  implicit def intToBoolean(i: Int): Boolean = i == 1

  /*
  if (n) do something
  else do something else
   */

  val aConditionedValue = if(3) "OK" else "Something wrong"

  // can be hard to debug
  println(aConditionedValue)
}
