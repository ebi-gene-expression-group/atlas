#!/usr/bin/env bash

echo "============================================================"
echo `date`
echo "============================================================"
echo "Stopping Tomcat on ves-hx-77..."
sudo -u tc_fg02 ssh ves-hx-77 '/nfs/public/rw/webadmin/tomcat/bases/fg/tc-fg-gxa_test/bin/controller stop'

echo "============================================================"
echo `date`
echo "============================================================"
echo "Replicating Solr indexes from ves-hx-69 to ves-hx-77..."
sudo -u fg_atlas ssh ves-hx-77 'rsync -irltpz --delete ves-hx-69:/srv/gxa/solr/* /srv/gxa/solr'

echo "============================================================"
echo `date`
echo "============================================================"
echo "Syncing experiment designs from ves-hx-76 to ves-hx-77..."
curl http://ves-hx-77:8983/solr/analytics/replication?command=fetchindex
curl http://ves-hx-77:8983/solr/baselineConditions/replication?command=fetchindex
curl http://ves-hx-77:8983/solr/differentialConditions/replication?command=fetchindex
curl http://ves-hx-77:8983/solr/gxa/replication?command=fetchindex

echo "============================================================"
echo `date`
echo "============================================================"
echo "Syncing admin logs from ves-hx-76 to ves-hx-77..."
sudo -u tc_fg02 ssh ves-hx-77 'rsync -irtpz --safe-links --delete ves-hx-76:/srv/gxa/data/admin/* /srv/gxa/data/admin'

echo "============================================================"
echo `date`
echo "============================================================"
echo "Syncing serialized expression from ves-hx-76 to ves-hx-77..."
sudo -u tc_fg02 ssh ves-hx-77 'rsync -irtpz --safe-links --delete ves-hx-76:/srv/gxa/data/serialized_expression/* /srv/gxa/data/serialized_expression'

# sudo -u fg_atlas ssh ves-hx-77 './atlas_scripts/sync_data_dir.sh'
echo "============================================================"
echo `date`
echo "============================================================"
echo "Syncing magetab directory from experiments to experiments_test..."
sudo -u fg_atlas sh -c 'rsync -irlpt --delete /nfs/public/ro/fg/atlas/experiments/* /nfs/public/ro/fg/atlas/experiments_test'

echo "Refreshing VATLASTST to latest snapshot..."
sudo -u dxatlas sh -c '/net/nasP/oracle/delphix/ebi_refresh_vdb.sh Delphix_Silver1 VATLASTST "`/net/nasP/oracle/delphix/ebi_list_snapshots.sh Delphix_Silver1 VATLASTST | tail -1`"'
echo "============================================================"
echo `date`
echo "============================================================"

echo "Starting Tomcat..."
sudo -u tc_fg02 ssh ves-hx-77 '/nfs/public/rw/webadmin/tomcat/bases/fg/tc-fg-gxa_test/bin/controller jpda start' &
echo "============================================================"
echo `date`
echo "============================================================"

echo -n "Waiting for Atlas to start... "
sleep 60
echo "done"

echo "============================================================"
echo `date`
echo "============================================================"
echo "Warm server caches..."
cd ~/atlas_scripts
./warm_experiment_cache.sh ves-hx-77

echo "============================================================"
echo `date`
echo "============================================================"
echo "All done!"
