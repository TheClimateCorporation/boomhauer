(ns com.climate.boomhauer.util.date-util
  (:require [clojure.tools.logging :as log]
            [clj-time.format :as cf]
            [clj-time.core :as ct]
            [clojure.string :as string])
  (:import [org.joda.time DateTime Interval Days DateTimeZone]
           [org.joda.time.format DateTimeFormatter]))

(def day-formatter ^DateTimeFormatter (cf/formatter "EEEE MMMM" (DateTimeZone/UTC)))
(def year-month-day ^DateTimeFormatter (cf/formatter "yyyy-MM-dd" (DateTimeZone/UTC)))
(def year-week ^DateTimeFormatter (cf/formatter "yyyy-'W'ww" (DateTimeZone/UTC)))

(def days-of-month ["" "1st" "2nd" "3rd" "4th" "5th" "6th" "7th" "8th" "9th" "10th" "11th" "12th" "13th" "14th" "15th"
                    "16th" "17th" "18th" "19th" "20th" "21st" "22nd" "23rd" "24th" "25th" "26th" "27th" "28th" "29th"
                    "30th" "31st"])

(defn parse-date
  [date-string]
  (if (not (string/blank? date-string))
    (if (.contains date-string "W")
      (let [start-date (cf/parse year-week date-string)
            end-date (ct/plus start-date (ct/weeks 1))]
        {:start-date start-date
         :end-date   end-date})
      (let [date (cf/parse year-month-day date-string)]
        {:start-date date
         :end-date   date}))
    (let [date (.withTime (.withZone (DateTime/now) (DateTimeZone/UTC)) 0 0 0 0)]
      {:start-date date
       :end-date   date})))

(defn format-date-time
  [^DateTime date-time ^DateTime now]
  (let [day-of-week-and-month (.print day-formatter date-time)
        day-of-month (nth days-of-month (.getDayOfMonth date-time))
        date-speech (str "on " day-of-week-and-month " " day-of-month)
        year (.getYear date-time)]
    (if (= (.getYear now) year)
      date-speech
      (str date-speech ", " year))))

(defn date-time->speech
  [^DateTime date-time]
  (let [now (.withZone (DateTime/now) (DateTimeZone/UTC))
        yesterday (Interval. (.withTimeAtStartOfDay (.minusDays now 1)) Days/ONE)
        today (Interval. (.withTimeAtStartOfDay now) Days/ONE)
        tomorrow (Interval. (.withTimeAtStartOfDay (.plusDays now 1)) Days/ONE)
        is-yesterday? (.contains yesterday date-time)
        is-today? (.contains today date-time)
        is-tomorrow? (.contains tomorrow date-time)]
    (log/debugf "date [%s], is yesterday [%s], is today [%s], is tomorrow [%s]" date-time is-yesterday? is-today?
      is-tomorrow?)

    (cond
      (nil? date-time) nil
      is-yesterday? "yesterday"
      is-today? "today"
      is-tomorrow? "tomorrow"
      :else (format-date-time date-time now))))