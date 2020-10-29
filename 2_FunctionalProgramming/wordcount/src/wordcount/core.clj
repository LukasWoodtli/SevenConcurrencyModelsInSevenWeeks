(ns wordcount.core
  (:gen-class)
  (:require [clojure.core.protocols :refer [CollReduce coll-reduce]]
            [clojure.core.reducers :as r]))


(defn get-words [text] (re-seq #"\w+" text))

(def pages
  ["one potato two potato three potato 
    four five potato six potato seven potato more"])

; (merge-with f & maps) is available in the standard lib
(def merge-counts (partial merge-with +))


(defn count-word-parallel [pages]
  (reduce (partial merge-with +)
          (pmap #(frequencies (get-words %)) pages)))


(defn make-reducer [reducible transformf]
  ; Reification of CollReduce and CollFold protocol.
  ; Reifying a protocol is like using new in Java
  ; to create an anonymous instance of an interface.
  (reify
    CollReduce
    (coll-reduce [_ f1]
      (coll-reduce reducible (transformf f1) (f1)))
    (coll-reduce [_ f1 init]
      (coll-reduce reducible (transformf f1) init))))


(defn my-map [mapf reducible]
  (make-reducer reducible
                (fn [reducef]
                  (fn [acc v]
                    (reducef acc (mapf v))))))

(defn parallel-frequencies [coll]
  (r/fold
   (partial merge-with +)
   (fn [counts x](assoc counts x (inc (get counts x 0))))
   coll))