#!/bin/bash
# description: Starts and stops Solr production
SOLR_DIST=/nfs/public/rw/fg/atlas/solr_dev/
SOLR_INDEXES_DIR=/srv/gxa/solr
SOLR_CONF=/nfs/public/rw/fg/atlas/solr_dev_conf
SOLR_LOG=/srv/gxa/solr/log/
SOLR_BIN=${SOLR_DIST}/example
JAVA="/nfs/public/rw/webadmin/java/jdks/jdk1.7.0_04/bin/java"
JAVA_OPTIONS="-Dsolr.indexes.dir=$SOLR_INDEXES_DIR -Dsolr.solr.home=$SOLR_CONF -Dsolr.log=$SOLR_LOG -Dlog4j.configuration=file:$SOLR_CONF/log4j.properties -server -DSTOP.PORT=8079 -DSTOP.KEY=stopkey -Xmx8g -Xms1g -jar start.jar"
CONSOLE_LOG=/srv/gxa/solr/log/console.log
set -e
PATH=/usr/local/sbin:/usr/local/bin:/sbin:/bin:/usr/sbin:/usr/bin
case $1 in
	start)
		echo "Starting Solr"
		cd $SOLR_BIN
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
