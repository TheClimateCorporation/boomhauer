(ns com.climate.boomhauer.middleware
  (:require [clojure.string :as string]
            [clojure.tools.logging :as log])
  (:import [clojure.lang ExceptionInfo]
           [com.amazon.speech.speechlet SpeechletResponse]
           [com.amazon.speech.ui PlainTextOutputSpeech SimpleCard]))

(defmacro error-decoder-wrapper
  [body]
  `(try
     ~body
     (catch ExceptionInfo e#
       (if-let [errors# (:errors (ex-data e#))]
         (let [err# (-> errors#
                        first)]
           (condp = (:code err#)
             "ECHO-001" nil
             (throw e#)))
         (throw e#)))))
