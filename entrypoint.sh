#!/usr/bin/env bash

set -xe

`which java` -cp $(echo target/*.jar | tr ' ' ':') flight_log.visualization.App
