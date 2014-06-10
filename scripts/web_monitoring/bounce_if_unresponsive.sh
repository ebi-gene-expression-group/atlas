#!/bin/bash
# @author: rpetry                                                                                
# @date:   12 Sep 2013   
# This script bounces $HOSTNAME if response time exceeds allowed maximum                                                                                                                                                                                      

if [ $# -lt 2 ]; then
        echo "Usage: $0 HOSTNAME TOMCAT_DIR MAXTIME (NAGIOS_URL)"
        exit;
fi

HOSTNAME=$1
TOMCAT_DIR=$2
MAXTIME=$3
ATLAS3_NAGIOS_URL=$4
if [ -z "$ATLAS3_NAGIOS_URL" ]; then 
    ATLAS3_NAGIOS_URL="http://${HOSTNAME}:8080/gxa/experiments/E-GEOD-22351?geneQuery=Gpnmb"
fi

log="/tmp/${HOSTNAME}_bounces.log"

# Nagios query
response=`curl -o /dev/null -X GET -s --max-time $MAXTIME -w %{http_code}:%{time_total} "$ATLAS3_NAGIOS_URL" | awk -F"." '{print $1}'`
httpCode=`echo $response | awk -F":" '{print $1}'`
responseTime=`echo $response | awk -F":" '{print $2}'`
if [ "$responseTime" -ge "$MAXTIME" -o "$httpCode" -ne "200" ]; then
    touch $log
    echo `eval date "+%Y-%m-%d\ %k:%M:%S"`" - Response time for $HOSTNAME exceeded $MAXTIME or httpCode: $httpCode <> 200 - bouncing $HOSTNAME..." >> $log
    ssh `whoami`@${HOSTNAME} ${TOMCAT_DIR}/bin/stop
    ssh `whoami`@${HOSTNAME} ${TOMCAT_DIR}/bin/start
    curl -o /dev/null -s -I 'http://${HOSTNAME}:8080/gxa/experiments'
fi
