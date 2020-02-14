package lectures.advancedscala.part2afp

object LazyEvaluation extends App {

  // lazy DELAYS the evaluation of values, is not evaluated until it is used for the first time
 lazy val x: Int = {
   println("Hello")
   42
 }
  println(x) // hello is only printed once, 42 twice
  println(x)

  // examples of implications:
  // side effects
  def sideEffectCondition: Boolean = {
    println("Boo")
    true
  }

  def simpleCondition: Boolean = false

  lazy val lazyCondition = sideEffectCondition
  println(if (simpleCondition && lazyCondition) "yes" else "no") // no "boo" is printed!
  // runtime is smart enough to not evaluate lazyCondition because simpleCondition is false!

  // in conjunction with call by name
  def byNameMethod(n: => Int): Int = {
    // CALL BY NEED
    lazy val t = n
    t + t + t + 1
  }
  def retrieveMagicValue = {
    // side effect or a long computation
    println("waiting")
    Thread.sleep(1000)
    42
  }

  println(byNameMethod(retrieveMagicValue))
  // use lazy vals

  // filtering with lazy vals
  def lessThan30(i : Int): Boolean = {
    println(s"$i is less than 30?")
    i < 30
  }

  def greaterThan20(i : Int): Boolean = {
    println(s"$i is greater than 20?")
    i > 20
  }

  val numbers = List(1, 25, 40, 5, 23)
  val lt30 = numbers.filter(lessThan30) // List(1, 25, 5, 23)
  val gt20 = lt30.filter(greaterThan20)
  println(gt20)

  val lt30Lazy = numbers.withFilter(lessThan30) // lazy vals under the hood
  val gt20Lazy = lt30Lazy.withFilter(greaterThan20)
  println("----------")
  println(gt20Lazy) // filtering hasn't actually taken place yet!
  gt20Lazy.foreach(println) // now we will see side effects, but in diff order

  // for-comprehensions use withFilter with guards
  for {
    a <- List(1,2,3) if a % 2 == 0 // use lazy vals
  } yield a + 1
  // equals
  List(1,2,3).withFilter(_ % 2 == 0).map(_ + 1)

  /*
  Exercise: Implement a lazily evaluated, singly linked STREAM of elements

  naturals = MyStream.from(1)(x => x + 1)  = (potentially infinite!)
  naturals.take(100) // lazily evaluated stream of the first 100 naturals (finite stream)
  naturals.foreach(println) // will crash - infinite!!
  naturals.map(_ * 2) // stream of all even numbers (potentially infinite)
   */

  abstract class MyStream[+A] {
    def isEmpty: Boolean
    def head: A
    def tail: MyStream[A]

    def #::[B >: A](element: B): MyStream[B] // prepend operator
    def ++[B >: A](anotherStream: MyStream[B]): MyStream[B] // concatenate two streams

    def foreach(f: A => Unit): Unit
    def map[B](f: A => B): MyStream[B]
    def flatMap[B](f: A => MyStream[B]): MyStream[B]
    def filter(predicate: A => Boolean): MyStream[A]

    def take(n: Int): MyStream[A] // takes the first n elements out of this stream
    def takeAsList(n: Int): List[A]

  }

  object MyStream {
    def from[A](start: A)(generator: A => A): MyStream[A] = ???
  }




}
