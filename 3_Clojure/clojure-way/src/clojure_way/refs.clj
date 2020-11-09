(ns clojure-way.refs
  (:gen-class))


(def attempts (atom 0))
(def transfers (agent 0))

(defn transfer [from to amount]
  (dosync
   (swap! attempts inc) ; DON'T DO THIS IN PRODUCTION CODE
   (send transfers inc)
   (alter from - amount)
   (alter to + amount)))



;; Testing

(def checking (ref 10000))
(def savings (ref 20000))

(defn stress-thread [from to iterations amount]
  (Thread. #(dotimes [_ iterations](transfer from to amount))))

(defn -main [& args]
  (println "Before: Checking =" @checking "Savings =" @savings)
  (let [t1 (stress-thread checking savings 10000 1)
        t2 (stress-thread savings checking 20000 1)]
    (.start t1)
    (.start t2)
    (.join t1)
    (.join t2))
  (await transfers)
  (println "Attempts: " @attempts)
  (println "Transfers: " @transfers)
  (println "After: Checking =" @checking " Savings =" @savings))
  