#!/bin/sh
# Development only: recompile when src/ changes, so DevTools restarts the app.
set -e
mvn -q compile
touch target/.compiled
(
  while true; do
    sleep 1
    if [ -n "$(find src -newer target/.compiled -print -quit)" ]; then
      touch target/.compiled
      mvn -q compile || true
    fi
  done
) &
exec mvn spring-boot:run
