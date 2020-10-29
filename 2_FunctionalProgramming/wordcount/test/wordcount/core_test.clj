(ns wordcount.core-test
  (:require [clojure.test :refer :all]
            [wordcount.core :refer :all]
            [clojure.core.reducers :as r]))

(deftest pmap-frequencies-test
  (testing "pmap-frequencies-test"
    (is (= '({"more" 1
            "six" 1
            "three" 1
            "two" 1
            "seven" 1
            "five" 1
            "one" 1
            "four" 1
            "potato" 6})
           (pmap #(frequencies (get-words %1)) pages)))))



(deftest merge-counts-test
  (testing "merge-counts-test"
    (is (= {:x 1, :y 3, :z 1} (merge-counts {:x 1 :y 2} {:y 1 :z 1})))))

; partition-all: part of standard lib
(deftest partition-all-test
  (testing "partition-all-test"
    (is (= '((1 2 3 4) (5 6 7 8) (9 10))
         (partition-all 4 [1 2 3 4 5 6 7 8 9 10])))))

(deftest reducers-map-test
  (testing "reducers-map-test"
    (is (= [2 4 6 8]
           ; `reduce` needed to turn a reducible into a usable value
           (reduce conj [] (r/map (partial * 2) [1 2 3 4]))))))

(deftest partition-all-with-into-test
  (testing "partition-all-with-into-test"
    (is (= [2 4 6 8]
           ; `into` uses `reduce` internally 
           (into [] (r/map (partial * 2) [1 2 3 4]))))))


(deftest my-map-test-1
  (testing "my-map-test-1"
    (is (= [3 4 5 6]
           (into [] (my-map (partial + 2) [1 2 3 4]))))))

(deftest my-map-test-2
  (testing "my-map-test-2"
    (is (= [2 4 6 8]
           (into [] (my-map (partial * 2) [1 2 3 4]))))))


(deftest my-map-test-3
  (testing "my-map-test-3"
    (is (= [2 3 4 5]
           (into [] (my-map (partial + 1) [1 2 3 4]))))))


(deftest my-map-chain-test
  (testing "my-map-chain-test"
    (is (= [4 6 8 10]
           (into [] (my-map
                     (partial * 2)
                     (my-map
                      (partial + 1)
                      [1 2 3 4])))))))


(def v (into [] (range 10000)))


