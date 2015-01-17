;(frequencies ["one" "potato" "two" "potato" "three" "potato" "four" "potato"])

(defn get-words [text] (re-seq #"\w+" text))
;(get-words "one two three four")

;(mapcat get-words ["one two three four" "five six seven"])


(defn count-words-sequential [pages]
  (frequencies (mapcat get-words pages)))


 (take 10 (range 0 10000000))

 (take-last 5 (range 0 100000000))
