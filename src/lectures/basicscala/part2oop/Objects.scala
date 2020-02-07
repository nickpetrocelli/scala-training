package lectures.basicscala.part2oop

object Objects extends App {

  // scala does not have class-level functionality ("static")
  object Person { // type + its only instance
    // "static"/"class" - level functionality
    val N_EYES = 2
    def canFly: Boolean = false

    // factory method
    def apply(mother: Person, father: Person): Person = new Person("Bobby")
  }
  class Person(val name: String) {
    // instance-level functionality
  }
  // COMPANIONS

  println(Person.N_EYES)
  println(Person.canFly)

  val mary = new Person("Mary")
  val john = new Person("John")
  println(mary == john) // false

  // Scala objects = Singleton Instance
  val person1 = Person
  val person2 = Person
  println(person1 == person2) // true

  val bobby = Person(mary, john)

  // Scala Applications = Scala object with
  // def main(args: Array[String]): Unit




}
