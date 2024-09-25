#!/usr/bin/env bash

set -euo pipefail

# activate sdk-man
set +u
source "$HOME/.sdkman/bin/sdkman-init.sh"
set -u

# GraalVM 22
sdk use java 23-graal

SERVER_PORT=8080 cpulimit -l 1 java -jar artifacts/books-app.jar &
JAVA_PID=$!

SERVER_PORT=8081 cpulimit -l 1 ./artifacts/books-app
NATIVE_PID=$!

trap 'pkill -P $$; exit' SIGINT SIGTERM EXIT
