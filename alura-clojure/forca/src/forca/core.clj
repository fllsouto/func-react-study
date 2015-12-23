(ns forca.core
  (:gen-class))

(defn perdeu [] (println "Você perdeu"))
(defn ganhou [] (println "Você ganhou"))

(def total-de-vidas 6)

(defm acertou-a-palavra-toda? [palavra acertos] true)

(defn jogo [vidas, palavra, acertos]
  (if (= vidas 0)
      (perdeu)
      (if (acertou-a-palavra-toda? palavra acertos)
        (ganhou)
        (println "Chuta, amigo!")
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
)



; (defn fib[x]
;     (if (= x 0) 0 
;     (if (= x 1) 1 
;     (+ (fib (- x 1)) (fib (- x 2))))))

; (require '[forca.core :as forca] :reload)
