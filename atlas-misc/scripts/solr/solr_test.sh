#!/bin/bash
# description: Starts and stops Solr in acceptance and production servers (ves-hx-77, ves-pg-76, ves-pg-77, ves-oy-76, ves-oy-77))
SOLR_DIST=/nfs/public/rw/fg/atlas/solr-5.1.0
SOLR_INDEXES_DIR=/srv/gxa/solr/index
SOLR_CONF=/srv/gxa/solr/conf
SOLR_LOG=/srv/gxa/solr/log
SOLR_BIN=${SOLR_DIST}/server

JAVA_OPTIONS="-Dsolr.indexes.dir=/srv/gxa/solr -Dsolr.solr.home=./solr -Djava.util.logging.config.file=solr_test/logging.properties -server -DSTOP.PORT=8079 -DSTOP.KEY=stopkey -Xmx12g -Xms12g"
JAVA=/nfs/public/rw/webadmin/java/jdks/jdk1.7.0_04/bin/java
CONSOLE_LOG=$SOLR_LOG/console.log
SCRIPTPATH=$( cd "$(dirname "$0")" ; pwd -P )/solr_test.sh

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
