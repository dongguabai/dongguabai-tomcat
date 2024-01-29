#!/bin/bash

if [[ -z "$1" ]]
then
    echo "Please provide a commit ID as an argument."
    exit 1
fi

commit_id="$1"

parent_commit_id=$(git rev-parse "$commit_id^")

GIT_SEQUENCE_EDITOR="sed -i -e '1,1!s/pick/squash/'" git rebase -i "$parent_commit_id"

git rebase --continue