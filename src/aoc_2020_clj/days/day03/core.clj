(ns aoc-2020-clj.days.day03.core
  (:require [clojure.java.io :as io]
            [clojure.pprint :refer [pprint]]))

(def input (io/resource "aoc_2020_clj/day03/input.txt"))

(defn read-input
  ([] (read-input input))
  ([input-file]
   (with-open [rdr (io/reader input-file)]
     (->> rdr
          line-seq
          vec))))

(defn solve
  ([right down] (solve right down (read-input)))
  ([right down input]
   (let [area (take-nth down input)
         width (count (first area))
         columns (iterate #(mod (+ right %1) width) 0)
         squares (map nth area columns)]
     (reduce
      (fn [trees square]
        (if (= square \#) (inc trees) trees))
      0
      squares))))

(defn solve-part-1
  "145"
  []
  (solve 3 1))

(defn solve-part-2
  "3424528800"
  []
  (* (solve 1 1)
     (solve 3 1)
     (solve 5 1)
     (solve 7 1)
     (solve 1 2)))
