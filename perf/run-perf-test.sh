#!/usr/bin/env bash

set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
pushd "$SCRIPT_DIR"

curl -sL http://localhost:9000
curl -sL http://localhost:9001

poetry run locust \
  --locustfile locustfile_parallel.py \
  --processes 6 \
  --users 16 \
  --spawn-rate 16 \
  --headless \
  --timescale \
  --pghost=localhost --pgport=5432 --pgpassword=password --pguser=postgres
