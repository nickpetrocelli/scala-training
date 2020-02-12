package lectures.advancedscala.part2afp

object CurriesPAF extends App {

  // curried functions
  val superAdder: Int => Int => Int =
    x => y => x + y

  val add3 = superAdder(3) // Int => Int = y => 3 + y
  println(add3(5))
  println(superAdder(3)(5)) // curried function

  // METHOD
  def curriedAdder(x: Int)(y: Int): Int = x + y // curried method

  val add4: Int => Int = curriedAdder(4)
  // lifting = ETA-EXPANSION

  // functions != methods (JVM limitation)
  def inc(x: Int) = x + 1
  List(1,2,3).map(inc) // Compiler does ETA-expansion, turns inc into a function (x => inc(x))

  // Partial function applications
  val add5 = curriedAdder(5) _ // _ tells compiler to convert expression to Int => Int

  // EXERCISE
  val simpleAddFunction = (x: Int, y: Int) => x + y
  def simpleAddMethod(x: Int, y: Int) = x + y
  def curriedAddMethod(x: Int)(y: Int) = x + y

  // define add7: Int => Int = y => 7 + y
  // as many different implementations of add7 using the above

  val add7 = (x: Int) => simpleAddFunction(7, x)
  val add7_2 = curriedAddMethod(7) _ // PAF
  val add7_3 = simpleAddFunction.curried(7)
  val add7_4 = curriedAddMethod(7)(_) // PAF, alternative syntax

  val add7_5 = simpleAddMethod(7, _: Int) // alternative syntax for turning methods into function values
              // y => simpleAddMethod(7, y)
  val add7_6 = simpleAddFunction(7, _: Int)

  // underscores are p o w e r f u l
  def concatenator(a: String, b: String, c: String) = a + b + c
  val insertName = concatenator("Hello, I'm ", _: String, ", how are you?") // x: String => concatenator(hello, x, howareyou)
  println(insertName("Nick"))

  val fillInTheBlanks = concatenator("Hello, ", _:String, _:String) // (x, y) => concatenator(hello, x, y)
  println(fillInTheBlanks("Nick ", " Scala is ok I guess"))

  // Exercises
  /*
  1. Process a list of numbers and return their string representations with diff formats
    Use %4.2f, %8.6f and %14.12f with a curried formatter functions
    i.e. "%4.2f".format(Math.PI)
 */
  def curriedFormatter(s: String)(number: Double): String = s.format(number)
  val numbers = List(Math.PI, Math.E, 1, 9.8, 1.3e-12)

  val simpleFormat = curriedFormatter("%4.2f") _
  val seriousFormat = curriedFormatter("%8.6f") _
  val preciseFormat = curriedFormatter("%14.12f") _

  println(numbers.map(preciseFormat))

  /*
  2. difference between
    - functions vs methods
    - parameters: by-name vs 0-lambda
   */
  def byName(n: => Int) = n + 1
  def byFunction(f: () => Int) = f() + 1

  def method: Int = 42
  def parenMethod(): Int = 42

  /*
  calling byName and byFunction
  - int
  - method
  - parenMethod
  - lambda
  - PAF
   */

  byName(23) // ok
  byName(method) // ok
  byName(parenMethod())
  byName(parenMethod) // ok but beware ==> byName(parenMethod()) (NOT calling with a function as input)
  // byName(() => 42) does not work
  byName((() => 42)())
  // byName(parenMethod _ ) // not ok

  // byFunction(45) not ok
  // byFunction(method) // not ok!! parameterless methods are evaluated to their value, compiler does not do ETA expansion
  byFunction(parenMethod) // compiler does ETA-expansion
  byFunction(() => 46)
  byFunction(parenMethod _) // also works, _ is not necessary though


}
