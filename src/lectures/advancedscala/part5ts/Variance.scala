package lectures.advancedscala.part5ts

object Variance extends App {

  trait Animal
  class Dog extends Animal
  class Cat extends Animal
  class Crocodile extends Animal

  // what is variance?
  // "inheritance" - type substitution of generics
  class Cage[T]
  // should Cage[Cat] Inherit from Cage[Animal]?

  // "Yes" - covariance
  class CCage[+T]
  val ccage: CCage[Animal] = new CCage[Cat]

  // "No" - invariance
  class ICage[T]
 // val icage: ICage[Animal] = new ICage[Cat] does not compile

  // hell no - opposite - contravariance
  class XCage[-T]
  val xcage: XCage[Cat] = new XCage[Animal] //valid

  class InvariantCage[T](val animal: T) // invariant

  //covariant positions
  class CovariantCage[+T](val animal: T) // COVARIANT position

  // can't put a contravariant type in a covariant position
  // class ContravariantCage[-T](val animal: T) // doesn't compile

  // class CovariantVariableCage[+T](var animal: T) // does not compile; CONTRAVARIANT position

  // class ContravariantVariableCage[-T](var animal: T) // does not compile; COVARIANT POSITION
  // var is in both positions

  class InvariantVariableCage[T](var animal: T) // OK

  /*
  trait AnotherCovariantCage[+T]{
    def addAnimal(animal:T) // CONTRAVARIANT POSITION, code does not compile
  }
  */
   class AnotherContravariantCage[-T] {
    def addAnimal(animal: T) = true
  }
  val acc: AnotherContravariantCage[Cat] = new AnotherContravariantCage[Animal]
  acc.addAnimal(new Cat)
  class Kitty extends Cat
  acc.addAnimal(new Kitty)
}
