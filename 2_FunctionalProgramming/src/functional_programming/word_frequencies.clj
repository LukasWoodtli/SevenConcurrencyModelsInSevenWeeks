(ns functional-programming.word-frequencies)

(defn word-frequencies [words]
  (reduce
   (fn [counts word] (assoc counts word (inc (get counts word 0))))
   {} words))

; `partial` takes a function and one or more arguments
; and returns partially applied function
(def multiply-by-2 (partial * 2))


(defn get-words [text] (re-seq #"\w+" text))

; `frequencies` is available in the std library
(defn count-words-sequential [pages] (frequencies (mapcat get-words pages)))
