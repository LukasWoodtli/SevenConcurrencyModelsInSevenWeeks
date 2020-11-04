(ns clojure-way.server
  (:gen-class)
   (:require [compojure.core         :refer :all]
            [compojure.handler      :refer [site]]
            [ring.util.response     :refer [response status]]
            [ring.adapter.jetty     :refer [run-jetty]]
            [cheshire.core          :as json]))



(def players (atom ()))

(defn list-players []
  (response (json/encode @players)))

(defn create-player [player-name]
  (swap! players conj player-name)
  (status (response "") 201))

(defroutes app-routes
  (GET "/players" [] (list-players))
  (PUT "/players/:player-name"
    [player-name]
    (create-player player-name)))

(defn -main [& _]
  (run-jetty (site app-routes) {:port 3000}))
