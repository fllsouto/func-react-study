object IntegerList {

	//List(1,2)
	def apply[T](x1: T, x2: T): List[T] = new Cons(x1, new Cons(x2, new Nil))
	//List(1)
	def apply[T](x1: T): List[T] = new Cons(x1, new Nil)
	//List()
	def apply[T](): List[T] = new Nil

	def main(args: Array[String]): Unit = {
		var foo = new Cons[Int](1, new Nil[Int])
		foo = new Cons[Int](2, foo)
		foo = new Cons[Int](3, foo)
		foo = new Cons[Int](4, foo)
		foo = new Cons[Int](5, foo)
		foo = new Cons[Int](6, foo)
		foo = new Cons[Int](7, foo)

		// println("Valor de foo : " + foo)
		println("Head : " + foo.head)
		println("Tail : " + foo.tail.head)

		println("Third element: " + foo.nthFoo(2, foo))
		println("First element: " + foo.nthFoo(0, foo))
		// println("Seventh element: " + foo.nthFoo(7, foo))
		println("\n----\n")
		println("Third element: " + foo.nth(2))
		println("First element: " + foo.nth(0))
		// println("Seventh element: " + foo.nth(7))

		var bar = apply(1,2)
		println("Head bar : " + bar.head)
		println("Tail Head bar : " + bar.tail.head)

		// var bar2 = apply()
		// println("Head empty : " + bar2.head)
	}
}

trait List[T] {
	def isEmtpy: Boolean
	def head: T
	def tail: List[T]
}
class Cons[T](val head: T, val tail: List[T]) extends List[T] {
	def isEmtpy: Boolean = false

	def nth(n: Int): T = {
		def takeNth(acc: Int, list: List[T]): T = {
			if (this.isEmtpy) throw new IndexOutOfBoundsException("Nil.tail")
			else if (acc == n) return list.head;
			else takeNth(acc + 1, list.tail)
		}
		takeNth(0, this)
	}

	def nthFoo(n: Int, list: List[T]): T = {
		if (list.isEmtpy) throw new IndexOutOfBoundsException("Nil.tail")
		else if (n == 0) list.head
		else nthFoo(n - 1, list.tail)
	}
}

class Nil[T] extends List[T] {
	def isEmtpy: Boolean = true
	def head: Nothing = throw new NoSuchElementException("Nil.head");
	def tail: Nothing = throw new NoSuchElementException("Nil.tail");
}