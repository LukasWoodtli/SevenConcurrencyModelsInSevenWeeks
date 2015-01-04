(defn recursive-sum [numbers]
  (if (empty? numbers)
    0
    (+ (first numbers) (recursive-sum (rest numbers)))
  )
)

(recursive-sum [1 1 2])




(defn reduce-sum [numbers]
  (reduce (fn [acc x] (+ acc x)) 0 numbers))

(reduce-sum [1 4 1])


(defn sum [numbers]
  (reduce + numbers))

(sum [1 2 7])
(sum [])


(+)
(*)



(defn apply-sum [numbers]
  (apply + numbers))


(apply-sum [3 4 2])