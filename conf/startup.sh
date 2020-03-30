#!/bin/bash

nohup java -Dserver.port=8787 -Dcsp.sentinel.dashboard.server=localhost:8787 -Dproject.name=sentinel-dashboard -jar sentinel-dashboard-1.7.1.jar &