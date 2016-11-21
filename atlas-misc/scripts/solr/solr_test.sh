#!/bin/bash
# description: Starts and stops Solr production
set -e
PATH=/usr/local/sbin:/usr/local/bin:/sbin:/bin:/usr/sbin:/usr/bin
SOLR_DIR=/nfs/public/rw/fg/atlas/solr_test/example
JAVA_OPTIONS="-Dsolr.indexes.dir=/srv/gxa/solr -Dsolr.solr.home=./solr -Djava.util.logging.config.file=solr_test/logging.properties -server -DSTOP.PORT=8079 -DSTOP.KEY=stopkey -Xmx8g -Xms1g -jar start.jar"
JAVA=/nfs/public/rw/webadmin/java/jdks/jdk1.7.0_04/bin/java
LOG_FILE="/srv/gxa/solr/log/console.log"
case $1 in
	start)
		echo "Starting Solr"
		cd $SOLR_DIR
		nohup $JAVA $JAVA_OPTIONS 2> $LOG_FILE &
		echo "ok - remember it may take a minute or two before Solr responds on requests"
		;;
	stop)
		echo "Stopping Solr"
		cd $SOLR_DIR
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
