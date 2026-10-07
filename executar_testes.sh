#!/usr/bin/env sh
set -eu
cd "$(dirname "$0")"
mkdir -p bin
javac -encoding UTF-8 -d bin src/calculadora/*.java
java -cp bin calculadora.TesteCalculadora
