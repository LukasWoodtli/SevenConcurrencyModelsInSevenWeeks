(ns channels.core-test
  (:require [clojure.test :refer :all]
            [channels.core :refer :all]
            [clojure.core.async :as async :refer :all
            :exclude [​map​ ​into​ ​reduce​ ​merge​ ​
              partition​ ​partition-by​ ​take​]]))

; synchronous (unbuffered) channel
(def c (chan))

; thread that reads (`<!!`) from the channel
(thread (println "Read: " (<!! c) "from c"))

; send (`>!!`) a text to the channel
(>!! c "Hello thread")

; This throws
; (>!! c nil)

(def bc (chan 5))
(>!! bc 0)
(>!! bc 1)
(close! bc)

(<!! bc)
(<!! bc)



(def ch (chan 10))

(onto-chan ch (range 0 10))
(<!! (async/into [] ch))


(def ch_2 (chan 10))

(writeall!! ch_2 (range 0 10))


(deftest test-readall
  (testing "test-readall"
    (is (= [0 1 2 3 4 5 6 7 8 9]
           (readall!! ch_2)))))


; using core.async
(def ch_3 (chan 10))

; The onto-chan function writes the entire
; contents of a collection onto a channel,
; closing it when the collection’s exhausted.
(onto-chan ch_3 (range 0 10))

(def res_ch3 (<!! (async/into [] ch_3)))

(deftest test-async-into
  (testing "test-async/into"
    (is (= [0 1 2 3 4 5 6 7 8 9]
           res_ch3))))


(def dropping_channel (chan (dropping-buffer 5)))

(onto-chan dropping_channel (range 0 10))

; async/into takes an initial collection 
; and a channel and returns a channel
(deftest test-dc
  (testing "test-dc"
    (is (= (<!! (async/into [] dropping_channel))
           [0 1 2 3 4]))))


; dropping buffer
(def dropping-channel (chan (dropping-buffer 5)))

(onto-chan dropping-channel (range 0 10))

(deftest test-dropping-buffer
  (testing "test-dropping-buffer"
    (is (= (<!! (async/into [] dropping-channel))
           [0 1 2 3 4]))))


; sliding buffer
(def sliding-channel (chan (sliding-buffer 5)))

(onto-chan sliding-channel (range 0 10))

(deftest test-sliding-buffer
  (testing "test-sliding-buffer"
    (is (= (<!! (async/into [] sliding-channel))
           [5 6 7 8 9]))))
