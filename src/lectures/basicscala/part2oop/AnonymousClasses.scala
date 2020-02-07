package lectures.basicscala.part2oop

object AnonymousClasses extends App {

  abstract class Animal {
    def eat: Unit
  }

  // anonymous class
  val funnyAnimal: Animal = new Animal {
    override def eat: Unit = println("ahahahahahaha")
  }

  /*
  Equivalent with
  class AnonymousClasses$$anon$1 extends Animal {
      override def eat: Unit = println("ahahahahahaha")
  }
  val funnyAnimal: Animal = new AnonymousClasses$$anon$1

   */

  println(funnyAnimal.getClass) // class lectures.basicscala.part2oop.AnonymousClasses$$anon$1

  class Person2(name: String) {
    def sayHi: Unit = println(s"Hi, my name is $name, how can I help?")
  }

  val jim = new Person2("Jim") {
    override def sayHi: Unit = println(s"Hi, my name is Jim, how can I be of service?")
  }

  // anonymous classes work for both abstract and non-abstract types!
  // also work for traits

}
