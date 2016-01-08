(ns com.climate.boomhauer.util.number-util
  (:require [clojure.string :as string]))

(defn number->speech
  [number]
  (let [number-string (str number)]
    (when (not (string/blank? number-string))
      (try
        (let [number (Double/parseDouble number-string)]
          (if (= number (double (int number)))
            (int number)
            number))
        (catch IllegalArgumentException _ nil)))))
