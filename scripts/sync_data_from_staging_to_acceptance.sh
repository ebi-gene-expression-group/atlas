#!/usr/bin/env bash

echo "============================================================"
echo `date`
echo "============================================================"
echo "Stopping Tomcat on ves-hx-77..."
sudo -u tc_fg02 ssh ves-hx-77 '/nfs/public/rw/webadmin/tomcat/bases/fg/tc-fg-gxa_test/bin/controller stop'

# sudo -u fg_atlas ssh ves-hx-77 './atlas_scripts/sync_solr.sh'
echo "============================================================"
echo `date`
echo "============================================================"
echo "Stopping Solr on ves-hx-77..."
sudo -u fg_atlas ssh ves-hx-77 '/nfs/public/rw/fg/atlas/solr_production.sh stop'

echo "============================================================"
echo `date`
echo "============================================================"
echo "Syncing Solr indexes from ves-hx-76 to ves-hx-77..."
sudo -u fg_atlas ssh ves-hx-77 'rsync -irltz --delete ves-hx-76:/srv/gxa/solr/* /srv/gxa/solr'

echo "============================================================"
echo `date`
echo "============================================================"
echo "Starting Solr on ves-hx-77..."
sudo -u fg_atlas ssh ves-hx-77 '/nfs/public/rw/fg/atlas/solr_production.sh start' &

# sudo -u fg_atlas ssh ves-hx-77 './atlas_scripts/sync_data_dir.sh'
echo "============================================================"
echo `date`
echo "============================================================"
echo "Syncing data from ves-hx-76 to ves-hx-77..."
sudo -u fg_atlas ssh ves-hx-77 'rsync -irltz --delete --exclude gxa/solr** --exclude gxa/lost+found** ves-hx-76:/srv/gxa /srv'

echo "Refreshing ATLASPUBFALL1 to latest snapshot..."
sudo -u dxatlas sh -c '/net/nasP/oracle/delphix/ebi_refresh_vdb.sh ATLASTST "`/net/nasP/oracle/delphix/ebi_list_snapshots.sh ATLASTST | tail -1`"'
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
./warm_server.sh ves-hx-77

echo "============================================================"
echo `date`
echo "============================================================"
echo "All done!"
