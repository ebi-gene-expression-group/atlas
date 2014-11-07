#!/bin/bash
# description: Starts and stops Solr production, adapted from http://john.parnefjord.se/node/63

# location of unpacked solr tarball
SOLR_DIST=/nfs/ma/home/atlas3/solr-integration/
SOLR_INDEXES_DIR=/nfs/ma/home/tomcats/ATLAS3.TEST/data/solr
SOLR_CONF=./solr
SOLR_BIN=${SOLR_DIST}/example
JAVA="/nfs/ma/home/java/jdk7/bin/java"

JAVA_OPTIONS="-Dsolr.indexes.dir=$SOLR_INDEXES_DIR -Dsolr.solr.home=$SOLR_CONF -Djava.util.logging.config.file=$SOLR_DIST/logging.properties -server -DSTOP.PORT=8079 -DSTOP.KEY=stopkey -Xmx4096M -Xms1024M -jar start.jar"
CONSOLE_LOG="$SOLR_DIST/console.log"

set -e
PATH=/usr/local/sbin:/usr/local/bin:/sbin:/bin:/usr/sbin:/usr/bin

case $1 in
	start)
		echo "Starting Solr"
		cd $SOLR_BIN
		#echo $JAVA $JAVA_OPTIONS
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
