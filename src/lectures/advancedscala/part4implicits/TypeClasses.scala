package lectures.advancedscala.part4implicits

object TypeClasses extends App {

  trait HTMLWritable {
    def toHTML: String
  }

  case class User(name: String, age: Int, email: String) {
    // override def toHTML: String = s"<div>$name ($age yo) <a href=$email/> </div>"
  }

  val john = User("John", 32, "john@rockthejvm.com")
  val john2 = User("John", 32, "john@rockthejvm.com")


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


  // part 3
  implicit class HTMLEnrichment[T](value: T) {
    def toHTML(implicit serializer: HTMLSerializer[T]): String = serializer.serialize(value)
  }

  println(john.toHTML) // println(new HTMLEnrichment[User](john).toHTML(UserSerializer))

  /*
  - extend to new types
  - choose implementation
  - super expressive
   */
  println(2.toHTML)
  println(john.toHTML(PartialUserSerializer))

  /*
  - type class itself, with functionality you want to expose -- HTMLSerializer[T]
  - type class instances (some of which are implicit) --- UserSerializer, IntSerializer
  - conversion with implicit classes --- HTMLEnrichment
   */

  // context bounds
  def htmlBoilerplate[T](content: T)(implicit serializer: HTMLSerializer[T]): String =
    s"<html><body> ${content.toHTML(serializer)}</body></html>"

  def htmlSugar[T : HTMLSerializer](content: T): String = { // context bound tells compiler to inject the serializer
    val serializer = implicitly[HTMLSerializer[T]]
    s"<html><body> ${content.toHTML}</body></html>"
  }

  // implicitly
  case class Permissions(mask: String)
  implicit val defaultPermissions: Permissions = Permissions("0744")

  // in some other part of the code, extract what implicit value is
  val standardPerms = implicitly[Permissions]
}
