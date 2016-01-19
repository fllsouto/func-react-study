#Progamming Clojure

### Symbols
Simbolos são identificadores como **+**, **concat**, **java.lang.String** usados para nomear coisas, como funções, estrutura de dados, namespaces e etc.

### Boolean
Fatos importantes :
- Listas vazias não são avaliadas em **false**
- Zero não é avaliado em **false**

Além disso é boa prática na hora de criar **funções predicado(que retornam true ou false)** que no fim no nome exista um **?** .

```(find-doc "\?#") //Mostra a documentação de todos métodos implementados```


### Maps, Keywords e Registros
Maps são estruturas de dados semelhantes a uma **hash table** :
```
//Cada par é uma chave e valor, e para separar o clojure usa espaço em branco
=>(def inventors {"Lisp" "McCarthy" "Clojure" "Hickey"})

//Mas também pode ser usado **,**
=>(def inventors {"Lisp" "McCarthy", "Clojure" "Hickey"})

//Maps são funções e pode-se passar parâmetros para ela :
=>(inventors "Lisp")
"McCarthy"

//Podemos usar uma função mais verbosa para recuperar o valor,
// (get the-map key not-found-val)

=>(get inventors "Clojure" "foo")
"Hickey"

=>(get inventors "Clojurex" "foo")
"foo"
```
Qualquer estrutura de dados em clojure pode ser uma chave em um map. Um exemplo muito usado de estrutura como chave é a **keyword**, que consiste em um simbolo começado com **:**

```
(def inventors {:Lisp "McCarthy" :Clojure "Hickey"})
//Keywords também são funções e pode funcionar do seguinte jeito

(:Lisp inventos)
"McCarthy"
```
Registros funcionam parecido com uma struct em C.

```
//Defino a estrutura e seus campos
forca.core=> (defrecord Book [title author])
forca.core.Book

//Defino um novo livro
forca.core=> (def b (->Book "Crime and Pushiment" "Dostoievsky"))
#'forca.core/b

//O livro está definido no simbolo b
forca.core=> b
#forca.core.Book{:title "Crime and Pushiment", :author "Dostoievsky"}

//Posso usar a ideia de keywords como funções para acessar os atributos do meu livro, como se fosse um **getter**
forca.core=> (:title b)
"Crime and Pushiment"

forca.core=> (:author b)
"Dostoievsky"
```

### Funções
Funções nada mais são do que o processamento de uma lista. Elas seguem basicamente a seguinte estrutura :

```(defn name doc-string? attr-map? [params*] body)```

- defn é uma macro para definição de funções
- name é o nome da funçào
- doc-string? é uma explicação do que a função faz, acessivel via **(doc name)**
- [params*] é a lista de parametros, podem ir de 0 a quantos forem necessários. Pode ser separados com espaço ou virgula
- body é o corpo da função

#### Aridade de parâmetros
Clojure leva a sério a quantidade de parâmetros definida e passada na chamada de uma função. Para contornar isso podemos definir a mesma função com diversos comportamentos, de acordo com a **aridade da chamada**

```
forca.core=> (defn say-hello
        #_=> "Return a nicely hello"
        #_=> ([] "Hello World")
        #_=> ([username] (str "Hello " username))
        #_=> )
#'forca.core/say-hello
forca.core=> (doc say-hello)
-------------------------
forca.core/say-hello
([] [username])
  Return a nicely hello
nil
forca.core=> (say-hello)
"Hello World"
forca.core=> (say-hello "Fellipe")
"Hello Fellipe"

```