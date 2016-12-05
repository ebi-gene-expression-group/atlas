#!/bin/bash
# description: Starts and stops Solr staging
SOLR_DIST=/nfs/public/rw/fg/atlas/solr-5.1.0
SOLR_INDEXES_DIR=/srv/gxa/solr/index
SOLR_CONF=/srv/gxa/solr/conf
SOLR_LOG=/srv/gxa/solr/log
SOLR_BIN=${SOLR_DIST}/server

JAVA="/nfs/public/rw/webadmin/java/jdks/jdk1.7.0_04/bin/java"
JAVA_OPTIONS="-Dsolr.indexes.dir=$SOLR_INDEXES_DIR -Dsolr.solr.home=$SOLR_CONF -Dsolr.log=$SOLR_LOG -Dlog4j.configuration=file:$SOLR_CONF/log4j.properties -server -DSTOP.PORT=8079 -DSTOP.KEY=stopkey -Xmx22g -Xms22g"

JAVA_OPTIONS_STOP="-Dsolr.indexes.dir=$SOLR_INDEXES_DIR -Dsolr.solr.home=$SOLR_CONF -Dsolr.log=$SOLR_LOG -Dlog4j.configuration=file:$SOLR_CONF/log4j.properties -server -DSTOP.PORT=8079 -DSTOP.KEY=stopkey -Xmx256m"

CONSOLE_LOG=/srv/gxa/solr/log/console.log
SCRIPTPATH=$( cd "$(dirname "$0")" ; pwd -P )/solr_standalone.sh

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
		$JAVA $JAVA_OPTIONS_STOP -jar start.jar --stop
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
