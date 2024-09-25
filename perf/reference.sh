#!/usr/bin/env bash

exit 0

poetry run locust --processes 4 --users 4 --spawn-rate 4

poetry run locust \
  --locustfile locustfile_parallel.py \
  --processes 4 \
  --users 8 \
  --spawn-rate 8 \
  --headless \
  --timescale \
  --pghost=localhost --pgport=5432 --pgpassword=password --pguser=postgres


poetry run locust-compose up
poetry run locust-compose down -v

