package lectures.advancedscala.part3concurrency

import java.util.concurrent.Executors

object Intro extends App {

  /*
  interface Runnable {
    public void run()
  }
   */
  //JVM threads
  val runnable = new Runnable {
    override def run(): Unit = println("Running in parallel")
  }
  val aThread = new Thread(runnable)

  aThread.start() // gives the signal to the JVM to start a JVM thread
  // create a JVM thread => OS thread
  runnable.run() // doesn't do anything in parallel!
  aThread.join() // blocks until aThread finishes running

  val threadHello = new Thread(() => (1 to 5).foreach(_ => println("hello")))
  val threadGoodbye = new Thread(() => (1 to 5).foreach(_ => println("goodbye")))

  threadHello.start()
  threadGoodbye.start()
  // different runs produce different results!

  // executors
//  val pool = Executors.newFixedThreadPool(10)
//  pool.execute(() => println("something in the thread pool")) // will be executed by one of the 10 threads
//
//  pool.execute(() => {
//    Thread.sleep(1000)
//    println("done after 1 second")
//  })
//
//  pool.execute(() => {
//    Thread.sleep(1000)
//    println("almost done")
//    Thread.sleep(1000)
//    println("done after 2 seconds")
//  })
//
//  pool.shutdown()
  // pool.execute(() => println("should not appear")) // throws exception in the calling thread

  // pool.shutdownNow() // interrupts sleeping threads, making them throw exceptions

  //println(pool.isShutdown) // true (returns even if actions are still running. shutdown means it is not accepting new actions)

//  def runInParallel = {
//    var x = 0
//
//    val thread1= new Thread(() => {
//      x = 1
//    })
//
//    val thread2 = new Thread(() => {
//      x = 2
//    })
//
//    thread1.start()
//    thread2.start()
//    println(x)
//  }

//  for (_ <- 1 to 10000) runInParallel

  // race condition
  class BankAccount(var amount: Int) {
    override def toString: String = "" + amount
  }

  def buy(account: BankAccount, thing: String, price: Int) = {
    account.amount -= price
//    println("I've bought " + thing)
//    println("My account is now:+ ")
  }

//  for (_ <- 1 to 1000) {
//    val account = new BankAccount(50000)
//    val thread1 = new Thread(() => buy(account, "shoes", 3000))
//    val thread2 = new Thread(() => buy(account, "phone", 4000))
//
//    thread1.start()
//    thread2.start()
//    Thread.sleep(100)
//    if (account.amount != 43000) println("AHA: " + account.amount)
//  }

  /*
  thread1 (shoes): 50000
    - account = 50000 - 3000 = 47000
  thread2 (iphone): 50000
    - account = 5000 - 4000 = 46000 overwrites the memory of account.amount
   */

  // option 1: use synchronized()
  def buySafe(account: BankAccount, thing: String, price: Int) =
    account.synchronized {
      // no two threads can evaluate this at this same time
      account.amount -= price
      println("I've bought " + thing)
      println("My account is now:+ ")
    }

  // option #2: use @volatile

  /**
   * Exercises
   *
   * 1) construct 50 "inception" threads
   *    Thread1 -> Thread2 -> thread3 -> ...
   *    println("hello from thread #3")
   *    in REVERSE ORDER
   */
  def inceptionThreads(maxThreads: Int, i: Int = 1): Thread = new Thread(() => {
    if (i < maxThreads) {
      val newThread = inceptionThreads(maxThreads, i + 1)
      newThread.start()
      newThread.join()
    }
    println(s"hello from thread $i")
  })

  inceptionThreads(50).start()

  /*
  2
   */
  var x = 0
  val threads = (1 to 100).map(_ => new Thread(() => x += 1))
  /* 1) what is the biggest value possible for x? 100
  2) what is the smallest value possible for x? 1

  thread1: x = 0
  thread2: x = 0
    ...
  thread100: x = 0
  for all threads: x = 1 and write back to x
   */

  /*
  3) sleep fallacy
   */
  var message = ""
  val awesomeThread = new Thread(() => {
    Thread.sleep(1000)
    message = "scala is awesome"
  })

  message = "scala sucks"
  awesomeThread.start()
  Thread.sleep(2000)
  println(message) // value?
  /*
  almost always "scala is awesome
  but not guaranteed!

  main thread
    message = "Scala sucks"
    awesomeThread.start()
    sleep() - reliees execution
  awesome thread
    sleep(relieves execution
  (OS gives the CPU to some important thread - takes CPU for more than 2 seconds)
  (OS gives the CPU back to the MAIN thread)
    println("Scala sucks")
  (OS gives CPU to awesomethread)
    message = "scala is awesome"

   */

  // how to fix?
  // synchronize doesn't help, it only works for concurrent modification
  // must have threads join()
}
