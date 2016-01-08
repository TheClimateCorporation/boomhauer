(ns com.climate.boomhauer.util.number-util-test
  (:require [com.climate.boomhauer.util.number-util :as nu])
  (:use [clojure.test]))

(deftest number->speech
  (testing "nil number input returns nil"
    (is (nil? (nu/number->speech nil))))
  (testing "blank number input returns nil"
    (is (nil? (nu/number->speech ""))))
  (testing "invalid number input returns nil"
    (is (nil? (nu/number->speech "bogus"))))
  (testing "int returns value as int'"
    (is (= 3 (nu/number->speech 3))))
  (testing "long returns value as long'"
    (is (= 3 (nu/number->speech (long 3)))))
  (testing "double returns value as double'"
    (is (= 3.3 (nu/number->speech 3.3))))
  (testing "double as string with decimals returns value as double'"
    (is (= 3.3 (nu/number->speech "3.3"))))
  (testing "double as string that is a whole number returns value as int'"
    (is (= 3 (nu/number->speech "3.0")))))