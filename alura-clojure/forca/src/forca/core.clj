(ns forca.core
  (:gen-class))

(defn perdeu [] (println "Você perdeu"))
(defn ganhou [] (println "Você ganhou"))

(def total-de-vidas 6)

(defn letras-faltando [palavra acertos]
  (remove (fn [letra] (contains? acertos (str letra))) palavra)
)

(defn acertou-a-palavra-toda? [palavra acertos] 
  (empty? (letras-faltando palavra acertos))
)

(defn le-letra! [] (read-line))

(defn acertou? [chute palavra] (.contains palavra chute))

(defn avalia-chute [chute vidas palavra acertos]
  (if (acertou? chute palavra) 
    (jogo vidas palavra (conj acertos chute))
    (jogo (dec vidas) palavra (conj acertos chute))
  )
)

(defn jogo [vidas palavra acertos]
  (if (= vidas 0)
      (perdeu)
      (if (acertou-a-palavra-toda? palavra acertos)
        (ganhou)
        (avalia-chute (le-letra!) vidas palavra acertos)
      )
  )
)


(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))

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
