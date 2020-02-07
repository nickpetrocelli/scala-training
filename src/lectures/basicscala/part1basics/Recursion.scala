package lectures.basicscala.part1basics

import scala.annotation.tailrec

object Recursion extends App {

  def factorial(n: Int): Int =
    if (n <= 1) 1
    else {
      println("Computing factorial of " + n + " - I first need factorial of " + (n-1))
      val result = n * factorial(n-1)
      println(s"Computed factorial of $n")
      result
    }
  println(factorial(10))
  // println(factorial(5000)) crashes

  def anotherFactorial(n: Int): BigInt = {
    @tailrec
    def factHelper(x: Int, accumulator: BigInt): BigInt = {
      if (x <= 1) accumulator
      else factHelper(x-1, x * accumulator) // TAIL RECURSION = use recursive call as the LAST expression
    }
    factHelper(n, 1)
  }

  /*
  anotherFactorial(10) = factHelper(10, 1)
  = factHelper(9, 10 * 1)
  = factHelper(8, 9 * 10 * 1)
  = factHelper(7, 8 * 9 * 10 * 1)
  = ...
  = factHelper(2, 3 * 4 * ... * 10 * 1)
  = factHelper(1, 2 * 3 * 4 * ... * 10 * 1)
  = 1 * 2 * 3 * 4 * 5 * 6 * 7 * 8 * 9 * 10
   */
  println(anotherFactorial(5000))

  // When you need loops, use TAIL recursion instead.
  /*
  1. Concatenate a string n times using tail recursion
  2. IsPrime tail recursive
  3. Fibonacci function, tail recursive
   */
  def concat(s: String, n: Int): String = {
    @tailrec
    def concatHelper(acc: String, m: Int): String = {
      if (m > 0) concatHelper(acc + s, m-1) else acc
    }
    concatHelper("", n)
  }

  def isPrime(n: Int): Boolean = {
    @tailrec
    def isPrimeHelper(isStillPrime: Boolean, m: Int): Boolean = {
      if (!isStillPrime) false
      else if (m <= 1) true
      else isPrimeHelper(n % m == 0 && isStillPrime, m-1)
    }
    isPrimeHelper(isStillPrime = true, n/2)
  }

  def fib(n: Int): Int = {
    @tailrec
    def fibTailRec(i: Int, last: Int, nextLast: Int): Int = {
      if (i >= n) last
      else fibTailRec(i+1, last + nextLast, last)
    }
    fibTailRec(2, 1, 1)
  }
}
