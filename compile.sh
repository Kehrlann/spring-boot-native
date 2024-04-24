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
sdk use java 22-graal

./gradlew bootJar
cp simple/build/libs/simple-0.0.1-SNAPSHOT.jar artifacts

./gradlew clean nativeCompile
cp simple/build/native/nativeCompile/simple artifacts/native-graal-ee

# GraalVM 22 Community edition
sdk use java 22-graalce
./gradlew clean nativeCompile
cp simple/build/native/nativeCompile/simple artifacts/native-graal-ce
