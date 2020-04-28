(ns degree9.boot-io
  (:require [boot.core :as boot]
            [boot.util :as util]
            [clojure.java.io :as io]
            [degree9.boot-io.filesystem :as fs])
  (:import (java.io FileNotFoundException)))

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
      (try (fs/copy-dir source target)
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
      (try (fs/copy-file source target)
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
      (try (fs/to-dir source target)
        (catch FileNotFoundException npe (when-not optional npe)))
      (-> fileset (boot/add-resource tmp) boot/commit!))))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
