#!/usr/bin/env bash

set -euo pipefail

# activate sdk-man
set +u
source "$HOME/.sdkman/bin/sdkman-init.sh"
set -u

# CPU limit
LIMIT=2

# GraalVM 22
sdk use java 23-graal

SERVER_PORT=9001 cpulimit -l "$LIMIT" ./artifacts/books-app &
NATIVE_PID=$!

sleep 1

SERVER_PORT=9002 cpulimit -l "$LIMIT" ./artifacts/books-optimized &
NATIVE_PID=$!

sleep 1

SERVER_PORT=9000 cpulimit -l "$LIMIT" java -jar artifacts/books-app.jar
JAVA_PID=$!

trap 'pkill -P $$; exit' SIGINT SIGTERM EXIT
