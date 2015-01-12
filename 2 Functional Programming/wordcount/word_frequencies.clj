(defn word-frequencies [words]
;; (reduce  f val coll)  f: anonymous function (fn ...), val: empty map ({}), coll: words
   (reduce (fn [counts word]
   							(println (str "fn" counts word))
         ;; (assoc map key val)
            (assoc counts word
                ;; (get map key not-found)
							(inc (get counts word 0))
            )
          ) {} words
   )
)

(word-frequencies ["one" "potato" "two" "potato" "three" "potato" "four" "potato"])
