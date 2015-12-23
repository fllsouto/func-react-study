(defn fib [x]
  (if (>= x 2)
    (+ (fib (- x 1)) (fib (- x 2)))
    (if (= x 0)
        (0)
        (1) 
    )
  )
)