(ns com.climate.boomhauer.speechlet-test
  (:require [com.climate.boomhauer.test-helper :as helper]
            [com.climate.boomhauer.speechlet :as speechlet]
            [com.climate.boomhauer.launch-handler :as launch-handler]
            [com.climate.boomhauer.intent-handler :as intent-handler])
  (:use [clojure.test])
  (:import [com.amazon.speech.speechlet SpeechletResponse]))

(deftest speechlet
  (let [speechlet-under-test (com.climate.boomhauer.BoomhauerSpeechlet.)]

    (testing "onSessionStarted"
      (let [session-started-request (helper/build-session-started-request "request id")
            session (helper/build-session)]
        (is (nil? (.onSessionStarted speechlet-under-test session-started-request session)))))
    (testing "onSessionEnded"
      (let [session-ended-request (helper/build-session-ended-request "request id")
            session (helper/build-session)]
        (is (nil? (.onSessionEnded speechlet-under-test session-ended-request session)))))
    (testing "onLaunch with no constructor params"
      (let [launch-request (helper/build-launch-request)
            session (helper/build-session)
            stub-response (SpeechletResponse.)
            default-launch-message "No launch message provided"
            default-card-title ""
            stub-handle (fn [input-launch-request input-session launch-message card-title]
                          (is (= launch-request input-launch-request))
                          (is (= session input-session))
                          (is (= default-launch-message launch-message))
                          (is (= default-card-title card-title))
                          stub-response)]
        (with-redefs [launch-handler/handle stub-handle]
          (is (= stub-response (.onLaunch speechlet-under-test launch-request session))))))
    (testing "onLaunch with constructor params"
      (let [launch-request (helper/build-launch-request)
            session (helper/build-session)
            stub-response (SpeechletResponse.)
            expected-launch-message "dang ol' Jeronimoe!!!"
            expected-card-title "Boom"
            stub-handle (fn [input-launch-request input-session launch-message card-title]
                          (is (= launch-request input-launch-request))
                          (is (= session input-session))
                          (is (= expected-launch-message launch-message))
                          (is (= expected-card-title card-title))
                          stub-response)
            speechlet-under-test (com.climate.boomhauer.BoomhauerSpeechlet.
                                     {:launch-message expected-launch-message
                                      :card-title expected-card-title})]
        (with-redefs [launch-handler/handle stub-handle]
          (is (= stub-response (.onLaunch speechlet-under-test launch-request session))))))
    (testing "onIntent"
      (let [intent-request (helper/build-intent-request "intent")
            session (helper/build-session)
            stub-response (SpeechletResponse.)
            stub-handle (fn [input-intent-request input-session]
                          (is (= intent-request input-intent-request))
                          (is (= session input-session))
                          stub-response)]
        (with-redefs [intent-handler/handle stub-handle]
          (is (= stub-response (.onIntent speechlet-under-test intent-request session))))))))
