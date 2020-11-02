(ns functional-programming.sum-test
  (:require [clojure.test :refer :all]
            [functional-programming.server :refer :all]))


(deftest sentence-split-test
  (testing "sentence-split-test"
    (is (= ["This is a sentence." "Is this?!" "A fragment"]
           (sentence-split "This is a sentence. Is this?! A fragment")))))



(deftest is-sentence-test-1
  (testing "is-sentence-test-1"
    (is (= "This is a sentence."
           (is-sentence? "This is a sentence.")))))

(deftest is-sentence-test-2
  (testing "is-sentence-test-2"
    (is (= nil
           (is-sentence? "A sentence doesn't end with a comma,")))))


; reductions is part of the std lib
; it returns all the intermediate values of a reduction
(deftest reductions-test
  (testing "reductions-test"
    (is (= [1 3 6 10]
           (reductions + [1 2 3 4])))))


(deftest sentece-join-test-1
  (testing "sentece-join-test-1"
    (is (= "Start of another"
           (sentence-join "A complete sentence." "Start of another")))))


(deftest sentece-join-test-2
  (testing "sentece-join-test-2"
    (is (= "This isa sentence."
           (sentence-join "This is" "a sentence.")))))
