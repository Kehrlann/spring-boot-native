#!/usr/bin/env bash

exit 0

poetry run locust --processes 4 --users 16

poetry run locust --processes 4 --users 32 --headless --timescale --pghost=localhost --pgport=5432 --pgpassword=password --pguser=postgres

poetry run locust-compose up
poetry run locust-compose down -v

