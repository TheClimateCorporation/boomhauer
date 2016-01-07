(ns com.climate.boomhauer.util.date-util
  (:require [clojure.tools.logging :as log])
  (:import [com.climate.boomhauer AlexaDateUtil]
           [org.joda.time DateTime Interval Days]
           [org.joda.time.format ISODateTimeFormat DateTimeFormatter]))

(def ^DateTimeFormatter date-parser (ISODateTimeFormat/dateTimeParser))

(defn ^DateTime parse-date
  [date-string]
  (.parseDateTime date-parser date-string))

(defn date->speech
  [date-string]
  (let [date (-> date-string parse-date .toDate)
        date-time (DateTime. date)
        yesterday (Interval. (.withTimeAtStartOfDay (.minusDays (DateTime/now) 1)) Days/ONE)
        today (Interval. (.withTimeAtStartOfDay (DateTime/now)) Days/ONE)
        tomorrow (Interval. (.withTimeAtStartOfDay (.plusDays (DateTime/now) 1)) Days/ONE)
        is-yesterday? (.contains yesterday date-time)
        is-today? (.contains today date-time)
        is-tomorrow? (.contains tomorrow date-time)]
    (log/debugf "date [%s], is yesterday [%s], is today [%s], is tomorrow [%s]" date is-yesterday? is-today?
                is-tomorrow?)

    (cond
      is-yesterday? "yesterday"
      is-today? "today"
      is-tomorrow? "tomorrow"
      :else (str "on " (AlexaDateUtil/getFormattedDate date true)))))