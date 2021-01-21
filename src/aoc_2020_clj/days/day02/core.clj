(ns aoc-2020-clj.days.day02.core
  (:require [clojure.java.io :as io]
            [clojure.pprint :refer [pprint]]
            [clojure.string :as s]))

(def input (io/resource "aoc_2020_clj/day02/input.txt"))

(def re #"^(\d+)-(\d+)\s(\w):\s(\w+)$")

(defn parse-line
  [line]
  (let [[min max v password] (rest (re-matches re line))]
    [(Integer/parseInt min) (Integer/parseInt max) v password]))

(defn read-input
  ([] (read-input input))
  ([input-file]
   (with-open [rdr (io/reader input-file)]
     (->> rdr
          line-seq
          (map #(parse-line %))
          set))))

(defn solve-part-1
  "628"
  ([] (solve-part-1 (read-input)))
  ([policy-passwords]
   (reduce
    (fn [acc [least most v password]]
      (let [rx (re-pattern (str "[^" v "]"))
            c (count (s/replace password rx ""))]
        (if (<= least c most) (inc acc) acc)))
    0
    policy-passwords)))

(defn solve-part-2
  "705"
  ([] (solve-part-2 (read-input)))
  ([policy-passwords]
   (reduce
    (fn [acc [a b v password]]
      (let [at-a (= (nth password (dec a)) (first v))
            at-b (= (nth password (dec b)) (first v))]
        (if (and (or at-a at-b) (not (and at-a at-b)))
          (inc acc)
          acc)))
    0
    policy-passwords)))
