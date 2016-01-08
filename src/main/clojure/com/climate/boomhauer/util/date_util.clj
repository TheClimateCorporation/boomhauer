(ns com.climate.boomhauer.util.date-util
  (:require [clojure.tools.logging :as log]
            [clj-time.format :as format])
  (:import [com.climate.boomhauer AlexaDateUtil]
           [org.joda.time DateTime Interval Days]))

(defn date->speech
  [date-string]
  (when-let [date-time (format/parse date-string)]
    (let [date (.toDate date-time)
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
        :else (str "on " (AlexaDateUtil/getFormattedDate date true))))))