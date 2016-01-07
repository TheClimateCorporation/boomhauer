(ns com.climate.boomhauer.util.number-util)

(defn number->speech
  [number]
  (if number
    (let [number (Double/parseDouble (str number))]
      (if (= number (double (int number)))
        (int number)
        number))
    nil
    ))