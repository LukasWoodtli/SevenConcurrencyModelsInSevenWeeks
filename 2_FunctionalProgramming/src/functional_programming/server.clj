(ns functional-programming.server
  (:gen-class)
  (:require [clojure.edn        :as    edn]
            [compojure.core     :refer :all]
            [compojure.handler  :refer :all]
            [ring.util.response :refer [response]]
            [ring.adapter.jetty :refer [run-jetty]]
            [clojure.string :refer [trim]]
            [clj-http.client    :as client]))

; Run the server with
; `lein run -m functional-programming.server`
; Make sure that the port 3000 is exposed (if running in a container)

; To run everything:
; - start this server
; - start the translator server (included in the accompanying code for the book)
; - then run the TranscriptTest application (also in the code for the book)

(def snippets (repeatedly promise))

(def translator "http://localhost:3001/translate")

(defn translate [text]
  (future
    :body (client/post translator {:body text})))


(defn sentence-split [text]
  (map trim (re-seq #"[^\.!\?:;]+[\.!\?:;]*" text)))

(defn is-sentence? [text]
  (re-matches #"^.*[\.!\?:;]$" text))

(defn sentence-join [x y]
  (if (is-sentence? x) y (str x "" y)))


(defn strings->sentences [strings]
  (filter is-sentence?
          (reductions sentence-join
                      (mapcat sentence-split strings))))


(def translations
  (delay
   (map translate (strings->sentences (map deref snippets)))))


(defn accept-snippet [n text]
  (deliver (nth snippets n) text))

(defn get-translation [n]
  @(nth @translations n))

(future (doseq [snippet (map deref snippets)]
          (println snippet)))


(defroutes app-routes
  (PUT "/snippet/:n" [n :as {:keys [body]}]
    (accept-snippet (edn/read-string n)(slurp body))
    (response "OK"))
  (GET "/translation/:n" [n]
    (response (get-translation (edn/read-string n)))))

(defn -main [& args]
  (run-jetty (site app-routes) {:port 3000}))


