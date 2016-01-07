(ns com.climate.boomhauer.error
  "error related functions")

(defrecord CodedError [code
                       message])

(def ^:const error-codes {:ECHO-001 (CodedError. "ECHO-001" "????")})

(defn create-coded-error
  [error-code & [extra-properties]]
  (merge extra-properties
         (get error-codes error-code)))

(defn throw-coded-error
  [error-code & [extra-properties cause]]
  (let [coded-error (create-coded-error
                      error-code
                      extra-properties)]
    (throw (ex-info (:message coded-error)
                    {:errors [coded-error]} cause))))