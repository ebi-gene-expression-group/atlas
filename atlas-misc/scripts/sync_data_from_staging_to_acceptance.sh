#!/usr/bin/env bash

echo "============================================================"
echo `date`
echo "============================================================"
echo "Stopping Tomcat on ves-hx-77..."
sudo -u tc_fg02 ssh ves-hx-77 '/nfs/public/rw/webadmin/tomcat/bases/fg/tc-fg-gxa_test/bin/controller stop'

echo "============================================================"
echo `date`
echo "============================================================"
echo "Syncing experiment designs from ves-hx-76 to ves-hx-77..."
sudo -u tc_fg02 ssh ves-hx-77 'rsync -irtpz --safe-links --delete ves-hx-76:/srv/gxa/data/expdesign/* /srv/gxa/data/expdesign'

echo "============================================================"
echo `date`
echo "============================================================"
echo "Syncing admin logs from ves-hx-76 to ves-hx-77..."
sudo -u tc_fg02 ssh ves-hx-77 'rsync -irtpz --safe-links --delete ves-hx-76:/srv/gxa/data/admin/* /srv/gxa/data/admin'

echo "Skipping syncing of serialized expression: this is only safe if code in ves-hx-76 and ves-hx-77 are in sync"
#echo "============================================================"
#echo `date`
#echo "============================================================"
#echo "Syncing serialized expression from ves-hx-76 to ves-hx-77..."
#sudo -u tc_fg02 ssh ves-hx-77 'rsync -irtpz --safe-links --delete ves-hx-76:/srv/gxa/data/serialized_expression/* /srv/gxa/data/serialized_expression'

echo "============================================================"
echo `date`
echo "============================================================"
echo "Syncing magetab directory from experiments to experiments_test..."
sudo -u fg_atlas sh -c 'rsync -irlpt --delete /nfs/public/ro/fg/atlas/experiments/* /nfs/public/ro/fg/atlas/experiments_test'

echo "============================================================"
echo `date`
echo "============================================================"
echo "Refreshing VATLASTST to latest snapshot..."
sudo -u dxatlas sh -c '/nfs/dbtools/delphix/postgres/ebi_refresh_vdb.sh -d pgsql-dlvm-005.ebi.ac.uk -S "`/nfs/dbtools/delphix/postgres/ebi_list_snapshots.sh -d pgsql-dlvm-005.ebi.ac.uk | tail -n1`"'

echo "============================================================"
echo `date`
echo "============================================================"
echo "Starting Tomcat..."
sudo -u tc_fg02 ssh ves-hx-77 '/nfs/public/rw/webadmin/tomcat/bases/fg/tc-fg-gxa_test/bin/controller jpda start' &

echo -n "Waiting tow minutes for Atlas to start... "
sleep 120
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
