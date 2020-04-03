#!/bin/bash
#chkconfig: 2345 20 80
#description:start_sentinel.sh
cd /sentinel
nohup java -Dserver.port=8787 -Dcsp.sentinel.dashboard.server=localhost:8787 -Dproject.name=sentinel-dashboard -jar /sentinel/sentinel-dashboard-1.7.1.jar &
