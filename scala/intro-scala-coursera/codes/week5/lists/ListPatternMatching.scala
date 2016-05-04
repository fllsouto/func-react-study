object ListPatternMatching {
	def main(args: Array[String]): Unit = {
		var list = List(10, 7, 3, 9, 2)
		println("Lista: " + list)
		println("Sorted list : " + isort(list))
	}

	def isort(xs: List[Int]): List[Int] = xs match {
		case List() => List()
		case y :: ys => insert(y, isort(ys))
	}

	def insert(x: Int, xs: List[Int]): List[Int] = xs match {
		case List() => List(x)
		case y :: ys => if (x <= y) x :: y :: ys else y :: insert(x, ys)
	}
}