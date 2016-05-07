object CombinatorFlatMap {

  def main(args: Array[String]): Unit = {
    println("Sequence : " + showCombinations(5,5))
  }

  def showCombinations(m: Int, n: Int): IndexedSeq[Tuple2[Int, Int]] = {
    (1 to m) flatMap { x =>
      (1 to n) map { y =>
        // "(" + x.toString + ", " + y.toString + ")"
        (x, y)
      }
    }
  }

  def isPrime(n: Int): Boolean = {
    (2 until n) forall {x => n % x != 0}
  }

}