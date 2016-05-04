object HighOrderList {
  def main(args: Array[String]): Unit = {
    val list1 = List[Int](1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    val list2 = List[String]("a", "a", "a", "b", "c", "c", "a")

    println("List squareList1 : " + squareList1(list1))
    println("List squareList2 : " + squareList2(list1))

    println("List pack : " + pack(list2))
    println("List encode : " + encode(list2))
  }

  def  squareList1(xs: List[Int]): List[Int] = xs match {
    case Nil => Nil
    case y :: ys => y * y :: squareList1(ys) 
  }

  def squareList2(xs: List[Int]): List[Int] = {
    xs map ((x: Int) => x * x)
  }

  def pack[T](xs: List[T]): List[List[T]] = xs match {
    case Nil => Nil
    case y :: ys => (xs span (x => x == y)) match {
      case (first, rest) => first :: pack(rest)
    }
  }

  def encode[T](xs: List[T]): List[(T, Int)] = {
    pack(xs) map (ys => (ys.head, ys.length))
  } 
}