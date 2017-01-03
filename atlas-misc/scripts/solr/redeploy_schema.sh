#!/bin/bash

CORE_NAME=$1
TARGET_VM=$2
if [ -z "CORE_NAME" ]
  then
    echo "Specify core to update as the first arg"
    exit 1
fi

if [ -z "TARGET_VM" ]
  then
    echo "Specify target VM as the second arg"
    exit 1
fi

FROM=$(dirname $0)/../../../base/src/test/resources/solr/$CORE_NAME/conf
AUX=/var/tmp/solr-schema-conf-aux
TO=/srv/gxa/solr/conf/$CORE_NAME/conf

scp -r $FROM ebi-005:$AUX

ssh ebi-005 sudo -u fg_atlas scp -r $AUX/* $TARGET_VM:$TO

ssh ebi-005 rm -rf $AUX
