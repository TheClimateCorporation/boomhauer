(ns com.climate.boomhauer.speechlet-request-handler-test
  (:use [clojure.test])
  (:import [com.climate.boomhauer BoomhauerSpeechletRequestHandler]))

(deftest implementation
  (testing "does it construct properly"
    (is (not (nil? (BoomhauerSpeechletRequestHandler.))))))
