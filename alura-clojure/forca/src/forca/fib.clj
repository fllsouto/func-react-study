  (defn fib [x]
    (if (>= x 2)
      (+ (fib (- x 1)) (fib (- x 2)))
      (if (= x 0)
          (0)
          (1) 
      )
    )
  )

(defn fib-tail [x]
  (loop [a 1 b 1 numero 2]
    (if
      (= numero x) b
      (recur b (+ a b) (inc numero))
    )
  )
)

(defn soma [x]
  (loop [soma 0 contador 1]
    (if 
      (> contador x) soma
      (recur (+ soma contador) (inc contador))
    )
  )
)