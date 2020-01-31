package lectures.part2oop

object AbstractDataTypes extends App {

  // abstract
  abstract class Animal {
    val creatureType: String = "Wild"
    def eat: Unit
    // abstract members/methods are unimplemented
  }

  class Dog extends Animal {
    override val creatureType: String = "Canine"
    def eat: Unit = println("Crunch Crunch") // override keyword not mandatory
  }

  // traits
  // by default have abstract methods/members
  trait Carnivore {
    def eat(animal: Animal): Unit
    val preferredMeal: String = "fresh meat"
  }

  trait ColdBlooded

  // inherits members from both Animal and Carnivore
  class Crocodile extends Animal with Carnivore with ColdBlooded {
    override val creatureType: String = "croc"
    def eat: Unit = println("nomnomnom")
    def eat(animal: Animal): Unit = println(s"I'm a croc and I'm eating ${animal.creatureType}")
  }

  val dog = new Dog
  val croc = new Crocodile
  croc.eat(dog)

  // traits vs abstract classes
  // abstract classes AND traits can have both abstract and non-abstract members
  // 1. Traits do not have constructor parameters
  // 2. Can only extend one class, but can mixin multiple traits
  // 3. Traits == BEHAVIOR, Abstract Class == "thing"

}
