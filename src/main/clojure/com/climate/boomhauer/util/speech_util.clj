(ns com.climate.boomhauer.util.speech-util
  (:import [com.amazon.speech.ui PlainTextOutputSpeech Reprompt]))

(defn mk-speech [text]
  (doto (PlainTextOutputSpeech.) (.setText text)))

(defn mk-speech-and-reprompt [text]
  (let [speech (mk-speech text)]
    [speech (doto (Reprompt.) (.setOutputSpeech speech))]))
