#!/bin/bash
FROM=$(dirname $0)/../../../base/src/test/resources/solr
echo "This is not a great script, find complains about random things and it hangs at the end"

ssh lime sudo -u ma-svc /nfs/ma/home/atlas3/solr_integration.sh stop
if [[ $? != 0 ]]; then
    ssh lime sudo -u ma-svc /nfs/ma/home/atlas3/solr_integration.sh restart
    exit $rc;
fi

scp -r $FROM lime:/var/tmp/solr-sync-aux

ssh lime sudo -u ma-svc rm -rf /nfs/ma/home/atlas3/solr-conf/
ssh lime sudo -u ma-svc cp -r /var/tmp/solr-sync-aux/ /nfs/ma/home/atlas3/solr-conf/

ssh lime <<-EOSSH
sudo -u ma-svc -s -- << EOF
   find /nfs/ma/home/atlas3/solr-conf/ -type f -name 'schema.xml' | xargs -n 1 sed -i.backup 's/stored="false"/stored="true"/g'
   find /nfs/ma/home/atlas3/solr-conf/ -type f -name 'schema.xml.backup' | xargs rm
EOF
EOSSH
ssh lime rm -rf /var/tmp/solr-sync-aux

ssh lime sudo -u ma-svc /nfs/ma/home/atlas3/solr_integration.sh start

exit
