#!/usr/bin/env bash

PORT=$(docker ps | grep postg | sed -E 's/^.*\:([0-9]+)\-.*/\1/g' | tr -d '\n')
PGPASSWORD=test psql -U test -h localhost -p $PORT  test
