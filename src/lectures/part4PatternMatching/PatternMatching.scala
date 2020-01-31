package lectures.part4PatternMatching

import scala.util.Random

object PatternMatching extends App {

  // switch on steroids
  val random = new Random
  val x = random.nextInt(10)

  val description = x match {
    case 1 => "the ONE"
    case 2 => "double or nothing"
    case 3 => "third time is the charm"
    case _ => "something else" // _ == wildcard
  }

  println(x)
  println(description)

  // 1. Decompose values
  case class Person(name: String, age: Int)
  val bob = Person("bob", 20)

  val greeting = bob match {
      // with guard (if statement)
    case Person(n, a) if a < 21 => s"Hi, my name is $n and I can't drink in the United States."

    // if bob is person, can extract parameters
    case Person(n, a) => s"Hi, my name is $n and I am $a years old."
    case _ => "I don't know who I am."
  }

  println(greeting)

  /*
  1. Cases are matched in order.
  2. What if no cases match? {Exception in thread "main" scala.MatchError: 5 (of class java.lang.Integer)}
    Make sure to use wildcards!
  3. What is the type of the pattern match expression? Unification of all types returned by the statement. (Lowest common ancestor)
  4. PM works really well with case classes!
   */

  // PM on sealed hierarchies
  sealed class Animal
  case class Dog(breed: String) extends Animal
  case class Parrot(greeting: String) extends Animal

  // compiler issues warning
  val animal: Animal = Dog("Terra Nova")
  animal match {
    case Dog(someBreed) => println(s"matched a Dog of the $someBreed breed")
  }

  // match everything?
  val isEven = x match {
    case n if n % 2 == 0 => true
    case _ => false
  }
  // WHY??

  /*
  Exercise
  simple function uses PM
  takes an Expr => human readable form
  e.g. Sum(Number(2), Number(3)) => 2 + 3
   */

  trait Expr
  case class Number(n: Int) extends Expr
  case class Sum(e1: Expr, e2: Expr) extends Expr
  case class Prod(e1: Expr, e2: Expr) extends Expr

  def show(e: Expr): String = e match {
    case Number(n) => s"$n"
    case Sum(e1, e2) => show(e1) + " + " + show(e2)
    case Prod(e1, e2) => {
      def maybeShowParentheses(exp: Expr) = exp match {
        case Prod(_, _) => show(exp)
        case Number(_) => show(exp)
        case _ => s"(${show(exp)})"
      }

      s"${maybeShowParentheses(e1)} * ${maybeShowParentheses(e2)}"
    }
  }

  println(show(Sum(Number(2), Number(3))))
  println(show(Prod(Sum(Number(2), Number(3)), Number(4))))

}
