(defproject com.climate/boomhauer "0.1.1"
  :description "Library for writing AWS Lambda functions in Clojure to handle Alexa voice requests"
  :url "https://github.com/TheClimateCorporation/boomhauer"
  :min-lein-version "2.0.0"
  :license {:name "Apache License Version 2.0"
            :url "http://www.apache.org/licenses/LICENSE-2.0"
            :distribution :repo}

  :source-paths ["src/main/clojure"]
  :test-paths ["src/test/clojure" "src/test/resources"]
  :resource-paths ["src/main/resources"]
  :java-source-paths ["src/main/java"]

  :dependencies [[org.clojure/data.json "0.2.6"]
                 [com.amazonaws/aws-lambda-java-core "1.1.0"]
                 [com.amazonaws/aws-lambda-java-events "1.1.0"]
                 [com.amazon.alexa/alexa-skills-kit "1.1.3"]
                 [org.clojure/clojure "1.8.0"]
                 [clj-http "2.0.0"]
                 [cheshire "5.5.0"]
                 [clj-time "0.11.0"]
                 [org.clojure/tools.logging "0.3.1"]
                 [org.slf4j/slf4j-api "1.7.13"]
                 [org.slf4j/slf4j-log4j12 "1.7.13"]
                 [log4j "1.2.17"]
                 [org.apache.commons/commons-lang3 "3.4"]
                 [commons-io/commons-io "2.4"]]

  :aot [com.climate.boomhauer.speechlet
        com.climate.boomhauer.speechlet-request-handler])
