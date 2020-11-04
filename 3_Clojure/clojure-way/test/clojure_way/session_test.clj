(ns clojure-way.session-test
   (:require [clojure.test :refer :all]
             [clojure-way.session :refer :all]))


 (deftest next-session-id-test
   (testing "next-session-id-test"
     (is (= [0 1 2 3]
            (doall [(reset! last-session-id 0)
                    (next-session-id)
                    (next-session-id)
                    (next-session-id)])))))
