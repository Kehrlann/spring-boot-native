# Perf tests

Recommended: run the perf test on a separate machine. Locust is an absolute resource hog.

## Install

Install [Poetry](https://python-poetry.org/docs/).

```
poetry install
```

## Run

```
poetry run locust --processes X
```

Where X is < your number of machine core.

Navigate to localhost:8089 ; use roughly `X * 2` workers.
