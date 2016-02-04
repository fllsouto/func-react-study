(ns forca.core
  (:gen-class))

(defn perdeu [] (println "Você perdeu"))
(defn ganhou [] (println "Você ganhou"))

(def palavra-secreta "MELANCIA")
(def total-de-vidas 6)

(defn letras-faltando [palavra acertos]
  (remove (fn [letra] (contains? acertos (str letra))) palavra)
)

(defn acertou-a-palavra-toda? [palavra acertos] 
  (empty? (letras-faltando palavra acertos))
)

(defn le-letra! [] (read-line))

(defn acertou? [chute palavra] (.contains palavra chute))

(defn imprime-forca [vidas palavra acertos]
  (println "Vidas :" vidas)
  (doseq [letra (seq palavra)]
    (if (contains? acertos (str letra))
      (print letra " ")
      (print "_" " ")))
  (println ))

(defn jogo [vidas palavra acertos]
  (imprime-forca vidas palavra acertos)
  (cond 
      (= vidas 0) (perdeu)
      (acertou-a-palavra-toda? palavra acertos) (ganhou)
      :else
        (let [chute (le-letra!)]
          (if (acertou? chute palavra)
            (do
              (println "Acertou o chute!")
              (jogo vidas palavra (conj acertos chute))
            )
            (do
              (println "Errou o chute!")
              (jogo (dec vidas) palavra (conj acertos chute)))))))


(defn comeca-o-jogo []
  (jogo total-de-vidas palavra-secreta #{}))
(defn -main  [& args]
  (comeca-o-jogo))

; (defn fib [x]
;   (if (>= x 2)
;     (+ (fib (- x 1)) (fib (- x 2)))
;     x
;   )
; )



; (defn fib[x]
;     (if (= x 0) 0 
;     (if (= x 1) 1 
;     (+ (fib (- x 1)) (fib (- x 2))))))

; (require '[forca.core :as forca] :reload)
