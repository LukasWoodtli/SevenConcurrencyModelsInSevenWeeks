(ns clojure-way.philosophers
  (:gen-class))


(def philosophers (into [] (repeatedly 5 #(ref :thinking))))

(defn print-philosophers-sync []
  (locking *out*
    (println (map deref philosophers))))

(defn claim-chopsticks [philosopher left right]
  (print-philosophers-sync)
  (dosync
   (when (and (= (ensure left) :thinking) (= (ensure right) :thinking))
     (ref-set philosopher :eating))))


(defn release-chopsticks [philosopher]
  (print-philosophers-sync)
  (dosync (ref-set philosopher :thinking)))


(defn think []
  (Thread/sleep (rand 1000)))

(defn eat []
  (Thread/sleep (rand 1000)))

(defn philosopher-thread [n]
  (Thread.
   #(let [philosopher (philosophers n)
          left (philosophers (mod (- n 1) 5))
          right (philosophers (mod (+ n 1) 5))]
      (while true
        (think)
        (when (claim-chopsticks philosopher left right)
          (eat)
          (release-chopsticks philosopher))))))


(defn -main [& args]
  (let [threads (map philosopher-thread (range 5))]
    (doseq [thread threads] (.start thread))
    (doseq [thread threads] (.join thread))))

