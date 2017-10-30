(ns degree9.boot-io
  (:require [boot.core :as boot]
            [boot.util :as util]
            [clojure.java.io :as io])
  (:import (org.apache.commons.io FileUtils)))

;; Helper Functions ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
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
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; IO Tasks ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
(boot/deftask add-directory
  "Add directory to fileset. Contents of source directory are placed in target."
  [s source      VAL str "Source directory used during copy."
   d destination VAL str "Target directory relative to fileset root used during copy."]
  (let [tmp (boot/tmp-dir!)
        src (:source *opts*)
        dst (:destination *opts*)
        source (io/file src)
        target (io/file tmp dst)]
    (boot/with-pre-wrap fileset
      (when-not (and src dst)
        (util/fail "Please provide both `source` and `destination` options. \n"))
      (when-not (and (directory? source) (directory? target))
        (util/fail "Both source and destination must be directories. \n"))
      (util/info "Adding directory to fileset... \n")
      (util/info "• %s -> %s \n" src dst)
      (copy-dir source target)
      (-> fileset (boot/add-resource tmp) boot/commit!))))

(boot/deftask add-file
  "Add file to fileset. Contents of source file are placed in target."
  [s source      VAL str "Source file used during copy."
   d destination VAL str "Target file relative to fileset root used during copy."]
  (let [tmp (boot/tmp-dir!)
        src (:source *opts*)
        dst (:destination *opts*)
        source (io/file src)
        target (io/file tmp dst)]
    (boot/with-pre-wrap fileset
      (when-not (and src dst)
        (util/fail "Please provide both `source` and `destination` options. \n"))
      (when-not (and (file? source) (file? target))
        (util/fail "Both source and destination must be files. \n"))
      (util/info "Adding file to fileset... \n")
      (util/info "• %s -> %s \n" src dst)
      (copy-file source target)
      (-> fileset (boot/add-resource tmp) boot/commit!))))

(boot/deftask add-fileset
  "Add source to fileset. Supports both files and directories."
  [s source      VAL str "Source file/directory to add to fileset."
   d destination VAL str "Target directory relative to fileset root."]
  (let [tmp (boot/tmp-dir!)
        src (:source *opts*)
        dst (:destination *opts*)
        source (io/file src)
        target (io/file tmp dst)]
    (boot/with-pre-wrap fileset
      (when-not (and src dst)
        (util/fail "Please provide both `source` and `destination` options. \n"))
      (when (file? target)
        (util/fail "Destination must be a directory. \n"))
      (util/info "Adding items to fileset... \n")
      (util/info "• %s -> %s \n" src dst)
      (to-dir source target)
      (-> fileset (boot/add-resource tmp) boot/commit!))))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
