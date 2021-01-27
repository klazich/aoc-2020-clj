(ns aoc-2020-clj.days.day06.core
  (:require [clojure.java.io :as io]
            [clojure.string :refer [split]]
            [clojure.set :refer [union intersection]]
            [clojure.pprint :refer [pprint]]))

(def input (io/resource "aoc_2020_clj/day06/input.txt"))

(defn read-input
  ([] (read-input input))
  ([input-file]
   (with-open [rdr (io/reader input-file)]
     (slurp rdr))))

(defn parse-input
  ([] (parse-input (read-input)))
  ([input]
   (-> input
       (split #"\r\n\r\n")
       (as-> vc (map #(split % #"\r\n") vc)))))

(defn solve-part-1
  "6714"
  ([] (solve-part-1 (parse-input)))
  ([groups]
   (reduce
    #(->> %2
          (map set)
          (apply union)
          count
          (+ %1))
    0
    groups)))

(defn solve-part-2
  "3435"
  ([] (solve-part-2 (parse-input)))
  ([groups]
   (reduce
    #(->> %2
          (map set)
          (apply intersection)
          count
          (+ %1))
    0
    groups)))
