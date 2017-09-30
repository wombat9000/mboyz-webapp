#!/usr/bin/env bash

pkill ssh-agent;
pkill gpg-agent;
echo "$(gpg-agent --daemon --enable-ssh-support --log-file ~/.gnupg/gpg-agent.log)"
