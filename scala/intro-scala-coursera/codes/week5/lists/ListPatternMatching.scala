import math.Ordering

object ListPatternMatching {
	def main(args: Array[String]): Unit = {
		var list = List(10, 7, 3, 9, 2)
		var listWord = List("Conceitualmente","fazer","sentido","pois","uma","lista","não","vazia","é","um","caso","especial","de","um","conjunto","arbitário")
		var listFloat = List(1.5, 3.5, 0.5, 12.5, 0.9)
		println("Lista Num: " + list)
		println("Lista Words: " + listWord)
		println("Insertion sort : " + isort(list))

		println("Merge Sort Num: " + msort(list))
		println("Merge Sort Words: " + msort(listWord))
		println("Merge Sort Float: " + msort(listFloat))
	}

	def isort(xs: List[Int]): List[Int] = xs match {
		case List() => List()
		case y :: ys => insert(y, isort(ys))
	}

	def insert(x: Int, xs: List[Int]): List[Int] = xs match {
		case List() => List(x)
		case y :: ys => if (x <= y) x :: y :: ys else y :: insert(x, ys)
	}

	def msort[T](xs: List[T])(implicit ord: Ordering[T]): List[T] = {
		val n = xs.length / 2
		if (n == 0) xs
		else {
			def merge(xs: List[T], ys: List[T]): List[T] = {
				// xs match {
				// 	case Nil => ys
				// 	case x :: xs1 => {
				// 		ys match {
				// 			case Nil => xs
				// 			case y :: ys1 =>
				// 				if (x < y) x :: merge(xs1, ys)
				// 				else y :: merge(xs, ys1)
				// 		}
				// 	}
				// }
				(xs, ys) match {
					case (Nil, Nil) => Nil
					case (xs, Nil) => xs
					case (Nil, ys) => ys
					case (x :: xs1, y :: ys1) =>
						if (ord.lt(x, y)) x :: merge(xs1, ys)
						else y :: merge(xs, ys1) 
				} 
			}
			val (fst, snd) = xs splitAt n
			merge(msort(fst), msort(snd))
		}
	}


}