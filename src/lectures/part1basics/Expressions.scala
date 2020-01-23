package lectures.part1basics

object Expressions extends App {
  val x = 1 + 2 // Expression
  println(x)

  println(2 + 3 * 4)
  // + - * / & | ^ << >> >>> (right shift with zero extension)

  println(1==x)
  // == != > >= < <=

  println(!(1==x))
  // ! && ||

  var aVar = 2
  aVar += 3 // also works with -=, *=, \= .... side effects
  println(aVar)

  // Instructions (DO) vs expressions (VALUE)
  // Instructions are executed, expressions are evaluated

  // IF expression
  val aCondition = true
  val aConditionedValue = if(aCondition) 5 else 3 // "if expression"
  println(if(aCondition) 5 else 3)

  var i = 0
  val aWhile: Unit = while (i<10) {
    println(i)
    i+=1
  }

  // NEVER WRITE THAT AGAIN.
  // EVERYTHING in Scala is an expression!

  val aWeirdValue: Unit = (aVar = 3) // Unit === void
  println(aWeirdValue) // ()

  // side effects: println(), whiles, reassigning (all return Unit)

  // Code blocks
  // value of code block is value of last expression
  val aCodeBlock = {
    val y = 2
    val z = y+1

    if (z>2) "hello" else "goodbye"
  }
  // 1. Difference between "hello world" vs println("hello world") (Ans: string literal vs. side effect)
  // 2. Value of:
  val someValue = {
    2 < 3
  } // Ans: true

  val someOtherValue = {
    if(someValue) 239 else 986
    42
  } // Ans: 42
}
