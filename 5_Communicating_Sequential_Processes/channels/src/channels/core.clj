(ns channels.core
  (:gen-class)
  (:require [channels.core :as async :refer :all]
            [clojure.core.async :refer :all
             :exclude [​map​ ​into​ ​reduce​ ​merge​ ​
                       partition​ ​partition-by​ ​take​]]))



(defn readall!! [ch]
  (loop [coll []]
    (if-let [x (<!! ch)]
      (recur (conj coll x))
      coll)))

(defn writeall!! [ch coll]
  (doseq [x coll]
    (>!! ch x))
  (close! ch))


