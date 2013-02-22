#!/bin/bash
# @author: rpetry
# @date:   8 Feb 2013

if [ $# -lt 1 ]; then
        echo "Usage: $0 HOSTNAME NOTIFICATION_EMAILADDRESS"
        exit;
fi

HOSTNAME=$1
NOTIFICATION_EMAILADDRESS=$2

log="/tmp/web_monitoring."`eval date +%Y%m%d`".log"
./web_response_times.sh $HOSTNAME:8080 > $log
./find_web_errors.sh $HOSTNAME >> $log

if [ -e "$log" ]; then
      mailx -s "[atlas3/cron] $HOSTNAME: Baseline Atlas Web Monitoring for "`date +'%d-%m-%Y'` ${NOTIFICATION_EMAILADDRESS} < $log
fi
