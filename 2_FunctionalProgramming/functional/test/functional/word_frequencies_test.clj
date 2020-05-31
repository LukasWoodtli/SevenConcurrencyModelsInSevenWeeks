(ns functional.sum-test
  (:require [clojure.test :refer :all]
            [functional.wordfreq :refer :all]))

(deftest word-frequencies-test
  (testing "Test word frequencies"
    (is (= (word-frequencies ["one" "potato" "two" "potato" "three" "potato" "four"])
           {"four" 1, "three" 1, "two" 1, "potato" 3, "one" 1}))))

; Standard library has already a function that count frequencies
(deftest frequencies-test
  (testing "Test word frequencies standard library"
    (is (= (frequencies ["one" "potato" "two" "potato" "three" "potato" "four"])
           {"one" 1, "potato" 3, "two" 1, "three" 1, "four" 1}))))

; Other standard functions
(deftest map-test-1
  (testing "map inc"
    (is (= '(1 2 3 4 5 6) (map inc [0 1 2 3 4 5])))))

(deftest map-test-2
  (testing "map inc"
    (is (= '(0 2 4 6 8 10) (map (fn [x] (* 2 x)) [0 1 2 3 4 5])))))