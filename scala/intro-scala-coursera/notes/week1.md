# Introdução a Programação Funcional com Scala - Week 1

## Paradigmas na computação
*Paradigma, no campo da ciência, descreve conceitos distintos ou padrões de pensamento em alguma disciplina científica.* 

Existem atualmente três paradigmas principais na ciência da computação para escrever um programa:

- Imperativo
- Funcional
- Lógico

O paradigma de orientação a objeto pode ser encarado de forma ortogonal a esses três pois pode ser combinado com qualquer um dos três padadigmas citados, dependendo da linguagem.

### Programação imperativa
É o paradigma mais utilizado hoje em dia, e sua criação está presente desde o início do computador, com a criação da arquitetura Von Neumann. Existe uma forte correspondência entre esta arquitetura e o paradigma:

- Variáveis mutaveis <=> Células de memória
- Dereferência de variável <=> Carregar instruções
- Atribuição de variável <=> Armazenar instrução
- Estrutura de controle <=> Saltos

John Backus escreveu na década de 70 um artigo que questionava esse tipo de estrutura. Ele dizia que uma linguagem puramente imperativa era limitada pelo gargalo de sua arquitetura, isso teria que ser vencido com a criação de um alto nível de abstração sobre estruturas como coleções, polonônimos, figuras geométricas, cadeias de caracteres e etc, apoiado na teorização dessas estruturas.

Uma teoria consiste no segunte:

- Um ou mais tipos de dados
- Operações entre esses tipos de dados
- Leis que descrevem o relacionamento entre valores e operações

