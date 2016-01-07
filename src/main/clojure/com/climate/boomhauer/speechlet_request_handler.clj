(ns com.climate.boomhauer.speechlet-request-handler
  (:gen-class
    :name com.climate.boomhauer.BoomhauerSpeechletRequestHandler
    :extends com.amazon.speech.speechlet.lambda.SpeechletRequestStreamHandler
    :init "init"
    :constructors {[] [com.amazon.speech.speechlet.Speechlet java.util.Set]})
  (:import [com.climate.boomhauer BoomhauerSpeechlet]))

(defn -init []
  [[(BoomhauerSpeechlet.), #{}] nil])

