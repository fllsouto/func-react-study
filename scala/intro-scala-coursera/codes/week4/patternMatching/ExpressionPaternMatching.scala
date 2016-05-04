object ExpressionPaternMatching{
	def main(args: Array[String]) {
		println("Number: " + show(Number(5)))
		println("Sum: " + show(Sum(Number(1), Number(41))))
	}

	def show(e: Expr): String = {
		e match {
			case Number(e) => e.toString
			case Sum(l, r) => show(l) + " + " + show(r)
		}
	}
}

trait Expr
case class Number(n: Int) extends Expr
case class Sum(e1: Expr, e2: Expr) extends Expr