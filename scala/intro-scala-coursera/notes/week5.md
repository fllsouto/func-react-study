# Introdução a Programação Funcional com Scala - Week 5

## Listas
Listas são estruturas de dados muito uteis em programação funcional. Suas principais características são:

- Imutabilidade
- São recursivas, enquanto Arrays são flat

## Valores implícitos

No seguinte exemplo:

```scala
//Example from http://docs.scala-lang.org/tutorials/tour/implicit-parameters.html

abstract class SemiGroup[A] {
  def add(x: A, y: A): A
}
abstract class Monoid[A] extends SemiGroup[A] {
  def unit: A
}
object ImplicitTest extends App {
  implicit object StringMonoid extends Monoid[String] {
    def add(x: String, y: String): String = x concat y
    def unit: String = ""
  }
  implicit object IntMonoid extends Monoid[Int] {
    def add(x: Int, y: Int): Int = x + y
    def unit: Int = 0
  }
  def sum[A](xs: List[A])(implicit m: Monoid[A]): A =
    if (xs.isEmpty) m.unit
    else m.add(xs.head, sum(xs.tail))
  println(sum(List(1, 2, 3)))
  println(sum(List("a", "b", "c")))
}

```

O compilador irá inferir o tipo necessário e chamará o respectivo companion object necessário. Um valor implícito tem que existir, caso contrário um erro é levantado.

Métodos muito úteis para processar listas são:
- Map - Mapeia cada elemento em uma função
- Filter - Filtra os elementos que passam na condição
- FilterNot - - Filtra os elementos que não passam na condição
- Partition - Constroi uma tupla com Filter e FilterNot
- TakeWhile - Pega elementos enquanto a condição for verdadeira
- DropWhile - Remove elementos da lista enquanto a condição for verdadeira
- Span - Constroi uma tupla com TakeWhile e FilterWhile