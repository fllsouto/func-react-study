# Princípios de programação reativa	 - Week 2

## Funções e Estados
Até agora nossos programas não tinha **side-effects**, além disso o conceito de **tempo** não era importante. Mesmo por que, para um programa que termina, seja qualquer for a sequência de ações, o resultado será o mesmo (determinístico).

### Revisando o modelo de substituição
```scala
// considere
// Obs: A passagem de x é por valor então eu calculo antes de passar!!!!
def iterate(n: Int, f: Int => Int, x: Int) = 
  if (n == 0) x else iterate(n - 1, f, f(x))

def square(x: Int) = x * x

// Incorreto!
iterates(1, square, 3) => 
[1/n, square/f, 3/x] if (1 == 0) 3 else iterate(1 - 1, square, square(3)) =>
iterate(0, square, square(3)) => 
[0/n, square/f, square(3)/x] if (0 == 0) square(3) else iterate(0, square, square(square(3))) => 
square(3) => 
3 * 3 => 9

// Correto
iterates(1, square, 3) => 
[1/n, square/f, 3/x] if (1 == 0) 3 else iterate(1 - 1, square, square(3)) =>
iterate(0, square, 3 * 3) => iterate(0, square, 9)
[0/n, square/f, 9/x] if (0 == 0) 9 else iterate(0, square, square(9)) => 9
```

Considera-se que um objeto tenha **estado** se seus comportamentos são influenciados durante seu tempo de vida.

## Identidade e Mudança
Quando temos atribuições um novo problema surge, como podemos decidir se duas expressões são iguais? Exemplo

```scala
val x = E;
val y = E;

// Onde E é um expressão arbitrária, é razoável assumir que x e y são iguais. Podemos escrever então:

val x = E;
val y = x;
```

Essa propriedade recebe o nome de **transparência referêncial**. Eu posso provar que valores são iguais utilizando equivalência de operações. Essa técnica é útil para provar que eles são diferentes, basta achar um contra-exemplo que invalide a igualdade, porém provar a igualdade implica me provar um número infinito de combinações, o que é impraticável.

Para testar se x e y são iguais temos que:
- Executar as definições de uma sequência arbitrária de operações em F, que envolvem x e y, e observar o resultado

```scala
val x = new BankAccount
val y = new BankAccount
f(x, y) ==
f(x, x) ==
f(y, y) ==
f(y, x)

// Todo par de combinações tem que reproduzir a mesma resposta. Se isso for verdade então x e y são iguais

//Ex

x deposit 30 => val res1: Int = 30
y withdraw 20 => java.lang.Error: Insufficient funds

x deposit 30 => val res1: Int = 30
x withdraw 20 => val res2: Int = 10
```

Isso nos leva a concluir que o modelo de substituição não é válido quando temos atribuição:

```scala
val x = new BankAccount
val y = x => val y = new BankAccount

// porém
x != y
// O modelo de substituição deixa de valer quando adicionamos atribuições. Ele pode ser adaptado ao introduzirmos o conceito de "store", mas isso torna o modelo muito mais complexo.
```