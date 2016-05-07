# Introdução a Programação Funcional com Scala - Week 6

## Vectors

O acesso de listas em Scala leva um tempo Linear, ou seja, para acessar um k-esimo elemento eu tenho que percorrer todos os outros elementos da lista. Uma alternativa para isso é utilizar **Vectors**. O crescimento de um Vector segue um comportamento curioso, Scala trabalha com vectors de tamanho máximo 32, a regra é a seguinte:

- Começamos com um vetor de 32 posições [0 ... 31] que contem realmente o dado que estamos guardando.
- Passado de 32 elementos o vetor é recriado, sendo que agora cada um dos 32 elementos tem uma lista de 32 elementos, com um total de 1024.
- O crescimento da árvore acontece em uma potência de 2^5, e essa estrutura se assemelha a uma árvore B

O acesso de um elemento tem complexidade Log(32)(n), onde n é o tamanho do vetor, isso garante um acesso muito rápido.

## For-Expressions
Uma for-expression é algo parecido com o for em linguagens imperativas, a diferença primordial é que uma lista resulta da iteração é criada, não alterando nada.

```scala
for(s) yield e
//Onde:
// S é uma sequencia de geradores e filtos
// E é uma expressão a qual os valores são retornados por uma iteração
```

Internamente uma for-expression é convertida em maps, filters e flatmaps

```scala
for {
  i <- 1 until n
  j <- 1 until i
  if (isPrime(i + j))
} yield (i, j)

//É convertido para

(1 until n) flatMap(i =>
  (1 until i).withFilter(j => isPrime(i + j))
  .map(j => (i, j)) //O i aqui está fixo
)

for(b <- books; a <- b.authors if a statsWith "Bird")
  yield b.title

books flatMap ( b =>
  b.authors.map( a => a startsWith "Bird" )
  .map( b => b.title)
)
```