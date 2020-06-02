(ns functional.core-test
  (:require [clojure.test :refer :all]
            [functional.core :refer :all]))


;; lazy

(range 0 10)

; This takes a long time
;(range 0 100000000)


(take 10 (range 0 100000000))

(take 10 (map (partial * 2) (range 0 100000000)))

; infinite sequence
(take 10 (iterate inc 0))


(take-last 5 (range 0 1000))