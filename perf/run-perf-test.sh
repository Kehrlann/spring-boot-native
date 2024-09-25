#!/usr/bin/env bash

set -euo pipefail

poetry run locust \
  --locustfile locustfile_parallel.py \
  --processes 4 \
  --users 8 \
  --spawn-rate 8 \
  --headless \
  --timescale \
  --pghost=localhost --pgport=5432 --pgpassword=password --pguser=postgres