Um coisa que percebemos é que uma teoria normalmente [Não descreve mutações](http://math.stackexchange.com/questions/663578/how-is-a-computer-variable-defined-mathematically)

### Programando com mutação
Na matemática formal não temos a ideia de mutação, se algo é definido como tendo um valor este algo não será mutado para que obtenha outro valor. Para implementar conceitos matemáticos teóricos de alto nível não podemos ter mutação por que:
- As teorias não admitem isso
- Mutação podem destruir leis úteis às teorias

Para conseguir isso teremos que:
- Nos concentrar em definir teorias para operadores expressos através de funções
- Evitar mutação
- Pensar de forma abstrata e compor funções

### Avaliando uma aplicação de função
Aplicação de funções parametrizadas são avaliadas de uma forma parecida com operadores:
- Avaliar todos os argumentos, da esquerda para a direita
- Substituir a aplicação da função por ela mesma
- Substituir os parametros formais pelos argumentos atuais

```
def square(x: Int) = x*x
def sumOfSquare(x: Int, y: Int) = square(x) + square(y)

sumOfSquare(3, 2 + 2)
sumOfSquare(3, 4)
square(3) + square(4)
(3*3) + square(4)
9 + (4*4)
9 + 16
25
```

O nome desse processo é **modelo de substituição**, consiste em reduzir uma expressão para um valor, toda sua teoria é formalizada pelo λ calculos.

### Recursão
Nem toda expressão pode ser reduzida para um valor, um exemplo é uma função recursiva :

```
def loop(: Int) loop

loop -> loop ...
```

Existe duas estratégias de avaliação de função :
- **Call by value** : os valores são reduzidos antes que as funções sejam reescritas. Sua vantagem é que cada argumento da função é avaliado apenas uma vez.
- **Call by name** : os valores não são reduzidos até todas funções terem sido substituídas. Sua vantagem é que um argumento não é avaliado se ele não for utilizado no corpo da função, ex:

```
def square(x: Int) = x*x
def sumOfSquare(x: Int, y: Int) = square(x) + square(y)

sumOfSquare(3, 2 + 2)
square(3) + square(2+2)
(3*3) + square(2+2)
9 + (2+2)*(2*2)
9 + 4*4
25
```

Ambas estratégias terão o mesmo valor final se:
- Se as expressões reduzidas consistirem de funções puramente funcionais
- Ambas avaliações terminarem

E se não é garantido o termino? _Se **CBN** terminar em para **e** então **CBV** termina também para **e**_, a volta não é verdade... Ex:

```
def first(x: Int) = x

CBN: first(1, loop) -> 1
CBV: first(1, loop) -> first(1, loop) -> first(1, loop) -> ...
```

Em scala por default a avaliação é do tipo **CBV**, mas podemos forçar um tipo de avaliação **CBN** definindo um parametro com **=>**

```
def constOne(x: Int, y: => Int) = 1

constOne(1, loop) -> 1
constOne(loop, 1) -> constOne(loop, 1) -> constOne(loop, 1) -> ...
```

## Definição de valores
A passagem de parâmetro pode ser feita por nome ou valor, o mesmo princípio vale para **definições**. A definição com **def** é **by-name** e **val** é **by-value**, exemplo :

```
val x = 2
val y = square(x)
```

A avaliação de square(x) irá acontecer durante a atribuição para y, valendo 4 no final, por isso que **o nome se refere a um valor**. Exemplo:

```
// Essa definição é ok pois não será avaliado pois é by-name
def loop(: Boolena) = loop
def x = loop

//Mas essa trava, pois a avaliação por valor tenta avaliar o argumento loop
val x = loop
```

```
//como implementar uma função que and(a, b) == a && b

def and(a: Boolean, b: Boolean) = if(a) b else false
```
Uma boa prática de programação funcional é dividir uma tarefa em pequenas subtarefas. Funções que fazem sentido para um contexto específico devem ser colocadas em um lugar específico, para sua definição não atrapalhe o namespace das funções.


### Blocos
Um bloco é uma sequência de definições auto-contidas com um namespace isolado, a ultima linha de um bloco é uma expressão que define o seu valor, onde esse valor pode ser calculado com o auxílio das funções. Um bloco pode aparecer em qualquer lugar que uma expressão apareceria.

O escopo de um bloco é ele todo, variáveis externas e definições podem ser acessadas de dentro mas caso dentro do bloco o mesmo nome de uma variável seja redefinido ele obfuscará a definição externa, passado a valer apenas a definição interna. As variáveis recebidas nesse bloco são visíveis por todas as funções definidas, algo como uma **variável global de bloco**

```
def blockTest(x: Int) = {
	def pow2(x: Int): Int = x*x
	def pow3(x: Int): Int = pow2(x)*x

	pow3(x)
}
blockTest(10)
```

## Recursão de cauda

### Aplicando a regra da reescrita

```
def f(x_1, x_2, ..., x_n) = Body; ....f(v_1, v_2, ..., v_n)
def f(x_1, x_2, ..., x_n) = Body; ....[x_1/v_1, x_2/v_2, ..., x_n/v_n]Body

// [x_1/v_1, x_2/v_2, ..., x_n/v_n] significa que toda ocorrência de um x_i
//será trocada por um v_i, esse processo é chamado **substituição**.

//Ex: 

def gcd(a: Int, b: Int): Int = 
	if(b == 0) a else gcd(b, a % b)

-> gcd(14, 21)
-> if(21 == 0) 14 else gcd(21, 14 % 21)
-> if(false) 14 else gcd(21, 14 % 21)
-> gcd(21, 14 % 21)
-> gcd(21, 14)
-> if(14 == 0) 21 else gcd(14, 21 % 14)
-> if(false) 21 else gcd(14, 21 % 14)
-> gcd(14, 7)
-> if(7 == 0) 14 else gcd(7, 14 % 7)
-> gcd(7, 14 % 7)
-> gcd(7, 0)
-> if(0 == 0) 7 else gcd(0, 7 % 0)
-> if(true) 7 else gcd(0, 7 % 0)
7


def factorial(n: Int): Int = 
	if(n == 0) 1 else n * factorial(n - 1)
    
factorial(4)
if(4 == 0) 1 else 4 * factorial(4 - 1)
if(false) 1 else 4 * factorial(4 - 1)
4 * factorial(4 - 1)
4 * factorial(3)
4 * (if(3 == 0) 1 else 3 * factorial(3 - 1))
4 * (if(false) 1 else 3 * factorial(3 - 1))
4 * (3 * factorial(3 - 1))
4 * (3 * factorial(2))
4 * (3 * (if(2 == 0) 1 else 2 * factorial(2 - 1)))
4 * (3 * (if(false) 1 else 2 * factorial(2 - 1)))
4 * (3 * (2 * factorial(2 - 1)))
4 * (3 * (2 * factorial(1)))
4 * (3 * (2 * (if(1 == 0) 1 else 1 * factorial(1 - 1))))
4 * (3 * (2 * (if(false) 1 else 1 * factorial(1 - 1))))
4 * (3 * (2 * (1 * factorial(1 - 1))))
4 * (3 * (2 * (1 * factorial(0))))
4 * (3 * (2 * (1 * (if(0 == 0) 1 else 0 * factorial(0 - 1)))))
4 * (3 * (2 * (1 * (if(true) 1 else 0 * factorial(0 - 1)))))
4 * (3 * (2 * (1 * (1))))
24
// ^---- crescimento da pilha
```

Se uma função chamar a si mesma na ultima linha da sua definição o stack da função pode ser reaproveitado, isso é chamado **recursão de cauda**. No caso do gcd apenas sua chamada é executada, enquanto no factorial uma computação a mais precisa ser feita, que no caso é a multiplicação por **n**.
