(ns clojure-way.philosophers-atom
  (:gen-class))

; Instead of representing each philosopher as a `ref`  and using transactions to ensure
; that updates are coordinated, we can use one single `atom` to represent the state of all
; the philosophers

(def philosophers (atom (into [] (repeat 5 :thinking))))

(defn print-philosophers-sync []
  (locking *out*
    (println (deref philosophers))))

(defn claim-chopsticks! [philosopher left right]
  (print-philosophers-sync)
  (swap! philosophers
         (fn [ps]
           (if (and (= (ps left) :thinking)
                    (= (ps right) :thinking))
             (assoc ps philosopher :eating)
             ps)))
  (= (@philosophers philosopher) :eating))



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

