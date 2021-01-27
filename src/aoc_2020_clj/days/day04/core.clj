(ns aoc-2020-clj.days.day04.core
  (:require [clojure.java.io :as io]
            [clojure.string :refer [split replace]]
            [clojure.pprint :refer [pprint]]))

(def input (io/resource "aoc_2020_clj/day04/input.txt"))

(defn parseInt [v] (Integer/parseInt v))

(defn read-input
  ([] (read-input input))
  ([input-file]
   (with-open [rdr (io/reader input-file)]
     (slurp rdr))))

(defn parse-input
  ([] (parse-input (read-input)))
  ([input]
   (map
    (fn [fields]
      (reduce
       (fn [pass-map pairs]
         (let [[k v] (split pairs #":")] (assoc pass-map (keyword k) v)))
       {}
       (split (replace fields #"\r*\n" " ") #"\s")))
    (split input #"\r\n\r\n"))))

(def required-fields [:byr :iyr :eyr :hgt :hcl :ecl :pid])

(defn required-fields?
  [passport]
  (every? #(contains? passport %) required-fields))

(defn in-range
  ([low-bound high-bound]
   (fn [value] (in-range low-bound high-bound value)))
  ([low-bound high-bound v]
   (let [value (if (instance? String v) (parseInt v) v)]
     (<= low-bound value high-bound))))

(def parseable? (every-pred string? (partial re-matches #"^\d+$")))
(defn digits4? [s] (= 4 (count s)))
(defn digits9? [s] (= 9 (count s)))

(defn byr?
  [{byr :byr}]
  ((every-pred parseable? digits4? (in-range 1920 2002)) byr))
(defn iyr?
  [{iyr :iyr}]
  ((every-pred parseable? digits4? (in-range 2010 2020)) iyr))
(defn eyr?
  [{eyr :eyr}]
  ((every-pred parseable? digits4? (in-range 2020 2030)) eyr))
(defn hgt?
  [{hgt :hgt}]
  (let [[v unit] (rest (re-matches #"^(\d+)((?:cm)|(?:in))$" hgt))]
    (case unit
      "cm" (in-range 150 193 v)
      "in" (in-range 59 76 v)
      false)))
(defn hcl?
  [{hcl :hcl}]
  (boolean (re-matches #"^#[0-9a-f]{6}$" hcl)))
(defn ecl?
  [{ecl :ecl}]
  (contains? #{"amb" "blu" "brn" "gry" "grn" "hzl" "oth"} ecl))
(defn pid?
  [{pid :pid}]
  ((every-pred parseable? digits9?) pid))

(defn valid-fields?
  [p]
  (every? #(% p) [byr? iyr? eyr? hgt? hcl? ecl? pid?]))

(def valid-passport? (every-pred required-fields? valid-fields?))

(defn solve-part-1
  "202"
  ([] (solve-part-1 (parse-input)))
  ([passports]
   (reduce #(if (required-fields? %2) (inc %1) %1) 0 passports)))

(defn solve-part-2
  "137"
  ([] (solve-part-2 (parse-input)))
  ([passports]
   (reduce
    #(if (valid-passport? %2) (inc %1) %1) 0 passports)
  ;;  (->> passports
  ;;       (take 25)
  ;;       (filter required-fields?)
  ;;       (map #(pid? %)))
   ))
