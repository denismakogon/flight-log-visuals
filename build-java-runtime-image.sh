#!/usr/bin/env bash

rm -fr target/custom-jlink
jlink --add-modules $(jdeps target/*.jar | grep ".jar -> java." | awk '{print $3}' | tr "\n" ',') --output target/custom-jlink
