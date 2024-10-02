#!/usr/bin/env bash

set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
pushd "$SCRIPT_DIR"

curl -sL http://localhost:9000
curl -sL http://localhost:9001
curl -sL http://localhost:9002

poetry run locust \
  --locustfile locustfile_parallel.py \
  --processes 8 \
  --users 15 \
  --spawn-rate 15 \
  --headless \
  --timescale \
  --pghost=localhost --pgport=5432 --pgpassword=password --pguser=postgres
