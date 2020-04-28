(set-env!
  :dependencies  '[[boot/core                           "2.8.2"]
                   [commons-io                          "2.6"]
                   [degree9/boot-semver                 "1.8.0" :scope "test"]]
  :resource-paths   #{"src"})

(require '[degree9.boot-semver :refer :all])

(task-options!
  pom    {:project 'degree9/boot-io
          :description "Boot-clj IO functionality using Apache Commons IO."
          :url         "https://github.com/degree9/boot-io"
          :scm         {:url "https://github.com/degree9/boot-io"}}
  target {:dir #{"target"}})

(deftask develop
  "Build boot-io for development."
  []
  (comp
   (version :develop true
            :minor 'inc
            :patch 'zero
            :pre-release 'snapshot)
   (watch)
   (target)
   (build-jar)))

(deftask deploy
  "Build boot-io and deploy to clojars."
  []
  (comp
   (version)
   (target)
   (build-jar)
   (push-release)))
