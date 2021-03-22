(ns channels.rss-reader
  (:gen-class)
  (:require [channels.core :as async :refer :all]
            [clojure.core.async :refer :all
             :exclude [​map​ ​into​ ​reduce​ ​merge​ ​
                       partition​ ​partition-by​ ​take​]]))

(defn poll-fn [interval action]
  (let [seconds (* interval 1000)]
    (go (while true
          (action)
          (<! (timeout seconds))))))

; this runs endlessly
; (poll-fn 10 #(println "Polling at:" (System/currentTimeMillis)))


; parking calls need to be made directly within a go block—Clojure’s
; macro system is unable to perform its magic otherwise

(def ch (chan))

; backtick (‘): quote operator. It takes source code and, instead of executing it,
; returns a representation of it that can be subsequently compiled.
; Within that code, we can use the ~(unquote) and ~@(unquote splice) operators to
; refer to arguments passed to the macro.
; The #(auto-gensym) suffix indicates that Clojure should automatically generate
; a unique name (which guarantees that it won’t clash with any names used by code passed to the macro) .
(defmacro poll [interval & body]
  `(let [seconds# (* ~interval 1000)]
     (go (while true
           (do ~@body)
           (<! (timeout seconds#))))))

; this runs infinitelly
;(poll 10
;      (println "Polling at:" (System/currentTimeMillis))
;      (println (<! ch)))
