package lectures.advancedscala.part4implicits

object TypeClasses extends App {

  trait HTMLWritable {
    def toHTML: String
  }

  case class User(name: String, age: Int, email: String) extends HTMLWritable {
    override def toHTML: String = s"<div>$name ($age yo) <a href=$email/> </div>"
  }

  val john = User("John", 32, "john@rockthejvm.com")

  /*
  1 - only works for types WE write
  2 - ONE implementation out of quite a number
   */

  // option 2 - pattern matching
  object HTMLSerializerPM {
    def serializeToHtml(value: Any) = value match {
      case User(n, a, e) => ""
      case _ => ""
    }
  }

  /*
  1 - lost type safety
  2 - need to modify the code every time
  3 - still ONE implementation (think logins)
   */

  trait HTMLSerializer[T] {
    def serialize(value: T): String
  }

  implicit object UserSerializer extends HTMLSerializer[User] {
    def serialize(value: User): String = s"<div>{$value.name} (${value.age} yo) <a href=${value.email}/> </div>"
  }

  println(UserSerializer.serialize(john))

  // 1 - we can define serializers for other types
  import java.util.Date
  object DateSerializer extends HTMLSerializer[Date] {
    override def serialize(value: Date): String = s"<div>${value.toString}</div>"
  }

  // 2 - we can define MULTIPLE serializers
  object PartialUserSerializer extends HTMLSerializer[User] {
    def serialize(value: User): String = s"<div>{$value.name}</div>"
  }

  // HTMLSerializer is a "type class"
  // All implementers are called "type class instances"

  // TYPE CLASS
  trait MyTypeClassTemplate[T] {
    def action(value: T): String
  }

  object MyTypeClassTemplate {
    def apply[T](implicit instance: MyTypeClassTemplate[T]): MyTypeClassTemplate[T] = instance
  }

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

  // PART 2
  object HTMLSerializer {
    def serialize[T](value: T)(implicit serializer: HTMLSerializer[T]): String =
      serializer.serialize(value)

    def apply[T](implicit serializer: HTMLSerializer[T]): HTMLSerializer[T] = serializer
  }

  implicit object IntSerializer extends HTMLSerializer[Int] {
    override def serialize(value: Int): String = s"<div style: color-blue>$value</div>"
  }


  println(HTMLSerializer.serialize(42))
  println(HTMLSerializer.serialize(john))

  // access o the entire type class interface
  println(HTMLSerializer[User].serialize(john))

  /*
  Exercise: implement the TC pattern for the Equality TC
   */
  object Equal {
    def apply[T](a: T, b: T)(implicit equalizer: Equal[T]): Boolean = equalizer.apply(a, b)
  }

  val anotherJohn = User("John", 45, "anotherJohn@rtjvm.com")
  println(Equal.apply(john, anotherJohn))
}
