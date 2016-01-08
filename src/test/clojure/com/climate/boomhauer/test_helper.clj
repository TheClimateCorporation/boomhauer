(ns com.climate.boomhauer.test-helper
  (:import [com.amazon.speech.speechlet IntentRequest SessionStartedRequest SessionStartedRequest$Builder
                                        IntentRequest$Builder Session$Builder Session SessionEndedRequest
                                        SessionEndedRequest$Builder LaunchRequest LaunchRequest$Builder]
           [com.amazon.speech.slu Intent Slot Slot$Builder Intent$Builder]))

; stub functions
(defn build-slot
  [slot-name slot-value]
  (let [builder ^Slot$Builder (Slot/builder)]
    (.withName builder slot-name)
    (.withValue builder slot-value)
    (.build builder)))

(defn build-intent
  [intent-name]
  (let [builder ^Intent$Builder (Intent/builder)]
    (.withName builder intent-name)
    (.build builder)))

(defn build-intent-request
  [intent-name]
  (let [intent (build-intent intent-name)
        builder ^IntentRequest$Builder (IntentRequest/builder)]
    (.withRequestId builder "request id")
    (.withIntent builder intent)
    (.build builder)))

(defn build-session-started-request
  [request-id]
  (let [builder ^SessionStartedRequest$Builder (SessionStartedRequest/builder)]
    (.withRequestId builder request-id)
    (.build builder)))

(defn build-session-ended-request
  [request-id]
  (let [builder ^SessionEndedRequest$Builder (SessionEndedRequest/builder)]
    (.withRequestId builder request-id)
    (.build builder)))

(defn build-session
  []
  (let [builder ^Session$Builder (Session/builder)]
    (.withSessionId builder "session id")
    (.build builder)))

(defn build-launch-request
  []
  (let [builder ^LaunchRequest$Builder (LaunchRequest/builder)]
    (.withRequestId builder "request id")
    (.build builder)))