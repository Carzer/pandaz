#!/bin/bash

S_PID=$(ps -ef | grep sentinel-dashboard-1.7.1.jar | grep -v grep | awk '{ print $2 }')
echo kill S_PID
 
kill -9 $S_PID