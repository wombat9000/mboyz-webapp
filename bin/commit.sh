#!/usr/bin/env bash
set -e

changed=$(git status --porcelain)

if [ "$changed" != "" ]; then

	echo -e "Git Status:"
	git status -s
	echo ""

	echo "All changes will be added to commit after entering message:"
	read -p "message: " -e message

	git add .

	git commit -m "${message}"

fi