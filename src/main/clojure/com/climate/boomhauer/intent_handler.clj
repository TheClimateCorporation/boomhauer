(ns com.climate.boomhauer.intent-handler
  (:require [clojure.tools.logging :as log])
  (:import [com.amazon.speech.speechlet Session IntentRequest SpeechletResponse]
           [com.amazon.speech.slu Slot Intent]
           [com.amazon.speech.ui PlainTextOutputSpeech SsmlOutputSpeech]
           [java.util Map]
           [java.lang.reflect Method]))

(defmulti handle-intent
  (fn [intent]
    (-> intent .getName keyword)))

(defmacro defintent
  [intent-sym intent-fn]
  `(defmethod handle-intent ~intent-sym
     [~'_]
     ~intent-fn))

(defn- slot->map
  [^Slot slot]
  [(-> slot .getName keyword) (.getValue slot)])

(defn- slot-and-session-parameters->map
  [^Intent intent ^Session session]
  (let [^Map session-attributes (.getAttributes session)
        session-attributes-map (zipmap (map keyword (.keySet session-attributes)) (.values session-attributes))]
    (into session-attributes-map (map slot->map (-> intent .getSlots vals)))))

(defn- create-intent-not-found-response
  [intent-name exception-message]
  (let [speech-text (str "Intent " intent-name " could not be handled due to error " exception-message)
        speech (doto (PlainTextOutputSpeech.) (.setText speech-text))]
    (SpeechletResponse/newTellResponse speech)))

(defn- create-intent-error-response
  [intent-name exception]
  (let [exception-type (-> exception class .getName)
        exception-message (.getMessage exception)
        speech-text (str "Error handling intent " intent-name " due to error " exception-type " " exception-message)
        speech-ssml (str "????")
        speech (doto (SsmlOutputSpeech.) (.setSsml speech-ssml))]
    (SpeechletResponse/newTellResponse speech)))

(defn- get-function-arity
  [f]
  (-> f class .getDeclaredMethods first ^Method .getParameterTypes alength))

(defn- copy-slot-parameters-to-session
  [intent session]
  (doseq [slot-map-entry (.getSlots intent)]
    (.setAttribute session (-> slot-map-entry val .getName) (-> slot-map-entry val .getValue))))

(defn handle
  [^IntentRequest request ^Session session]
  (let [intent (.getIntent request)
        intent-name (.getName intent)
        intent-parameters (slot-and-session-parameters->map intent session)]
    (log/infof "intent [%s]" intent-name)

    (copy-slot-parameters-to-session intent session)

    (try
      (let [intent-fn (handle-intent intent)
            intent-fn-arity (get-function-arity intent-fn)]
        (log/debugf "intent fn arity [%s]" intent-fn-arity)
        (case intent-fn-arity
          0 (intent-fn)
          1 (intent-fn session)
          (intent-fn session intent-parameters)))
      (catch IllegalArgumentException e
        (log/error e "error finding intent handler fn")
        (create-intent-not-found-response intent-name (.getMessage e)))
      (catch Exception e
        (log/error e "error executing intent handler fn")
        (create-intent-error-response intent-name e)))))