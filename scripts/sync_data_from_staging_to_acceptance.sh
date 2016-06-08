#!/usr/bin/env bash

#!/usr/bin/env bash

echo "Stopping Tomcat on ves-hx-77..."
sudo -u tc_fg02 ssh ves-hx-77 '/nfs/public/rw/webadmin/tomcat/bases/fg/tc-fg-gxa_test/bin/controller stop'

# sudo -u fg_atlas ssh ves-hx-77 './atlas_scripts/sync_solr.sh'
echo "Stopping Solr on ves-hx-77..."
sudo -u fg_atlas ssh ves-hx-77 '/nfs/public/rw/fg/atlas/solr_acceptance.sh stop'

echo "Syncing Solr indexes from ves-hx-76 to ves-hx-77..."
sudo -u fg_atlas ssh ves-hx-77 'rsync -irltz --delete ves-hx-76:/srv/gxa/solr/* /srv/gxa/solr'

echo "Starting Solr on ves-hx-77:"
sudo -u fg_atlas ssh ves-hx-77 '/nfs/public/rw/fg/atlas/solr_acceptance.sh start' &

# sudo -u fg_atlas ssh ves-hx-77 './atlas_scripts/sync_data_dir.sh'
echo "Syncing data from ves-hx-76 to ves-hx-77..."
sudo -u fg_atlas ssh ves-hx-77 'rsync -irltz --delete --exclude gxa/solr** --exclude gxa/lost+found** ves-hx-76:/srv/gxa /srv'

echo "Refreshing ATLASPUBFALL1 to latest snapshot..."
sudo -u dxatlas ssh fb1-001 '/net/nasP/oracle/delphix/ebi_refresh_vdb.sh ATLASPUBFALL1 "`/net/nasP/oracle/delphix/ebi_list_snapshots.sh ATLASPUBFALL1 | tail -1`"'

echo "Starting Tomcat..."
sudo -u tc_fg02 ssh ves-hx-77 '/nfs/public/rw/webadmin/tomcat/bases/fg/tc-fg-gxa_test/bin/controller jpda start' &

echo -n "Waiting for Atlas to start... "
sleep 60
echo "done"

echo "Warm server caches..."
cd ~/atlas_scripts
./warm_server.sh ves-hx-77
echo "\nAll done!"
