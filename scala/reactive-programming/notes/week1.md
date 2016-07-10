# Princípios de programação reativa	 - Week 1

## Introdução
Programação reativa descreve uma série de padrões e técnicas que se tornaram muito importantes nos ultimos anos.

### Mudanças nos requisitos nos ultimos anos

|        | 10 anos atrás | Hoje em dia |
|--------------------|----------|--------|
| Nós em um servidor | 10's     | 1000's
| Tempo de resposta  | Segundos | Milisegundos
| Downtime           | Horas    | Nenhum
| Volume de dados    | GBs      | TBs -> PBs

### Novas arquiteturas

**Anteriormente** : Gestão de servidores e containers
**Hoje** : Aplicações Reativas

O dicionário **Meriam Webster** define o termo Reactive como:

```asciidoc
Reactive : 
1 - Done in response to a problem or situation
2 - Reacting to problems when they occur instead of doing something to prevent them
```

Um sistema que responde prontamente a um evento é chamado de **Event-driven**, podemos ampliar a definição para outros pilares:

- **Escalável** : É um sistema que reage a uma mudança na carga
- **Resiliente** : É um sistema que reage a falhas
- **Responsivo** : É um sistema que reage ao usuário

```asciidoc
   /------> Escalável -> \
  /                        \
Even driven --------------> |-> Responsivo
  \                        /
   \------> Resiliente -> /
```

### Event-driven

Tradicionalmente sistemas são compostos por multiplas threads, que se comunicam com estados compartilhados e sincronizados.

**Resultado:** Alto acoplamento e dífícil de compor.

Podemos criar sistemas com baixo acoplamento entre as partes através da manipulação de eventos.

**Resultado:** Eventos podem ser manipulados assicronamente, evitando o bloqueamento.

### Escalável
Uma aplicação é escalável se puder ser expandida de acordo com seu uso. Existe dois tipos de escalabilidade

- **Escalabilidade vertical** : Faz uso dos recursos computacionais, como memória, unidades de processamento e etc
- **Escalabilidade horizontal** : Faz uso de diversas instâncias da aplicação em diferentes nós

Para atingir escalabilidade é ideal minimizar o compartilhamento de estados mutáveis, além disso para obter escalabilidade horizontal é importante existir **transparência de localidade**, que significa que o recurso pode estar presente na mesma máquina ou através da internet, mas para o funcionamento da aplicação tem que ser o mesmo, independente do caso. Esse tipo de transparência está intimamente ligado com a recuperação de falhas.

### Resiliente
Um aplicação é considerada resiliente se tem a capacidade de se **recuperar rapidamente de uma falha**.  Falhas podem ser:

- Falhas de software
- Falhas de hardware
- Falhas de conexão e etc...

Tipicamente não se pode conferir resiliência a um sistema posteriormente, essa capacidade tem que ser parte do design desde o princípio. Existem técnicas importantes para obter resiliência, algumas delas são:

- Baixo acoplamento
- Forte encapsulamento de estados
- Uma hierarquia de supervisores universal **?!**

### Responsividade
Uma aplicação responsiva é aquela que provem uma interação em tempo real rica, mesmo que esteja sobrecarregada ou que esteja enfrentando falhas. Idealmente responsividade é uma caracteristica que se obtem ao construir uma arquitetura com as três características anteriores.

### Lindando com eventos
É comum lidar com eventos utilizando call-backs, isso é bem comum em linguagem como Java e Javascript. Temos o seguinte exemplo utilizando a biblioteca Swing:

```scala
class Counter extends ActionListener {
	private var count = 0
  button.addActionListener(this)
  
  def actionPerformed(e: ActionEvent): Unit = {
  	count += 1
  }
}
```

Sempre que o botão em questão for clicado um evento vai ser registrado e action será processada. Essa solução apresenta diversos problemas, eles são:

- O processamento do evento espera um resposta e altera uma variável compartilhada, ou seja, possui um [side effect](https://en.wikipedia.org/wiki/Side_effect_%28computer_science%29)
- É difícil de compor listener em um nível maior de abstração
- Esse tipo de abstração pode levar ao que é conhecido como Callback Hell

### Como melhorar isso?
Usando construtores vindos da programação funcional afim de obter abstrações e compor abstrações de eventos. Importante notar sobre os eventos que:

- São abstrações de primeira classe
- São frequentemente representados como mensagens
- Manipuladores de eventos também são de primeira classe
- Manipuladores de eventos complexos podem ser compostos a partir de primitivas

### Monads
Uma monad **M** é um tipo paramétrico M[T] com duas operações: **flatMap** e **unit**, que precisam satisfazer algumas leis:

```scala
trait M[T] {
  def flatMap[U](f: T => M[U]): M[U]
  
  def unit[T](x: T): M[T]
}

// Leis

// Associatividade
(m flatMap f) flatMap g == m flatMap (x => f(x) flatMap g)

// Left unit
unit(x) flatMap f == f(x)

// Right unit
m flatMap unit == m

// Exemplo de Monad

abstract class Option[+T] {
  def flatMap[U](f: T => Option[U]): Option[U] = this match {
    case Some(x) => f(x)
	case None => None
  }
}

// Com a definição acima podemos mostrar que a classe Option é uma Monad e que respeita as leis citadas
// Podemos ainda utilizar Monads em expressões for para simplifica-las

for (
  y <- for (x <- m; y <- f(x)) yield y 
  z <- g(y)
) yield z
==
for (
  x <- m;
  y <- f(x);
  z <- g(y)
) yield z

for (x <- m) yield x == m
```