object Polinomials {
  class Poly(terms0: Map[Int, Double]) {
    
    val terms = terms0 withDefaultValue 0.0
    
    def this(bindings: (Int, Double)*) = this(bindings.toMap)
    
    def + (other: Poly) = new Poly(terms ++ (other.terms map adjust))

    def adjust(term: (Int, Double)): (Int, Double) = {
      val (exp, coeff) = term
      exp -> (coeff + terms(exp))
    }

    override def toString = 
      (for ((exp, coeff) <- terms.toList.sorted.reverse) yield coeff + "x^" + exp) mkString " + "
  }

  def main(args: Array[String]): Unit = {
    val p1 = new Poly(1 -> 2.0, 3 -> 4.0, 5 -> 6.2)
    val p2 = new Poly(0 -> 3.0, 3 -> 7.0)

    println("Poly 1 : " + p1)
    println("Poly 2 : " + p2)

    println("Poly 3 : " + (p1 + p2 ))
  }
}