#!/usr/bin/env bash

set -euo pipefail

curl -sL http://localhost:9000
curl -s http://localhost:9001

poetry run locust \
  --locustfile locustfile_parallel.py \
  --processes 4 \
  --users 8 \
  --spawn-rate 8 \
  --headless \
  --timescale \
  --pghost=localhost --pgport=5432 --pgpassword=password --pguser=postgres
