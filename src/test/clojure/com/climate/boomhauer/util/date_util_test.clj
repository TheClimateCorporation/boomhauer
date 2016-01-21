(ns com.climate.boomhauer.util.date-util-test
  (:require [com.climate.boomhauer.util.date-util :as du])
  (:use [clojure.test])
  (:import [org.joda.time DateTime DateTimeZone]
           [org.joda.time.format DateTimeFormat DateTimeFormatter]))

(deftest parse-date
  (testing "nil date string"
    (let [today (.withTime (.withZone (DateTime/now) (DateTimeZone/UTC)) 0 0 0 0)]
      (is (= {:start-date today :end-date today} (du/parse-date nil)))))
  (testing "blank date string"
    (let [today (.withTime (.withZone (DateTime/now) (DateTimeZone/UTC)) 0 0 0 0)]
      (is (= {:start-date today :end-date today} (du/parse-date "")))))
  (testing "valid date string"
    (let [date-time (.withDate (.withTime (.withZone (DateTime.) (DateTimeZone/UTC)) 0 0 0 0) 2013 10 13)]
      (is (= {:start-date date-time :end-date date-time} (du/parse-date "2013-10-13")))))
  (testing "valid date string with week span"
    (let [start-date-time (.withDate (.withTime (.withZone (DateTime.) (DateTimeZone/UTC)) 0 0 0 0) 2015 11 23)
          end-date-time (.withDate (.withTime (.withZone (DateTime.) (DateTimeZone/UTC)) 0 0 0 0) 2015 11 30)]
      (is (= {:start-date start-date-time :end-date end-date-time} (du/parse-date "2015-W48"))))))

(deftest date-time->speech
  (testing "nil date input returns nil"
    (is (nil? (du/date-time->speech nil))))
  (testing "yesterday's date returns 'yesterday'"
    (let [yesterday (.minusDays (DateTime/now) 1)]
      (is (= "yesterday" (du/date-time->speech yesterday)))))
  (testing "today's date returns 'today'"
    (let [today (DateTime/now)]
      (is (= "today" (du/date-time->speech today)))))
  (testing "tomorrow's date returns 'tomorrow'"
    (let [tomorrow (.plusDays (DateTime/now) 1)]
      (is (= "tomorrow" (du/date-time->speech tomorrow)))))
  (testing "other date in current year returns 'on day-of-week month day-of-month'"
    (let [now (DateTime/now)
          ; jan 1st is convenient but avoid issues in jan or dec by using apr
          [month month-name] (if (#{1 12} (.getMonthOfYear now))
                               [4 "April"]
                               [1 "January"])
          other-day-in-current-year (.withDate (DateTime.) (.getYear now) month 1)
          day-of-week (.print ^DateTimeFormatter (DateTimeFormat/forPattern "EEEE") other-day-in-current-year)
          expected-speech (str "on " day-of-week " " month-name " 1st")]
      (is (= expected-speech (du/date-time->speech other-day-in-current-year)))))
  (testing "other date in different year returns 'on day-of-week month day-of-month year'"
    (let [other-day-in-different-year (.withDate (DateTime.) 1980 4 30)
          expected-speech "on Wednesday April 30th, 1980"]
      (is (= expected-speech (du/date-time->speech other-day-in-different-year))))))