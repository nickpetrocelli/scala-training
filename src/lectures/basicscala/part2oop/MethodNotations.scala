package lectures.basicscala.part2oop

object MethodNotations extends App {

  class Person(val name: String, favoriteMovie: String) {
    def likes(movie: String): Boolean = movie == favoriteMovie
    def +(person: Person): String = s"${this.name} is hanging out with ${person.name}"
    def unary_! : String = s"$name, what the heck?!"
    def isAlive: Boolean = true
    def apply(): String = s"Hi, my name is $name and I like $favoriteMovie"
  }

  val mary = new Person("Mary", "Inception")
  println(mary.likes("Inception"))
  println(mary likes "Inception") // equivalent
  // infix notation = operator notation (only works with methods with one parameter) (syntactic sugar)

  // "operators" in scala
  val tom = new Person("Tom", "Fight Club")
  println(mary + tom)
  println(mary.+(tom)) // equivalent

  println(1 + 2)
  println(1.+(2)) //equivalent

  // All operators are methods!
  // Ex: Akka actors have ! ?

  // prefix notation
  val x = -1
  val y = 1.unary_- //equivalent
  //unary_ prefix only works with - + ~ !

  println(!mary)
  println(mary.unary_!) //equivalent

  // postfix notation
  println(mary.isAlive)
  println(mary isAlive) // equivalent

  // apply
  println(mary.apply())
  println(mary()) // equivalent
}
