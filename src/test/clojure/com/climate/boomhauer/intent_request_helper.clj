(ns com.climate.boomhauer.intent-request-helper
  (:import [com.amazon.speech.speechlet IntentRequest]
           [com.amazon.speech.slu Intent Slot]))

; stub functions
(defn build-slot
  [slot-name slot-value]
  (let [slot-builder (Slot/builder)]
    (.build (doto slot-builder
              (.withName slot-name)
              (.withValue slot-value)))))

(defn build-intent
  [intent-name]
  (let [builder (doto (Intent/builder)
                  (.withName intent-name)
                  )]
    (.build builder)))


(defn build-intent-request
  [intent-name]
  (let [intent (build-intent intent-name)
        builder (doto (IntentRequest/builder)
                  (.withRequestId "request-id")
                  (.withIntent intent))]
    (.build builder)))
