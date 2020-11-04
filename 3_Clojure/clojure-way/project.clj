(defproject clojure-way "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.1"]
                   [compojure "1.6.2"]
                   [javax.servlet/servlet-api "2.5"]
                   [ring "1.8.2"]
                   [cheshire "5.10.0"]
                   [schejulure "1.0.1"]
                   [org.flatland/useful "0.11.6"]
                   ;[clj-http "3.10.3"]
                 ]
  :main ^:skip-aot clojure-way.server
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}})
