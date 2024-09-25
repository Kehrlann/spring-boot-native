#!/usr/bin/env bash

set -euo pipefail

poetry run locust-compose down -v
poetry run locust-compose up
