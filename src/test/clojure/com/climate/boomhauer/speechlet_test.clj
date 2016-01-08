(ns com.climate.boomhauer.speechlet-test
  (:require [com.climate.boomhauer.test-helper :as helper]
            [com.climate.boomhauer.speechlet :as speechlet]
            [com.climate.boomhauer.launch-handler :as launch-handler]
            [com.climate.boomhauer.intent-handler :as intent-handler])
  (:use [clojure.test])
  (:import [com.amazon.speech.speechlet SpeechletResponse]))

(deftest speechlet
  (testing "onSessionStarted"
    (let [session-started-request (helper/build-session-started-request "request id")
          session (helper/build-session)]
      (is (nil? (speechlet/-onSessionStarted nil session-started-request session)))))
  (testing "onSessionEnded"
    (let [session-ended-request (helper/build-session-ended-request "request id")
          session (helper/build-session)]
      (is (nil? (speechlet/-onSessionEnded nil session-ended-request session)))))
  (testing "onLaunch"
    (let [launch-request (helper/build-launch-request)
          session (helper/build-session)
          stub-response (SpeechletResponse.)
          stub-handle (fn [input-launch-request input-session]
                        (is (= launch-request input-launch-request))
                        (is (= session input-session))
                        stub-response)]
      (with-redefs [launch-handler/handle stub-handle]
        (is (= stub-response (speechlet/-onLaunch nil launch-request session))))))
  (testing "onIntent"
    (let [intent-request (helper/build-intent-request "intent")
          session (helper/build-session)
          stub-response (SpeechletResponse.)
          stub-handle (fn [input-intent-request input-session]
                        (is (= intent-request input-intent-request))
                        (is (= session input-session))
                        stub-response)]
      (with-redefs [intent-handler/handle stub-handle]
        (is (= stub-response (speechlet/-onIntent nil intent-request session)))))))
