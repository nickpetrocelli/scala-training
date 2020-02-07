package lectures.advancedscala.part2afp

object PartialFunctions extends App {

  // any int can be passed and return a result
  val aFunction = (x: Int) => x + 1 //Function1[Int, Int] === Int => Int

  val aFussyFunction = (x: Int) =>
    if (x == 1) 42
    else if (x == 2) 56
    else if (x == 5) 999
    else throw new FunctionNotApplicableException

  class FunctionNotApplicableException extends RuntimeException

  val aNicerFussyFunction = (x: Int) => x match {
    case 1 => 42
    case 2 => 56
    case 3 => 999
  }
  // {1,2,5} => Int === "Partial function from Int to Int"

  val aPartialFunction: PartialFunction[Int, Int] = {
    case 1 => 42
    case 2 => 56
    case 3 => 999
  } // partial function value

  println(aPartialFunction(2))
  // println(aPartialFunction(500)) // crashes with MatchError

  // PF utilities
  println(aPartialFunction.isDefinedAt(67))

  // lift to total function returning option
  val lifted = aPartialFunction.lift // Int => Option[Int]
  println(lifted(2))
  println(lifted(98))

  val pfChain = aPartialFunction.orElse[Int, Int] {
    case 45 => 67
  }

  println(pfChain(2))
  println(pfChain(45))

  // PF extend normal functions
  val aTotalFunction: Int => Int = {
    case 1 => 99
  }

  // HOFs accept partial functions as well
  val aMappedList = List(1,2,3).map {
    case 1 => 42
    case 2 => 78
    case 3 => 1000
  }

  println(aMappedList)

  /*
    Note: PFs can only have ONE parameter type
   */

  /**
   * Exercises
   *
   * 1 - construct a PF instance yourself (anonymous class)
   * 2 - dumb chatbot as a PF
   */
  val crapFun = new PartialFunction[Int, Int] {
    override def isDefinedAt(x: Int): Boolean = x == 2

    override def apply(v1: Int): Int = if (v1 == 2) v1 * 10 else throw new RuntimeException("aaaaaaa")
  }

  val dumbBot: PartialFunction[String, String] = {
    case "Hello" => "Hi"
    case "Goodbye" => "bye"
  }

  scala.io.Source.stdin.getLines().foreach(line => println(dumbBot(line)))
  // or scala.io.Source.stdin.getLines().map(chatbot).foreach(println)
}
