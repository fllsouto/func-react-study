# Introdução a Programação Funcional com Scala - Week 7 

## Streams
Streams é uma estrutura de dados parecida com Listas, porém possuem um comportamento de lazy evaluation, seus valores só são processados a medida que são requisitados, ex:

```scala
val foo = Stream(1, 10)
foo: scala.collection.immutable.Stream[Int] = Stream(1, ?)

//Os valores depois de 1 não foram calculados

def fibFrom(a: Int, b: Int): Stream[Int] = a #:: fibFrom(b, a + b)
fibFrom: (a: Int, b: Int)Stream[Int]

val fibs = fibFrom(1,1).take(7)
fibs: scala.collection.immutable.Stream[Int] = Stream(1, ?)

fibs.toList()
res10: List[Int] = List(1, 1, 2, 3, 5, 8, 13)

//A chamada com take 7 é convertido para:

1 #:: fibFrom(1, 1 + 1)
(1 #:: (1 #:: fibFrom(2, 1 + 2)))
(1 #:: (1 #:: (3 #:: fibFrom(3, 2 + 3))))
...

//Por que take força o stream a calcular até o 7 elemento
```

Em scala a avaliação dos valores segue o princípio do **strict evaluation**, ou seja, chamada por valor. Podemos forçar o Scala a criar variáveis lazy, que serão calculadas apenas quando requisitadas, por exemplo :

```scala
def expr = {
	val x = {println("x"); 1 }
	lazy val y = {println("y"); 1 }
	def z = {println("z"); 1 }
	z + y + x + z + y + x
}

expr
=> xzyz

//Na classe 7.3 existe um exemplo completo de avaliação com o operador lazy e a estrutura stream
```