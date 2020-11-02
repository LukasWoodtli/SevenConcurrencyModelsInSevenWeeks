(ns functional-programming.concurrency-test
  (:require [clojure.test :refer :all]
             [clojure.core.reducers :as r]))


(def expected-result 999000)

; reduce sequence build of a lazy sequence
; elements in each sequence are generated as needed
(deftest reduce-map-1
  (testing "reduce-map-1"
    (is (= expected-result (reduce + (map (partial * 2) (range 1000)))))))

; Generate the entire mapped sequence and reduce it after
; `doall` forces a lazy sequence to be fully realized
(deftest reduce-map-2
  (testing "reduce-map-2"
    (is (= expected-result (reduce + (doall (map (partial * 2) (range 1000))))))))

; reduce a semi-lazy sequence that is generated in parallel
(deftest reduce-map-3
  (testing "reduce-map-3"
    (is (= expected-result (reduce + (pmap (partial * 2) (range 1000)))))))


(deftest reduce-map-4
  (testing "reduce-map-4"
    (is (= expected-result (reduce + (r/map (partial * 2) (range 1000)))))))

; generate the rnage first and reduce in parallel
(deftest reduce-map-5
  (testing "reduce-map-5"
    (is (= expected-result (r/fold + (r/map (partial * 2) (into [](range 1000))))))))


(def sum (future (+ 12 3 4 5)))

; Dereferencing a future will block until 
; value is available (or realized)
(deftest dereference-future
  (testing "dereference-future"
    (is (= 24 (deref sum))))) 

; `@sum` is short for `(deref sum)`
(deftest dereference-at-future
  (testing "dereference-at-future"
    (is (= 24 @sum))))


(def meaning-of-life (promise))

; printing hapens on different thread (created with `future`)
(future (println "The meaning of life is: " @meaning-of-life))

; `deliver` unblocks the waiting print threac
(deliver meaning-of-life 42)



