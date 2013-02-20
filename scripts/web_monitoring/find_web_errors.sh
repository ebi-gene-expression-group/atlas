#!/bin/bash
# @author: rpetry
# @date:   08 Feb 2013

# This script allows to find the errors in atlas.log and catalina-<YYYY-MM-DD>.out along with their usage context in access.log<YYYY-MM-DD>
# This script is to be run on www-test.ebi.ac.uk/gxa (ves-hx-75:8080/gxa) machine

if [ $# -lt 1 ]; then
        echo "Usage: $0 HOSTNAME"
        exit;
fi

HOSTNAME=$1
WEBLOG_DIR=/nfs/public/rw/webadmin/tomcat/bases/fg/tc-fg-gxa_test/logs

today="`eval date +%Y-%m-%d`"
process_file="/tmp/find_web_errors.$today"
atlaslog_file="${WEBLOG_DIR}/atlas.log"
accesslog_file="${WEBLOG_DIR}/${HOSTNAME}/access_$today.log"
catalina_file="${WEBLOG_DIR}/${HOSTNAME}/catalina-$today.out"

rm -rf $process_file.log

# Sort error types by frequency
for e in " ERROR " " FATAL " 
do
   grep $e $atlaslog_file | awk -F'-' '{print $8}' >> $process_file.error_breakdown.aux
done

grep "SEVERE"  $catalina_file | awk -F':' '{print $2}' >> $process_file.error_breakdown.aux

cat $process_file.error_breakdown.aux | egrep -v 'Threads are going to be renewed over time to try and avoid a probable memory leak' | sort | uniq -c | sort -rg > $process_file.error_breakdown

# Sort exception types by frequency
for f in $atlaslog_file $catalina_file
do
    grep "Exception:" $f | egrep -v "ERROR|FATAL|SEVERE" >> $process_file.exception_breakdown.aux
done
cat $process_file.exception_breakdown.aux | sort | uniq -c | sort -rg > $process_file.exception_breakdown



echo "Error frequencies/types occurred on ${HOSTNAME} on "`eval date +%Y-%m-%d`": "
echo "-----------------------------------------------------------------------------------------------"
cat $process_file.error_breakdown
echo ""
echo "The following Baseline Atlas exceptions frequencies/types occurred on ${HOSTNAME} on "`eval date +%Y-%m-%d`": "
echo "-----------------------------------------------------------------------------------------------"
cat $process_file.exception_breakdown

rm -rf $process_file.error_breakdown.aux
rm -rf $process_file.error_breakdown
rm -rf $process_file.exception_breakdown.aux
rm -rf $process_file.exception_breakdown
rm -rf $process_file.error_ids