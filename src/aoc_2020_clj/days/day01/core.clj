(ns aoc-2020-clj.days.day01.core
  (:require [clojure.java.io :as io]))

(def input (io/resource "aoc_2020_clj/day01/input.txt"))

(defn read-input
  ([] (read-input input))
  ([input-file]
   (with-open [rdr (io/reader input-file)]
     (->> rdr
          line-seq
          (map #(Integer/parseInt %))
          set))))

(defn solve-part-1
  "381699"
  ([] (solve-part-1 (read-input)))
  ([numbers]
   (for [a numbers
         :let [b (- 2020 a)]
         :when (< a b)
         :when (contains? numbers b)]
     (* a b))))

(defn solve-part-2
  "111605670"
  ([] (solve-part-2 (read-input)))
  ([numbers]
   (for [a numbers
         b numbers
         :let [c (- 2020 a b)]
         :when (< a b c)
         :when (contains? numbers c)]
     (* a b c))))
