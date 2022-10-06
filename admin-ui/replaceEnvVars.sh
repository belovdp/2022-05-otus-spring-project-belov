#!/bin/sh

envsubst < /usr/share/nginx/html/js/env.tpl.js > /usr/share/nginx/html/js/env.js && nginx -g 'daemon off;' || cat /usr/share/nginx/html/js/env.js