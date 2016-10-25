#!/bin/bash
FROM=$(dirname $0)/../solr/conf
echo "This is not a great script, find complains about random things and it hangs at the end"

ssh lime sudo -u ma-svc /nfs/ma/home/atlas3/solr_integration.sh stop
if [[ $? != 0 ]]; then
    ssh lime sudo -u ma-svc /nfs/ma/home/atlas3/solr_integration.sh restart
    exit $rc;
fi

scp -r $FROM lime:/var/tmp/solr-sync-aux

ssh lime <<-EOSSH
sudo -u ma-svc -s -- << EOF
   rm -rf /nfs/ma/home/atlas3/solr_integration_conf/
   cp -r /var/tmp/solr-sync-aux/ /nfs/ma/home/atlas3/solr_integration_conf/
   find /nfs/ma/home/atlas3/solr_integration_conf/ -type f -name 'schema.xml' | xargs -n 1 sed -i.backup 's/stored="false"/stored="true"/g'
   find /nfs/ma/home/atlas3/solr_integration_conf/ -type f -name 'schema.xml.backup' | xargs rm
EOF
rm -rf /var/tmp/solr-sync-aux
EOSSH

ssh lime sudo -u ma-svc /nfs/ma/home/atlas3/solr_integration.sh start

exit







