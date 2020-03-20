package exercises

import lectures.advancedscala.part4implicits.TypeClasses.User

object EqualityPlayground extends App {
  val john = User("John", 32, "john@rockthejvm.com")

  /**
   * Equality
   */
  trait Equal[T] {
    def apply(t1: T, t2: T): Boolean
  }
  implicit object NameEquality extends Equal[User] {
    override def apply(t1: User, t2: User): Boolean = t1.name == t2.name
  }
  object FullEquality extends Equal[User] {
    override def apply(t1: User, t2: User): Boolean = t1.name == t2.name && t1.email == t2.email
  }

  /*
  Exercise: implement the TC pattern for the Equality TC
   */
  object Equal {
    def apply[T](a: T, b: T)(implicit equalizer: Equal[T]): Boolean = equalizer.apply(a, b)
  }

  val anotherJohn = User("John", 45, "anotherJohn@rtjvm.com")
  println(Equal.apply(john, anotherJohn))


  /*
  Exercise - improve the Equal TC with an implicit conversion class
   ===(anotherValue: T)
   !==(anotherValue: T)
   */
  implicit class EqualEnrichment[T](value: T) {
    def ===(anotherValue: T)(implicit equality: Equal[T]): Boolean = equality.apply(value, anotherValue)
    def !==(anotherValue: T)(implicit equality: Equal[T]): Boolean = !equality.apply(value, anotherValue)
  }
  println(john === anotherJohn)

  /*
  TYPE SAFE
   */
  println(john == 43)
   // println(john === 43) will not compile, type safe!
}
