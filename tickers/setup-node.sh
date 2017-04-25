#!/usr/bin/env bash

set -e
set -u

npm install > /dev/null 2>&1

BIN_PATH=`cd ./node_modules/.bin; pwd`

if [[ $PATH != *"$BIN_PATH"* ]]; then
    echo "export PATH=$BIN_PATH:$PATH"
fi
