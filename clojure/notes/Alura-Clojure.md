#Clojure

##Map, Filter e Remove

```
//Define uma função que retorna true ou false se a entrada é par
=> (defn par [x] (= 0 (rem x 2)))

//Defino x como sendo um array de 6 elementos
=> (def x [1 2 3 4 5 6])

//Aplica a função par a todos os elementos de x e recebe como resultado um novo array booleano
=> (map par x)
(false true false true false true

//Aplica a função par a todos os elementos de x e recebe como resultado um novo array dos elementos filtrados pela condição true
=>(filter par x)
(2 4 6)

//Aplica a função par a todos os elementos de x e recebe como resultado um novo array dos elementos filtrados pela condição false
=>(remove par x)
(1 3 5)
```