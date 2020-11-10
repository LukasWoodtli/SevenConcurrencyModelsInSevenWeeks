(ns functional-programming.sum
  (:require [clojure.core.reducers :as r]))

(defn recursive-sum [numbers]
  (if (empty? numbers)
    0
    (+ (first numbers) (recursive-sum (rest numbers)))))

; Reduce takes three arguments:
; a function, an initial value, and a collection
(defn reduce-sum [numbers]
  (reduce (fn [acc x] (+ acc x)) 0 numbers))

(defn sum [numbers]
  (reduce + numbers))


(defn apply-sum [numbers]
  (apply + numbers))


(defn parallel-sum [numbers] (r/fold + numbers))