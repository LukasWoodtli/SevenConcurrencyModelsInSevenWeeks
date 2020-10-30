(ns functional-programming.word-frequencies-test
  (:require [clojure.test :refer :all]
            [functional-programming.word-frequencies :refer :all]))

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

(deftest multiply-by-2-test
  (testing "multiply-by-2"
    (is (= 6 (multiply-by-2 3)))))


(deftest map-partial
  (testing "map with partial"
    (is (= '(0 2 4 6 8 10 12) (map (partial * 2) [0 1 2 3 4 5 6])))))

(deftest get-words-test
  (testing "get words test"
    (is (= ["one" "two" "three" "four"] (get-words "one two three four")))))

(deftest map-get-words-test
  (testing "map of get words test"
    (is (= [["one" "two" "three"] ["four" "five" "six"] ["seven" "eight" "nine"]]
           (map get-words ["one two three" "four five six" "seven eight nine"])))))


(deftest mapcat-get-words-test
  (testing "mapcat of get words test"
    (is (= ["one" "two" "three" "four" "five" "six" "seven" "eight" "nine"]
           (mapcat get-words ["one two three" "four five six" "seven eight nine"])))))
