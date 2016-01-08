(ns com.climate.boomhauer.util.date-util-test
  (:require [com.climate.boomhauer.util.date-util :as du])
  (:use [clojure.test])
  (:import [org.joda.time DateTime]
           [org.joda.time.format DateTimeFormat DateTimeFormatter]))

(deftest date->speech
  (testing "nil date input returns nil"
    (is (nil? (du/date->speech nil))))
  (testing "blank date input returns nil"
    (is (nil? (du/date->speech ""))))
  (testing "invalid date input returns nil"
    (is (nil? (du/date->speech "bogus"))))
  (testing "yesterday's date returns 'yesterday'"
    (let [yesterday (.minusDays (DateTime/now) 1)
          yesterday-date-string (.toString yesterday)]
      (is (= "yesterday" (du/date->speech yesterday-date-string)))))
  (testing "today's date returns 'today'"
    (let [today (DateTime/now)
          today-date-string (.toString today)]
      (is (= "today" (du/date->speech today-date-string)))))
  (testing "tomorrow's date returns 'tomorrow'"
    (let [tomorrow (.plusDays (DateTime/now) 1)
          tomorrow-date-string (.toString tomorrow)]
      (is (= "tomorrow" (du/date->speech tomorrow-date-string)))))
  (testing "other date in current year returns 'on day-of-week month day-of-month'"
    (let [now (DateTime/now)
          ; jan 1st is convenient but avoid issues in jan or dec by using apr
          [month month-name] (if (#{1 12} (.getMonthOfYear now))
                               [4 "April"]
                               [1 "January"])
          other-day-in-current-year (.withDate (DateTime.) (.getYear now) month 1)
          other-day-in-current-year-date-string (.toString other-day-in-current-year)
          day-of-week (.print ^DateTimeFormatter (DateTimeFormat/forPattern "EEEE") other-day-in-current-year)
          expected-speech (str "on " day-of-week " " month-name " 1st")]
      (is (= expected-speech (du/date->speech other-day-in-current-year-date-string)))))
  (testing "other date in different year returns 'on day-of-week month day-of-month year'"
    (let [other-day-in-different-year (.withDate (DateTime.) 1980 4 30)
          other-day-in-current-year-date-string (.toString other-day-in-different-year)
          expected-speech "on Wednesday April 30th 1980"]
      (is (= expected-speech (du/date->speech other-day-in-current-year-date-string))))))