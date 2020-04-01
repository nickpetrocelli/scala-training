package lectures.advancedscala.part4implicits

import java.{util => ju}

object ScalaJavaConversions extends App {

  import collection.JavaConverters._

  val javaSet: ju.Set[Int] = new ju.HashSet[Int]()
  (1 to 5).foreach(javaSet.add)
  println(javaSet)

  val scalaSet = javaSet.asScala
  println(scalaSet)

  /*
  Iterator
  Iterable
  ju.List = scala.mutable.Buffer
  ju.Set = scala.mutable.St
  ju.Map = scala.mutable.Map
   */

  import collection.mutable._
  val numbersBuffer = ArrayBuffer[Int](1,2,3)
  val juNumbersBuffer = numbersBuffer.asJava

  println(juNumbersBuffer.asScala eq numbersBuffer) //True! reference equality... sometimes

  val numbers = List(1,2,3)
  val juNumbers = numbers.asJava
  val backToScala = juNumbers.asScala
  println(backToScala eq numbers) //not equal
  println(backToScala == numbers) //equal

  // juNumbers.add(7) UnsupportedOperationException

  /*
  Exercise
  create a scala-java Optional-Option
   */

  class ToScala[T](value: => T) {
    def asScala: T = value
  }

  implicit def asScalaOptional[T](o: ju.Optional[T]): ToScala[Option[T]] = {
    new ToScala[Option[T]](
      if (o.isPresent) Some(o.get) else None
    )
  }

  val juOptional: ju.Optional[Int] = ju.Optional.of(2)
  val scalaOption = juOptional.asScala
  println(scalaOption)
}
