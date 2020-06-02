(ns functional.wordfreq)

(defn word-frequencies [words]
  (reduce
   (fn [counts word] (assoc counts word (inc (get counts word 0))))
   {} words))

(def multiply-by-2 (partial * 2))


(defn get-words [text] (re-seq #"\w+" text))

(defn count-words-sequential [pages] (frequencies (mapcat get-words pages)))
