(ns aoc-2020-clj.day01.part2
  (:require
   [aoc-2020-clj.day01.data :refer [input]]))

(def data
  (->> input
       (format "[%s]")
       read-string))

(defn solve
  [data]
  (loop [[data-i & remaining] data]
    (let [result (let [diff (- 2020 data-i)]
                   (loop [[data-j & iter-remaining] remaining]
                     (if (some #{(- diff data-j)} remaining)
                       (* data-i data-j (- diff data-j))
                       (if (empty? iter-remaining)
                         nil
                         (recur iter-remaining)))))]
      (if-not (nil? result)
        result
        (if (empty? remaining)
          nil
          (recur remaining))))))


(println (solve data)) ;; 111605670
