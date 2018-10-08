# boot-io
[![Clojars Project](https://img.shields.io/clojars/v/degree9/boot-io.svg)](https://clojars.org/degree9/boot-io)<!--- [![Dependencies Status](https://versions.deps.co/degree9/boot-io/status.svg)](https://versions.deps.co/degree9/boot-io)--> [![Downloads](https://versions.deps.co/degree9/boot-io/downloads.svg)](https://versions.deps.co/degree9/boot-io)
<!--[![CircleCI](https://circleci.com/gh/degree9/boot-io.svg?style=svg)](https://circleci.com/gh/degree9/boot-io)
[![gitcheese.com](https://api.gitcheese.com/v1/projects/95880215-d9f4-4604-9e9e-565efdbef0f4/badges?type=1&size=xs)](https://www.gitcheese.com/app/#/projects/95880215-d9f4-4604-9e9e-565efdbef0f4/pledges/create) --->

Boot-clj IO functionality using Apache Commons IO.

---

<p align="center">
  <a href="https://degree9.io" align="center">
    <img width="135" src="http://degree9.io/images/degree9.png">
  </a>
  <br>
  <b>boot-io is developed and maintained by Degree9</b>
</p>

---

IO Tasks for [boot-clj][1].

* Provides `add-file` task for adding files to the fileset.
* Provides `add-directory` task for adding directories to the fileset.
* Provides `add-fileset` task for adding files/directories to the fileset.

> The following outlines basic usage of the task, extensive testing has not been done.
> Please submit issues and pull requests!

## Usage ##

Add `boot-io` to your `build.boot` dependencies and `require` the namespace:

```clj
(set-env! :dependencies '[[degree9/boot-io "X.Y.Z" :scope "test"]])
(require '[degree9.boot-io :refer :all])
```

Add a file to the fileset:

```bash
boot add-file -s "license" -d "license"
```

Add a file to the fileset:

```bash
boot add-directory -s "test" -d "test"
```

Add a file or directory to the fileset:

```bash
;; add folder
boot add-fileset -s "test" -d "target"
;; add file
boot add-fileset -s "license" -d "target"
```

## Task Options ##

All tasks exposes options for specifying where to look for an files/directories and where to put them on the fileset.

```clojure
s source      VAL str "File/Folder used as source."
d destination VAL str "Target file/folder within the fileset."
```

[1]: https://github.com/boot-clj/boot
