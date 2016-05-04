# Introdução a Programação Funcional com Scala - Week 4

## Polimorfismo


## Parametros T

É uma generaliação para que uma estrutura possa recer um tipo de dado abstrato, por exemplo:

```scala

List[T]
//Na verdade o type parameter T pode ser omitido pois o
//compilador do scala na maioria dos casos consegue deduzir o tipo
//corretamente
```

Nothing é um subtipo de qualquer outro tipo.	


Type parameters não afetam o code evaluation do Scala, ele só serve para o compilador e é removido antes da avaliação do programa, isso se chama **type ensure**.

## Funções como objetos
Em Scala tudo é um objeto, então podemos pensar que funções anônimas também são convertidas em objetos:

```scala
val f = (x: Int) => x * x
f(7)

//É convertido para

val f = new Function1[Int, Int] {
  def apply(x: Int) = x * x
}
f.apply(7)
```

Vemos então que funções são objetos, mas a própria função **apply** é um objeto? Se sim a sua avaliação levaria a um looping infinito. Uma função definida com **def** não segue estritamente essa regra. A expansão do f para uma função anônima chama **eta-expansion**


Podemos construir qualquer coisa com objetos e funções, sem que tipos primitivos sejam necessários.


## Subtypes e Generics
Considere que tenho a seguinte função:

```scala
def assertAllPost(s: IntSet): Intset

//Podemos ter três casos
//Empty
//Non Empty
//Exception(Nothing)
```

Para alterar a assinatura da função para aceitar tanto Empty como NonEmpty temos que encontrar uma forma de expressão os dois tipos. Uma forma possível e a seguinte:

```scala
def assertAllPost[S <: IntSet](r: S): S = ...
```

O simbolo **<:** funciona como uma **upper bound** do tipo de parametro de S.

- S <: T significa que S é um subtipo de T
- S >: T significa que S é um supertipo de T, ou T é um subtipo de S

com isso podemos limitar tanto inferiormente como superiormente o tipo que uma estrutura está recebendo

### Covariancia
Dado que temos:

```scala
NonEmpty <: IntSet

List[NonEmpty] <: List[IntSet]?
```

Conceitualmente fazer sentido, pois uma lista não vazia é um caso especial de um conjunto arbitário. O nome disso é **covariância**. Porém nem todos os tipos em scala aceitam covariância, um exemplo deles é o Array

Seja C[T] um tipo parametrizado e A, B são tipos tal que A <: B, existe três possibilidades de relação entre C[A] e C[B]:

- C[A] <: C[B] ==> C é covatiante
- C[A] >: C[B] ==> C é contravariante
- Nem C[A] quanto C[B]são subtipo do outro ==> C é não variante

Scala deixa que isso seja definido de forma simples através de uma anotação:

```
class C[+A] ==> C é covariante
class C[-A] ==> C é contravariante
class C[A]  ==> C é não variante
```

## Pattern Matching
Partner Matching é uma generalização do conceito de switch case em Java/C :

```scala
def eval(e: Expr): Int = e match {
  case Number(n) => n
  case Sum(e1, e2) => eval(e1) + eval(e2)
}
```

Pontos importantes :
- É dividido em casos
- Cada caso associa uma expressão com um padrão
- Um Match erro é lançado caso o pattern procurado não exista.
- Um padrão de construtor C(p1, ..., pn) mapeia todos os valores do tipo C ou subtipo que possuem os atributos p1, ..., pn


### Exemplo de avaliação

```scala
evail(Sum(Number(1), Number(2)))

Sum(Number(1), Number(2)) match {
  case Number(1)=> n
  case Sum(e1, e2) => eval(e1) + eval(e2)
}

eval(Number(1)) + eval(Number(2))


Number(1) match {
  case Number(1)=> n
  case Sum(e1, e2) => eval(e1) + eval(e2)
} + eval(Number(2))

1 + eval(Number(2))
```
