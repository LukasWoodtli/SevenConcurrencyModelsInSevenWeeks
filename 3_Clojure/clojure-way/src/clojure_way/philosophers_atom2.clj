(ns clojure-way.philosophers-atom2
  (:gen-class))



(def philosophers (atom (into [] (repeat 5 :thinking))))

(defn print-philosophers-sync []
  (locking *out*
    (println (deref philosophers))))

; Simpler version that doesn't need to check value
; after swap!
(defn claim-chopsticks! [philosopher left right]
  (print-philosophers-sync)
  (swap-when! philosophers
           #(and (= (%1 left) :thinking)
                 (= (%1 right) :thinking))
             assoc  philosopher :eating))


; Docstring can be displayed in the REPL with `(clojure.repl/doc swap-when!)`
(defn swap-when! 
  "If `(pred current-value-of-atom)` is true, atomically swaps the value
   of the atom to become `(apply f current-value-of-atom args)`.
   `pred` and `f` may be called multiple times and should be free
   of side effects.
   Returns the value that was swapped in if the predicate was true,
   `nil` otherwise."
  [a pred f & args]
  ; The `loop` macro defines a target that `recur` can jump to
  (loop []
    (let [old @a]
      (if (pred old)
        ; `apply`, unpacks its last argument
        (let [new (apply f old args)]
          (if (compare-and-set! a old new)
            new(recur)))
        nil))))


(defn release-chopsticks! [philosopher]
  (print-philosophers-sync)
  (swap! philosophers assoc philosopher :thinking))


(defn think []
  (Thread/sleep (rand 1000)))

(defn eat []
  (Thread/sleep (rand 1000)))

(defn philosopher-thread [philosopher]
  (Thread.
   #(let [left (mod (- philosopher 1) 5)
          right (mod (+ philosopher 1) 5)]
      (while true
        (think)
        (when (claim-chopsticks! philosopher left right)
          (eat)
          (release-chopsticks! philosopher))))))


(defn -main [& args]
  (let [threads (map philosopher-thread (range 5))]
    (doseq [thread threads] (.start thread))
    (doseq [thread threads] (.join thread))))

