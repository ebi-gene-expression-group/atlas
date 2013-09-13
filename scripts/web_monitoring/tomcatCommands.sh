#!/bin/bash
#       
# tomcat7     This shell script takes care of starting and stopping Tomcat
# Description: This shell script takes care of starting and stopping Tomcat
# chkconfig: - 80 20
#
## Source function library.
#. /etc/rc.d/init.d/functions
TOMCAT_HOME=$2
SHUTDOWN_WAIT=20

tomcat_pid() {
  echo `ps aux | grep -- -Dcatalina.base=$TOMCAT_HOME | grep -v grep | awk '{ print $2 }'`
}

start() {
  pid=$(tomcat_pid)
  if [ -n "$pid" ] 
  then
    echo "Tomcat is already running (pid: $pid)"
  else
    # Start tomcat
    echo "\nStarting tomcat"
    $TOMCAT_HOME/bin/startup.sh
  fi

  return 0
}

stop() {
  pid=$(tomcat_pid)
  echo pid=$pid
  if [ -n "$pid" ]
  then
    echo "Stopping Tomcat"
    $TOMCAT_HOME/bin/shutdown.sh

    let kwait=$SHUTDOWN_WAIT
    count=0;
    until [ `ps -p $pid | grep -c $pid` = '0' ] || [ $count -gt $kwait ]
    do
      echo -n -e "\nwaiting for processes to exit";
      sleep 1
      let count=$count+1;
    done

    if [ $count -gt $kwait ]; then
      echo -n -e "\nkilling processes which didn't stop after $SHUTDOWN_WAIT seconds"
      kill -9 $pid
    fi
  else
    echo "Tomcat is not running"
  fi
 
  return 0
}

case $1 in
start)
  start
;; 
stop)   
  stop
;; 
restart)
  stop
  start
;;
status)
  pid=$(tomcat_pid)
  if [ -n "$pid" ]
  then
    echo "Tomcat is running with pid: $pid"
  else
    echo "Tomcat is not running"
  fi
;; 
esac
exit 0
