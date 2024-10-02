#!/usr/bin/env bash

set -euo pipefail

# clean up directories
if [[ -d "artifacts" ]]; then
  rm -rf artifacts
fi
mkdir artifacts

# activate sdk-man
set +u
source "$HOME/.sdkman/bin/sdkman-init.sh"
set -u

# GraalVM 23
sdk use java 23-graal

./gradlew clean :books:bootJar
cp books/build/libs/books-0.0.1-SNAPSHOT.jar artifacts/books-app.jar

./gradlew clean :books:nativeCompile
cp books/build/native/nativeCompile/books artifacts/books-app

./gradlew -Pbp=optimized clean :books:nativeCompile
cp books/build/native/nativeCompile/books artifacts/books-app
