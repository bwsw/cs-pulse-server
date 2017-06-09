#!/usr/bin/env bash

sed -i 's/$inactive/'"${NGINX_CACHE_TIME:-15s}"'/g' /etc/nginx/nginx.conf

/usr/sbin/nginx ; java -jar /opt/bin/jetty.jar --path /pulse /var/lib/jetty/webapps/ROOT.war