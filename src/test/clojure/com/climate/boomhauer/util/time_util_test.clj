(ns com.climate.boomhauer.util.time-util-test
  (:use [clojure.test])
  (:require [com.climate.boomhauer.util.time-util :as time-util])
  (:import [org.joda.time DateTime DateTimeZone]))

(deftest date-time->halfday
  (testing "same date time has equal halfday"
    (is (= (time-util/date-time->halfday (DateTime.))
          (time-util/date-time->halfday (DateTime.)))))
  (testing "AM halfday"
    (is (= 0 (time-util/date-time->halfday (.withTime (DateTime.) 0 0 0 0)))))
  (testing "PM halfday"
    (is (= 1 (time-util/date-time->halfday (.withTime (DateTime.) 13 0 0 0))))))

(deftest time->speech
  (testing "UTC time zone, do not include halfday"
    (let [zone (DateTimeZone/UTC)
          date-time (.withTime (.withZone (DateTime.) zone) 2 13 0 0)]
      (is (= "2 13" (time-util/time->speech date-time false zone)))))
  (testing "UTC time zone, include halfday"
    (let [zone (DateTimeZone/UTC)
          date-time (.withTime (.withZone (DateTime.) zone) 2 13 0 0)]
      (is (= "2 13 AM" (time-util/time->speech date-time true zone)))))
  (testing "UTC time zone, do not include halfday, no minutes"
    (let [zone (DateTimeZone/UTC)
          date-time (.withTime (.withZone (DateTime.) zone) 14 0 0 0)]
      (is (= "2" (time-util/time->speech date-time false zone)))))
  (testing "UTC time zone, include halfday, no minutes"
    (let [zone (DateTimeZone/UTC)
          date-time (.withTime (.withZone (DateTime.) zone) 14 00 0 0)]
      (is (= "2 PM" (time-util/time->speech date-time true zone)))))
  (testing "central time zone, include halfday"
    (let [from-zone (DateTimeZone/UTC)
          to-zone (DateTimeZone/forID "-05:00")
          date-time (.withTime (.withZone (DateTime.) from-zone) 7 13 0 0)]
      (is (= "2 13 AM" (time-util/time->speech date-time true to-zone))))))

(deftest time-span->speech
  (testing "UTC time zone, same halfday"
    (let [zone (DateTimeZone/UTC)
          start (.withTime (.withZone (DateTime.) zone) 2 13 0 0)
          end (.withTime (.withZone (DateTime.) zone) 8 13 0 0)]
      (is (= "from 2 13 to 8 13 AM" (time-util/time-span->speech start end zone)))))
  (testing "UTC time zone, different halfday"
    (let [zone (DateTimeZone/UTC)
          start (.withTime (.withZone (DateTime.) zone) 2 13 0 0)
          end (.withTime (.withZone (DateTime.) zone) 14 13 0 0)]
      (is (= "from 2 13 AM to 2 13 PM" (time-util/time-span->speech start end zone))))))