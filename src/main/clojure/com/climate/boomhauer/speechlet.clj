(ns com.climate.boomhauer.speechlet
  (:gen-class
    :name com.climate.boomhauer.BoomhauerSpeechlet
    :implements [com.amazon.speech.speechlet.Speechlet]
    :state launchParams
    :init init
    :constructors {[] []
                   [java.util.Map] []})
  (:import [com.amazon.speech.speechlet Session
                                        SessionEndedRequest
                                        IntentRequest
                                        LaunchRequest
                                        SessionStartedRequest])
  (:require [clojure.tools.logging :as log]
            [com.climate.boomhauer.launch-handler :as launch]
            [com.climate.boomhauer.intent-handler :as intent]))

(defn- mk-default-constructor-params []
  {:launch-message "No launch message provided" :card-title ""})

(defn -init
  ([]
   [[] (mk-default-constructor-params)])
  ([^java.util.Map launch-params]
   [[] (merge (mk-default-constructor-params) launch-params)]))

; void onSessionStarted(SessionStartedRequest request, Session session) throws SpeechletException;
(defn -onSessionStarted
  [_ ^SessionStartedRequest request ^Session session]
  (log/infof "session started, request [%s], session [%s]" (.getRequestId request) (.getSessionId session))
  nil)

; SpeechletResponse onLaunch(LaunchRequest request, Session session) throws SpeechletException;
(defn -onLaunch
  [this ^LaunchRequest request ^Session session]
  (log/infof "launch, request [%s], session [%s]" (.getRequestId request) (.getSessionId session))
  (let [{:keys [launch-message card-title]} (.launchParams this)]
    (launch/handle request session launch-message card-title)))

; SpeechletResponse onIntent(IntentRequest request, Session session) throws SpeechletException;
(defn -onIntent
  [_ ^IntentRequest request ^Session session]
  (log/infof "intent, request [%s], session [%s]" (.getRequestId request) (.getSessionId session))
  (intent/handle request session))

; void onSessionEnded(SessionEndedRequest request, Session session) throws SpeechletException;
(defn -onSessionEnded
  [_ ^SessionEndedRequest request ^Session session]
  (log/infof "session ended, request [%s], session [%s]" (.getRequestId request) (.getSessionId session))
  nil)

