(ns com.climate.boomhauer.speechlet
  (:gen-class
    :name com.climate.boomhauer.BoomhauerSpeechlet
    :implements [com.amazon.speech.speechlet.Speechlet])
  (:import [com.amazon.speech.speechlet Session
                                        SessionEndedRequest
                                        IntentRequest
                                        LaunchRequest
                                        SessionStartedRequest])
  (:require [clojure.tools.logging :as log]
            [com.climate.boomhauer.launch-handler :as launch]
            [com.climate.boomhauer.intent-handler :as intent]))

; void onSessionStarted(SessionStartedRequest request, Session session) throws SpeechletException;
(defn -onSessionStarted
  [_ ^SessionStartedRequest request ^Session session]
  (log/infof "session started, request [%s], session [%s]" (.getRequestId request) (.getSessionId session))
  nil)

; SpeechletResponse onLaunch(LaunchRequest request, Session session) throws SpeechletException;
(defn -onLaunch
  [_ ^LaunchRequest request ^Session session]
  (log/infof "launch, request [%s], session [%s]" (.getRequestId request) (.getSessionId session))
  (launch/handle request session))

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

