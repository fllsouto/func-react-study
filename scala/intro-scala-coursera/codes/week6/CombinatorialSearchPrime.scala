object CombinatorialSearchPrime {
  
  def main(args: Array[String]): Unit = {
    val n = 7
    println("Prime Combination for " + n + " :" + primeCombinator(n))
  }


  def isPrime(n: Int): Boolean = {
    (2 until n) forall {x => n % x != 0}
  }

  def primeCombinator(n: Int): IndexedSeq[Tuple2[Int, Int]] = {
    (1 until n) flatMap (i => 
      (1 until n) map (j => 
        (i, j))
    ) filter (pair => 
      isPrime(pair._1 + pair._2)
    )
  }

 
}