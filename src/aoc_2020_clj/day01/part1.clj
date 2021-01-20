(ns aoc-2020-clj.day01.part1
  (:require
   [aoc-2020-clj.day01.data :refer [input]]))

(def data
  (->> input
       (format "[%s]")
       read-string))

(defn solve
  [data]
  (let [data-as-set (set data)
        diff (some
              #(when (contains? data-as-set (- 2020 %)) %)
              data)]
    (* diff (- 2020 diff))))

(println (solve data))