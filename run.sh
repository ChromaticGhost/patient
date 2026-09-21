#!/bin/bash
cd "$(dirname "$0")"
java -cp "bin:lib/mysql-connector-j-9.0.0.jar" Main "$@"
