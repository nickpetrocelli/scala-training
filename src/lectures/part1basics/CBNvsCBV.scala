package lectures.part1basics

object CBNvsCBV extends App{

  def calledByValue(x: Long): Unit = {
    println(s"by value: $x")
    println(s"by value: $x")
  }

  def calledByName(x: => Long): Unit = {
    println(s"by name: $x")
    println(s"by name: $x")
  }

  // CBV evaluates the time expression first then passes to function
  calledByValue(System.nanoTime())
  // CBN literally passes the function call through and replaces all instances of variable in function with the call
  calledByName(System.nanoTime())

  def infinite(): Int = 1 + infinite()
  def printFirst(x: Int, y: => Int) = println(x)

 // printFirst(infinite(), 34) crashes with stack overflow
  printFirst(34, infinite()) // doesn't crash; infinite() is never evaluated because parameter y is never used
}
