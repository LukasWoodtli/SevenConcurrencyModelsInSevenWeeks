(ns clojure-way.core-test
  (:require [clojure.test :refer :all]
            [clojure-way.core :refer :all]))


(def my-atom (atom 42))

(deftest atom-test
  (testing "atom-test"
    (is (= 42 @my-atom))))

(deftest atom-deref-test
  (testing "atom-deref-test"
    (is (= 42 (deref my-atom)))))


; update an atom with `swap!`
; It's important that the function provided to swap is
; sideeffect free! If the value in the atom is changed between
; reading and modifying it `swap!` retries and calls the provided
; function again
(def my-atom-2 (atom 42))
(swap! my-atom-2 inc)

(deftest atom-updated-test
  (testing "atom-deref-test"
    (is (= 43 (deref my-atom-2)))))

; any type can be used for atoms
(def session (atom {}))

(swap! session assoc :username "lukas")
(swap! session assoc :session-id 12343)

(deftest atom-map-test
  (testing "atom-map-test"
    (is (= {:username "lukas", :session-id 12343}
           (deref session)))))

; a validator can be used for setting or changing an atom
(def non-negative (atom 0 :validator #(>= % 0)))

(deftest atom-validator-test
  (testing "atom-map-test"
    (is (= 42 (reset! non-negative 42)))))

(deftest atom-validator-failing-test
  (testing "atom-map-test"
    (is (thrown? java.lang.IllegalStateException 42 (reset! non-negative -1)))))


; watchers
; watch functions are called after the value has changed.
; They will only be called once
(def a (atom 0))
(add-watch a :print #(println "Changed from " %3 " to " %4 "(Atom: " %2 ", Watch function tag" %1 ")"))
(swap! a + 2)

