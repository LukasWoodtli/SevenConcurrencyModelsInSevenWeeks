(ns channels.multiple-channels
  (:gen-class)
  (:require [channels.core :as async :refer :all]
            [clojure.core.async :refer :all
             :exclude [​map​ ​into​ ​reduce​ ​merge​ ​
                       partition​ ​partition-by​ ​take​]]))


(def ch1 (chan))

(def ch2 (chan))

(go-loop []
  ; The `alt!` macro takes pairs of arguments, the first of
  ; which is a channel and the second of which is code that’s
  ; executed if there’s anything to read from that channel.
  (alt!
    ch1 ([x] (println "Read" x "from channel 1"))
    ch2 ([x] (println "Twice" x "is" (* x 2))))
  (recur))

(>!! ch1 "foo")

(>!! ch2 21)


; timeouts
(def ch_t (chan))

(let [t (timeout 1000)]
  (go (alt!
        ch_t ([x] (println "Read" x "from channel"))
        t (println "Timed out"))))



