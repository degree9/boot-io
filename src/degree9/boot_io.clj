(ns degree9.boot-io
  (:require [boot.core :as boot]
            [boot.util :as util]
            [clojure.java.io :as io])
  (:import (org.apache.commons.io FileUtils)
           (java.io FileNotFoundException)))

;; Helper Functions ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
(defn- exists? [item]
  (.exists item))

(defn- file? [file]
  (.isFile file))

(defn- directory? [dir]
  (.isDirectory dir))

(defn- copy-file [source target]
  (FileUtils/copyFile source target))

(defn- copy-dir [source target]
  (FileUtils/copyDirectory source target))

(defn- to-dir [source target]
  (FileUtils/copyToDirectory source target))

(defn- mk-dir [dir]
  (FileUtils/forceMkdir dir))

(defn- mk-parent [file]
  (FileUtils/forceMkdirParent file))
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; IO Tasks ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
(boot/deftask add-directory
  "Add directory to fileset. Contents of source directory are placed in target."
  [s source      VAL str  "Source directory used during copy."
   d destination VAL str  "Target directory relative to fileset root used during copy."
   o optional        bool "Catch missing source exception."]
  (let [tmp (boot/tmp-dir!)
        src (:source *opts*)
        dst (:destination *opts*)
        source (io/file src)
        target (io/file tmp dst)]
    (boot/with-pre-wrap fileset
      (when-not (and src dst)
        (util/fail "Please provide both `source` and `destination` options. \n"))
      (util/info "Adding directory to fileset... \n")
      (util/info "• %s -> %s \n" src dst)
      (try (copy-dir source target)
        (catch FileNotFoundException npe (when-not optional npe)))
      (-> fileset (boot/add-resource tmp) boot/commit!))))

(boot/deftask add-file
  "Add file to fileset. Contents of source file are placed in target."
  [s source      VAL str  "Source file used during copy."
   d destination VAL str  "Target file relative to fileset root used during copy."
   o optional        bool "Catch missing source exception."]
  (let [tmp      (boot/tmp-dir!)
        src      (:source *opts*)
        dst      (:destination *opts*)
        optional (:optional *opts*)
        source   (io/file src)
        target   (io/file tmp dst)]
    (boot/with-pre-wrap fileset
      (when-not (and src dst)
        (util/fail "Please provide both `source` and `destination` options. \n"))
      (util/info "Adding file to fileset... \n")
      (util/info "• %s -> %s \n" src dst)
      (try (copy-file source target)
        (catch FileNotFoundException npe (when-not optional npe)))
      (-> fileset (boot/add-resource tmp) boot/commit!))))

(boot/deftask add-fileset
  "Add source to fileset. Supports both files and directories."
  [s source      VAL str  "Source file/directory to add to fileset."
   d destination VAL str  "Target directory relative to fileset root."
   o optional        bool "Catch missing source exception."]
  (let [tmp (boot/tmp-dir!)
        src (:source *opts*)
        dst (:destination *opts*)
        source (io/file src)
        target (io/file tmp dst)]
    (boot/with-pre-wrap fileset
      (when-not (and src dst)
        (util/fail "Please provide both `source` and `destination` options. \n"))
      (util/info "Adding items to fileset... \n")
      (util/info "• %s -> %s \n" src dst)
      (try (to-dir source target)
        (catch FileNotFoundException npe (when-not optional npe)))
      (-> fileset (boot/add-resource tmp) boot/commit!))))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
