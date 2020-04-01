package lectures.advancedscala.part5ts

object RockingInheritance extends App {

  // convenience
  trait Writer[T] {
    def write(value: T): Unit
  }

  trait Closeable {
    def close(status: Int): Unit
  }
  trait GenericStream[T] {
    // some methods
    def foreach(f: T => Unit): Unit
  }

  def processStream[T](stream: GenericStream[T] with Writer[T] with Closeable): Unit = {
    stream.foreach(println)
    stream.close(0)
  }

  // diamond problem
  trait Animal {
    def name: String
  }
  trait Lion extends Animal {
    override def name: String = "lion"
  }
  trait Tiger extends Animal {
    override def name: String = "tiger"
  }
  class Mutant extends Lion with Tiger //which override do we get?

  val m = new Mutant
  println(m.name) // tiger

  /*
  Mutant extends Animal with {override def name: String = "lion"}
  with Animal with {override def name: String = "tiger"}

  because tiger is mixed in second, it gets priority

  LAST OVERRIDE GETS PICKED
  how scala resolves the diamond problem
   */

  // the super problem + type linearization
  trait Cold {
    def print: Unit = println("cold")
  }
  trait Green extends Cold {
    override def print: Unit = {
      println("green")
      super.print
    }
  }

  trait Blue extends Cold {
    override def print: Unit = {
      println("blue")
      super.print
    }
  }

  class Red {
    def print: Unit = println("red")
  }

  class White extends Red with Green with Blue {
    override def print: Unit = {
      println("white")
      super.print
    }
  }
  val color = new White
  color.print //white, blue, green cold. no red??
  //linearizes as: White = AnyRef with <Red> with <Cold> with <Green> with <Blue> with <White>
  // super() looks at the trait to the left of current class
}
