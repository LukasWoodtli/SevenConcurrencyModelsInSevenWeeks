(ns channels.go-blocks-test
  (:require [clojure.test :refer :all]
            [channels.core :refer :all]
            [clojure.core.async :as async :refer :all
             :exclude [​map​ ​into​ ​reduce​ ​merge​ ​
                       partition​ ​partition-by​ ​take​]]))

; parking

(def ch (chan))

; Instead of using <!! to read from the channel, our go block 
; is using <!. The single exclamation mark indicates
; that this is the parking version of a channel read,
; not the blocking version.
(go
  (let [
        ; The initial state immediately parks, waiting for
        ; something to be available for reading from ch.
        ; When it is, the state machine transitions to state 2.
        x (<! ch)
        ; Next the state machine binds the value read from ch to x
        ; and then parks, waiting for another value to be available,
        ; after which it transitions to state 3.
        y (<! ch)]
    ; Finally, the state machine binds the value read from ch to y,
    ; prints a message, and terminates.
    (println "Sum:" (+ x y))))

(>!! ch 3)
(>!! ch 4)



; add
; The anonymous function `#(go (inc (<! %)))` creates a go block
; that takes a single channel argument, reads a single value from it,
; and returns a channel containing that value incremented by one.
; 
; This function is passed to iterate with an initial value of (go x)
; (a channel that simply has the value x written to it).
; 
; We read the y-th element of this sequence with nth, the value
; of which will be a channel containing the result of incrementing
; x y times.
; 
; Finally, we read the value of that channel with <!!.
(defn go-add [x y]
  (<!! (nth (iterate #(go (inc (<! %)))
                     (go x))
            y)))


(deftest test-go-add-1
  (testing "test-go-add-1"
    (is (= 20
           (go-add 10 10)))))

(deftest test-go-add-2
  (testing "test-go-add-2"
    (is (= 1010
           (go-add 10 1000)))))

(deftest test-go-add-3
  (testing "test-go-add-3"
    (is (= 10010
           (go-add 10 10000)))))


; Not working???
;; ; This takes a function (f) and a source channel (from).
;; (defn map-chan [f from]
;;   ; It starts by creating a destination channel (to),
;;   ; which is returned at the end of the function.
;;   (let [to (chan)]
;;     ; Before then, however, it creates a go block with go-loop,
;;     ; a utility function that’s equivalent to (go (loop …)). 
;;     (go-loop []
;;       ; The body of the loop uses when-let to read from `from`
;;       ; and bind the value read to x.
;;       (when-let [x (<! from)]
;;         ; If x isn’t null, the body of the when-let is executed,
;;         ; (f x) is written to to, and the loop executed again.
;;         (>! to (f x))
;;         (recur))
;;       ; If x is null, to is closed.
;;       (close! to))
;;     to))

; `map<` is the `core.async` version of map
; see also `filter<`, `mapcat<`, ...
(def map-chan map<)

(def ch_map (chan 10))

(def mapped (map-chan (partial * 2) ch_map))

(onto-chan ch_map (range 0 10))

(deftest test-map-chan
  (testing "test-map-chan"
    (is (=
        [0 2 4 6 8 10 12 14 16 18]
        (<!! (async/into [] mapped))))))


; `filter<` and `map<`
(def ch_filter (to-chan (range 0 10)))

(deftest test-filter
  (testing "test-filter"
    (is (=
         [0 4 8 12 16]
         (<!! (async/into [] (map<
                              (partial * 2)
                              (filter< even? ch_filter))))))))


; Sieve of Eratosthenes

(defn factor? [x y]
  (zero? (mod y x)))

; Doesn't work. Error with `recur`
;; (defn get-primes [limit]
;;   (let [primes (chan)
;;         numbers (to-chan (range 2 limit))]
;;     (go-loop [ch numbers]
;;       (when-let [prime (<! ch)]
;;         (>! primes prime)
;;         (recur (remove< (partial factor? prime) ch)))
;;       (close! primes))
;;     primes))

