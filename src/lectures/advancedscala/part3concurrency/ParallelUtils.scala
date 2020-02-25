package lectures.advancedscala.part3concurrency

import java.util.concurrent.atomic.AtomicReference

import scala.collection.parallel.{ForkJoinTaskSupport, Task, TaskSupport}
import scala.collection.parallel.immutable.ParVector
import scala.concurrent.forkjoin.ForkJoinPool

object ParallelUtils extends App {

  // 1 - parallel collections

  val parList = List(1,2,3).par

  val aParVector = ParVector[Int](1,2,3)

  /*
  Seq
  Vector
  Arrays
  Map - Hash, Trie
  Set - Hash, Trie
   */

  def measure[T](operation: => T): Long = {
    val time = System.currentTimeMillis()
    operation
    System.currentTimeMillis() - time
  }

  val list = (1 to 10000).toList
  val serialTime = measure {
    list.map(_ + 1)
  }
  println(s"serial time: $serialTime")

  val parallelTime = measure {
    list.par.map(_ + 1)
  }
  // for small instances, serial is faster
  println(s"parallel time: $parallelTime")

  /*
  map-reduce model
  - split the elements into chunks - Splitter
  - operation
  - recombine - Combiner (reduce step)
   */

  // map, flatMap, filter, foreach, reduce, fold
  // map, flatMap, filter, foreach are pretty safe
  // reduce and fold are not

  println(List(1,2,3).reduce(_ - _)) // -4
  println(List(1,2,3).par.reduce(_ - _)) // 2
  // be careful with non-associative operators

  //synchronization
  var sum = 0
  List(1,2,3).par.foreach(sum += _)
  println(sum) // should be 6, but not guaranteed. race conditions!

  // configuring
  aParVector.tasksupport = new ForkJoinTaskSupport(new ForkJoinPool(2))
  /*
  alternatives
  - ThreadPoolTaskSupport - deprecated
  - ExecutionContextTaskSupport(EC)
   */

  aParVector.tasksupport = new TaskSupport {
    // execution context thing
    override val environment: AnyRef = ???

    // schedules a thread to run in parallel
    override def execute[R, Tp](fjtask: Task[R, Tp]): () => R = ???

    // same as execute but waits for join
    override def executeAndWaitResult[R, Tp](task: Task[R, Tp]): R = ???

    // number of cores to use
    override def parallelismLevel: Int = ???
  }

  // 2 - atomic ops and references
  val atomic = new AtomicReference[Int](2)

  val currentValue = atomic.get() // thread-safe read
  atomic.set(4) // thread-safe write

  atomic.getAndSet(5) // thread safe combo

  atomic.compareAndSet(38, 56)
  // if the value is 38, then set to 56
  // reference equality

  atomic.updateAndGet(_ + 1) // thread-safe function run
  atomic.getAndUpdate(_ + 1)

  atomic.accumulateAndGet(12, _ + _) // accepts arg + value in atomic, then run on function (thread-safe accumulation)
  atomic.getAndAccumulate(12, _ + _)
}
