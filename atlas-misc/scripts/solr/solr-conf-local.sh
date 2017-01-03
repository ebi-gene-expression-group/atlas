#!/bin/bash
FROM=$(dirname $0)/../../../base/src/test/resources/solr

echo "This is not a great script, it works for Wojtek's local environment and that's probably it"

~/solr/atlas/solr.sh stop
if [[ $? != 0 ]]; then
    ~/solr/atlas/solr.sh restart
    exit $rc;
fi

cp -r $FROM /var/tmp/solr-sync-aux

rm -rf ~/solr/atlas/conf/
cp -r /var/tmp/solr-sync-aux/ ~/solr/atlas/conf/

find ~/solr/atlas/conf/ -type f -name 'schema.xml' | xargs -n 1 sed -i.backup 's/stored="false"/stored="true"/g'
find ~/solr/atlas/conf/ -type f -name 'schema.xml.backup' | xargs rm

rm -rf /var/tmp/solr-sync-aux

~/solr/atlas/solr.sh start

exit
