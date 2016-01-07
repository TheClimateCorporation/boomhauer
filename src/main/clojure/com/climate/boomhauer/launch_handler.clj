(ns com.climate.boomhauer.launch-handler
  (:import [com.amazon.speech.speechlet Session LaunchRequest SpeechletResponse]
           (com.amazon.speech.ui SimpleCard PlainTextOutputSpeech Reprompt)))

(defn handle
  [^LaunchRequest request ^Session session]
  (let [speech-text "??????"
        card (doto (SimpleCard.) (.setTitle "????") (.setContent speech-text))
        speech (doto (PlainTextOutputSpeech.) (.setText speech-text))
        reprompt (doto (Reprompt.) (.setOutputSpeech speech))]
    (SpeechletResponse/newAskResponse speech reprompt card)))