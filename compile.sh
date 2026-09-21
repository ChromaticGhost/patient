#!/bin/bash
set -e
cd "$(dirname "$0")"
mkdir -p bin
javac -cp "lib/mysql-connector-j-9.0.0.jar:." -d bin src/*.java
echo "Build successful! Classes compiled to bin/"
