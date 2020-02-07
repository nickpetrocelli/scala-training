package lectures.basicscala.part1basics

object Functions extends App {

  def aFunction(a: String, b: Int): String = {
    a + b + " " + b
  }

  println(aFunction("hello", 3))

  def aParameterlessFunction(): Int = 42

  println(aParameterlessFunction())
  println(aParameterlessFunction)

  def aRepeatedFunction(aString: String, n: Int): String = {
    if (n == 1) aString
    else aString + aRepeatedFunction(aString, n - 1)
  }

  println(aRepeatedFunction("hello", 3))

  // When you need loops, use recursion.

  def aFunctionWithSideEffects(aString: String): Unit = println(aString)

  def aBigFunction(n: Int): Int = {
    def aSmallerFunction(a: Int, b: Int): Int = a + b

    aSmallerFunction(n, n - 1)
  }

  /*
  1. A greeting function (name, age) => "Hi, my name is $name and I am $age years old".
  2. Factorial function (recursive)
  3. Fibonacci sequence f(1) = 1 f(2) = 1 f(n) = f(n - 1) + f(n - 2)
  4. Tests if a number is prime.
   */

  def greeting(name: String, age:Int): Unit = {
    println(s"Hi, my name is $name and I am $age years old.")
  }

  def factorial(n: Int): Int = {
    if (n > 1) n * factorial(n-1) else 1
  }
  assert(factorial(3) == 6)
  assert(factorial(1) == 1)
  assert(factorial(0) == 1)

  def fib(n: Int): Int = {
    if (n == 1 || n == 0 ) n else fib(n-1) + fib(n-2)
  }
  assert(fib(0) == 0)
  assert(fib(1) == 1)
  assert(fib(2) == 1)
  assert(fib(3) == 2)
  assert(fib(4) == 3)
  assert(fib(5) == 5)
  assert(fib(6) == 8)
  assert(fib(8) == 21)

  def ifPrime(n: Int): Boolean = {
    def ifPrimeHelper(divBy: Int): Boolean = {
      if (divBy > 1) {
        if (n % divBy == 0) false else ifPrimeHelper(divBy - 1)
      }
      else true
    }
    ifPrimeHelper(n / 2)
  }

  assert(ifPrime(2))
  assert(ifPrime(7))
  assert(ifPrime(19))
  assert(!ifPrime(10))
  assert(!ifPrime(50))
  assert(!ifPrime(66))
}
