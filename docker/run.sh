#!/usr/bin/env bash

sed -i 's/$inactive/'"${NGINX_CACHE_TIME}"'/g' /etc/nginx/nginx.conf
sed -i 's/$rate_limit/'"${NGINX_RATE_LIMIT}"'/g' /etc/nginx/nginx.conf

/usr/sbin/nginx ; java -jar /opt/bin/jetty.jar --path /pulse /var/lib/jetty/webapps/cs-pulse-server.war