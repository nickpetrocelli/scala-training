package lectures.advancedscala.part4implicits

object OrganizingImplicits extends App {

  implicit val reverseOrdering: Ordering[Int] = Ordering.fromLessThan(_ > _)
   // implicit val normalOrdering: Ordering[Int] = Ordering.fromLessThan(_ < _) // will not compile

  println(List(1,4,5,3,2).sorted)

  // scala.Predef

  /*
  Implicits (used as implicit parameters):
  - val/var
  - object
  - accessor methods = defs with no parentheses
   */

  // Exercise
  case class Person(name: String, age: Int)


  val persons = List(
    Person("Steve", 30),
    Person("Amy", 22),
    Person("John", 66)
  )
  /*
  object Person {
  }


  println(persons.sorted) // won't compile unless we create an implicit ordering

   */
  /*
  Implicit scope
  - normal scope = Local Scope
  - imported scope
  - companions of all types involved in the method signature = A or any supertype
   */
  object AlphabeticNameOrdering {
    implicit val personOrdering: Ordering[Person] = Ordering.fromLessThan((a, b) => a.name.compareTo(b.name) < 0)
  }

  object AgeOrdering {
    implicit val ageOrdering: Ordering[Person] = Ordering.fromLessThan((a, b) => a.age < b.age)
  }


  // def sorted[B >: A](implicit ord : scala.math.Ordering[B]) : List[B]

  import AgeOrdering._
  println(persons.sorted)
  /*
  Best practices
  1. if there is a single possible value for an implicit, and you can edit the code for the type:
    - Define in COMPANION OBJECT
  2. If there are many possible values, but a single GOOD one, and you can edit code for the type:
    - Define the GOOD one in the COMPANION OBJECT.
  3. O.W. define in objects and make the user import them
   */

  /*
  Exercise.

  - totalPrice = most used (50%)
  - by unit count = 25%
  - by unit price = 25%
   */
  case class Purchase(nUnits: Int, unitPrice: Double)
  object Purchase {
    implicit val totalPriceOrder: Ordering[Purchase] = Ordering.fromLessThan((a, b) => a.nUnits * a.unitPrice < b.nUnits * b.unitPrice)
  }

  object PurchaseUnitCountOrder {
    implicit val unitCountOrder: Ordering[Purchase] = Ordering.fromLessThan((a,b) => a.nUnits < b.nUnits)
  }

  object PurchaseUnitPriceOrder {
    implicit val unitPriceOrder: Ordering[Purchase] = Ordering.fromLessThan((a, b) => a.unitPrice < b.unitPrice)
  }

}
