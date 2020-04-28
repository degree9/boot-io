(ns degree9.boot-io.filesystem
  (:require [clojure.java.io :as io])
  (:import (org.apache.commons.io FileUtils)))

;; Helper Functions ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
(defn exists? [item]
  (.exists item))

(defn file? [file]
  (.isFile file))

(defn directory? [dir]
  (.isDirectory dir))

(defn copy-file [source target]
  (FileUtils/copyFile source target))

(defn copy-dir [source target]
  (FileUtils/copyDirectory source target))

(defn to-dir [source target]
  (FileUtils/copyToDirectory source target))

(defn mk-dir [dir]
  (FileUtils/forceMkdir dir))

(defn mk-parent [file]
  (FileUtils/forceMkdirParent file))
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
