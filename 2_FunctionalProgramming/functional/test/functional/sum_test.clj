(ns functional.sum-test
  (:require [clojure.test :refer :all]
            [functional.sum :refer :all]))

(deftest recursive-sum-test
  (testing "Own recursive sum"
    (is (= (recursive-sum [1 1 2])
           4))))

(deftest reduce-sum-test
  (testing "reduce-sum-test"
    (is (= (reduce-sum [1 4 1]) 6))))


(deftest sum-test
  (testing "sum-test"
    (is (= (sum [1 2 7]) 10))))


(deftest sum-test-empty
  (testing "sum-test-empty"
    (is (= (sum []) 0))))

(deftest plus
  (testing "empty plus"
    (is (= (+) 0))))

(deftest times
  (testing "empty times"
    (is (= (*) 1))))


(deftest apply-sum-test
  (testing "apply-sum"
    (is (= (apply-sum [3 4 2]) 9))))


(def numbers (into [] (range 0 10000000)))

(println "Sum 1.")
(time (sum numbers))
(println "Sum 2.")
(time (sum numbers))
(println "Parallel Sum 1.")
(time (parallel-sum numbers))
(println "Parallel Sum 2.")
(time (parallel-sum numbers))
