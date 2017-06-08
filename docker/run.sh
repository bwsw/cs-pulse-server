#!/usr/bin/env bash

mkdir -p /var/log/rest
touch /var/log/rest/supervisor.log

echo "Running. See logs in /var/log/rest"
exec supervisord -n > /var/log/rest/supervisor.log 2>&1