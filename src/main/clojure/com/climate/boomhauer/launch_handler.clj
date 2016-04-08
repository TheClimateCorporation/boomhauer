(ns com.climate.boomhauer.launch-handler
  (:import [com.amazon.speech.speechlet Session LaunchRequest SpeechletResponse]
           (com.amazon.speech.ui SimpleCard PlainTextOutputSpeech Reprompt)))

(defn handle
  [^LaunchRequest request ^Session session launch-message card-title]
  (let [card (doto (SimpleCard.) (.setTitle card-title) (.setContent launch-message))
        speech (doto (PlainTextOutputSpeech.) (.setText launch-message))
        reprompt (doto (Reprompt.) (.setOutputSpeech speech))]
    (SpeechletResponse/newAskResponse speech reprompt card)))
