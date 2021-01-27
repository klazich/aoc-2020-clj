(ns aoc-2020-clj.days.day05.core
  (:require [clojure.java.io :as io]
            [clojure.math.numeric-tower :as math :refer [expt]]
            [clojure.pprint :refer [pprint]]))

(def input (io/resource "aoc_2020_clj/day05/input.txt"))

(defn read-input
  ([] (read-input input))
  ([input-file]
   (with-open [rdr (io/reader input-file)]
     (->> rdr
          line-seq
          vec))))

(defn binary-2-add [s tr]
  (get
   (reduce
    (fn [[acc v] l]
      [(if (= l tr) (+ acc v) acc) (- v (/ v 2))])
    [0 (->> s count dec (expt 2))]
    (vec s))
   0))

(defn parse-row [s] (binary-2-add s \B))
(defn parse-col [s] (binary-2-add s \R))

(defn row [pass] (parse-row (subs pass 0 7)))
(defn col [pass] (parse-col (subs pass 6)))

(defn seat-id [pass] (+ (* (row pass) 8) (col pass)))

(defn solve-part-1
  "951"
  ([] (solve-part-1 (read-input)))
  ([passes]
   (reduce
    (fn [largest pass]
      (let [id (seat-id pass)]
        (if (> id largest) id largest)))
    0
    passes)))

(defn missing-passes
  ([] (missing-passes (read-input)))
  ([passes]
   (reduce
    (fn [m pass]
      (let [r (row pass) c (col pass)]
        (assoc m r (disj (get m r) c))))
    (reduce
     #(assoc %1 %2 (set (range 8)))
     {}
     (range 128))
    passes)))

(reduce-kv
 (fn [s k v]
   (if (not-empty v) (conj s [k v]) s))
 #{}
 (missing-passes))