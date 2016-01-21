(ns com.climate.boomhauer.util.time-util
  (:import [org.joda.time DateTimeFieldType DateTime DateTimeZone]
           [org.joda.time.format DateTimeFormat DateTimeFormatter]))

(def ^:private formatters
  {:hour-minute-halfday-formatter (DateTimeFormat/forPattern "h m a")
   :hour-halfday-formatter        (DateTimeFormat/forPattern "h a")
   :hour-minute-formatter         (DateTimeFormat/forPattern "h m")
   :hour-formatter                (DateTimeFormat/forPattern "h")})

(defn date-time->halfday
  [^DateTime date-time]
  (.get date-time (DateTimeFieldType/halfdayOfDay)))

(defn time->speech
  [^DateTime date-time include-halfday? ^DateTimeZone zone]
  (let [has-minutes? (> (.getMinuteOfHour date-time) 0)
        formatter-key (if include-halfday?
                        (if has-minutes?
                          :hour-minute-halfday-formatter
                          :hour-halfday-formatter)
                        (if has-minutes?
                          :hour-minute-formatter
                          :hour-formatter))
        ^DateTimeFormatter formatter (get formatters formatter-key)
        formatter (.withZone formatter zone)]
    (.print formatter date-time)))

; todo fix issue where start is before day or end is after day
(defn time-span->speech
  [event-start event-end zone]
  (let [include-halfday-on-start-of-span? (not (= (date-time->halfday event-start) (date-time->halfday event-end)))
        event-start-speech (time->speech event-start include-halfday-on-start-of-span? zone)
        event-end-speech (time->speech event-end true zone)]
    (str "from " event-start-speech " to " event-end-speech)))

(defn past?
  [date-time]
  (< (.compareTo date-time (.minusDays (DateTime/now) 1)) 0))