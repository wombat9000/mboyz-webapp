#!/usr/bin/env bash
set -e

changed=$(git status --porcelain)

./bin/commit.sh

git pull --rebase

./gradlew check

git push