#!/bin/bash
# description: Starts and stops Solr production, adapted from http://john.parnefjord.se/node/63

# location of unpacked solr tarball
SOLR_DIST=/nfs/ma/home/atlas3/solr_integration/
SOLR_INDEXES_DIR=/nfs/ma/home/tomcats/ATLAS3.TEST/data/solr
SOLR_CONF=/nfs/ma/home/atlas3/solr-conf/
SOLR_LOG=/nfs/ma/home/atlas3/log/
SOLR_BIN=${SOLR_DIST}/server
JAVA="/nfs/ma/home/java/jdk7/bin/java"

JAVA_OPTIONS="-Dsolr.indexes.dir=$SOLR_INDEXES_DIR -Dsolr.solr.home=$SOLR_CONF -Dsolr.log=$SOLR_LOG -Dlog4j.configuration=file:$SOLR_CONF/log4j.properties -server -DSTOP.PORT=8079 -DSTOP.KEY=stopkey -Xmx4g -Xms1g"
CONSOLE_LOG=$SOLR_LOG/console.log
SCRIPTPATH=$( cd "$(dirname "$0")" ; pwd -P )/solr_integration.sh

set -e
PATH=/usr/local/sbin:/usr/local/bin:/sbin:/bin:/usr/sbin:/usr/bin


case $1 in
	start)
		echo "Starting Solr"
		cd $SOLR_BIN
		nohup $JAVA $JAVA_OPTIONS -jar start.jar 2> $CONSOLE_LOG &
		echo "ok - remember it may take a minute or two before Solr responds on requests"
		;;
	forever)
		echo "Starting Solr with OOM handler"
		cd $SOLR_BIN
		nohup $JAVA $JAVA_OPTIONS -XX:OnOutOfMemoryError="date >> $CONSOLE_LOG && kill -9 %p && echo killed >> $CONSOLE_LOG && $SCRIPTPATH forever" -jar start.jar 2> $CONSOLE_LOG &
		echo "ok - remember it may take a minute or two before Solr responds on requests"
		;;
	stop)
		echo "Stopping Solr"
		cd $SOLR_BIN
		$JAVA $JAVA_OPTIONS -jar start.jar --stop
    		echo "ok"
		;;
	restart)
		$0 stop
		sleep 3
		$0 start
		;;
	*)
		echo "Usage: $0 {start|forever|stop|restart}" >&2
		exit 1
		;;
esac
