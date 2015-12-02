#!/bin/bash
# description: Starts and stops Solr production, adapted from http://john.parnefjord.se/node/63

# location of unpacked solr tarball
SOLR_DIST=~/solr/solr-5.1.0
SOLR_INDEXES_DIR=~/solr/atlas/index
SOLR_CONF=~/solr/atlas/conf
SOLR_LOG=~/solr/atlas/logs
SOLR_BIN=${SOLR_DIST}/server
JAVA=`which java`

JAVA_OPTIONS="-Dsolr.indexes.dir=$SOLR_INDEXES_DIR -Dsolr.solr.home=$SOLR_CONF -Dsolr.log=$SOLR_LOG -Dlog4j.configuration=file:$SOLR_CONF/log4j.properties -server -DSTOP.PORT=8079 -DSTOP.KEY=stopkey -Xms1g -Xmx2g -jar start.jar"
CONSOLE_LOG=console.log

set -e
PATH=/usr/local/sbin:/usr/local/bin:/sbin:/bin:/usr/sbin:/usr/bin

case $1 in
	start)
		echo "Starting Solr"
		cd $SOLR_BIN
		echo $JAVA $JAVA_OPTIONS
		nohup $JAVA $JAVA_OPTIONS 2> $CONSOLE_LOG &
		echo "ok - remember it may take a minute or two before Solr responds on requests"
		;;
	stop)
		echo "Stopping Solr"
		cd $SOLR_BIN
		$JAVA $JAVA_OPTIONS --stop
    		echo "ok"
		;;
	restart)
		$0 stop
		sleep 3
		$0 start
		;;
	*)
		echo "Usage: $0 {start|stop|restart}" >&2
		exit 1
		;;
esac
