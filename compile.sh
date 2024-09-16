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

# GraalVM 22
sdk use java 23.ea.24-graal

./gradlew clean bootJar
cp books/build/libs/books-0.0.1-SNAPSHOT.jar artifacts

./gradlew clean nativeCompile
cp books/build/native/nativeCompile/books artifacts/native-graal-ee

# GraalVM 22 Community edition
sdk use java 22-graalce
./gradlew clean nativeCompile
cp books/build/native/nativeCompile/books artifacts/native-graal-ce
