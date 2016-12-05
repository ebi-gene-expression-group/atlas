#!/bin/bash
echo "I have never been verified to work"
echo "I probably don't work because ssh'es didn't always terminate when I do them"
echo "Exiting and encouraging you to read and fix the script :)"
exit

cd `dirname $O`

echo "Lime"
ssh lime sudo -u ma-svc cp /nfs/ma/home/atlas3/solr_integration.sh /var/tmp/solr_integration.sh.bak
ssh lime sudo -u ma-svc /var/tmp/solr_integration.sh stop
scp solr_integration.sh ma_svc@lime:/nfs/ma/home/atlas3/solr_integration.sh
ssh lime sudo -u ma-svc /var/tmp/solr_integration.sh forever

echo "Acceptance"
ssh ebi-005 sudo -u fg_atlas scp ves-hx-77:/nfs/public/rw/fg/atlas/solr_test.sh /var/tmp/solr_test.sh.ves-hx-77.bak
scp solr_test.sh ebi-005:/var/tmp/solr_test.sh
ssh ebi-005 sudo -u fg_atlas ssh ves-hx-77 /nfs/public/rw/fg/atlas/solr_test.sh stop
ssh ebi-005 sudo -u fg_atlas scp /var/tmp/solr_test.sh ves-hx-77:/nfs/public/rw/fg/atlas/solr_test.sh
ssh ebi-005 sudo -u fg_atlas ssh ves-hx-77 /nfs/public/rw/fg/atlas/solr_test.sh forever

echo "FIGURE THE REST OUT YOURSELF BRO"
exit

#
#
# 558  scp atlas-misc/scripts/solr/solr_integration.sh ma_svc@lime:/nfs/ma/home/atlas3/solr_integration.sh
# 559  scp atlas-misc/scripts/solr/solr_integration.sh lime:/var/tmp/solr_integration.sh
# 560  ssh lime sudo -u ma-svc /nfs/ma/home/atlas3/solr_integration.sh stop
# 561  ssh lime sudo -u ma-svc mv /var/tmp/solr_integration.sh /nfs/ma/home/atlas3/solr_integration.sh
# 562  ssh lime sudo -u ma-svc cp /var/tmp/solr_integration.sh /nfs/ma/home/atlas3/solr_integration.sh
# 563  ssh lime sudo -u ma-svc /var/tmp/solr_integration.sh forever
# 564  ssh lime sudo -u ma-svc /var/tmp/solr_integration.sh stop
# 565  ssh lime sudo -u /nfs/ma/home/atlas3/solr_integration.sh forever
# 566  ssh lime sudo -u ma-svc /nfs/ma/home/atlas3/solr_integration.sh forever
# 567  ssh lime
# 568  scp atlas-misc/scripts/solr/solr_test.sh ebi-005:/var/tmp
# 569  ssh ebi-005 sudo -u fg_atlas cp /nfs/public/rw/fg/atlas/solr_test.sh /var/tmp/solr_test.sh.bak
# 570  ssh ebi-005 sudo -u fg_atlas /nfs/public/rw/fg/atlas/solr_test.sh stop
# 571  ssh ebi-005 sudo -u fg_atlas cp /var/tmp/solr_test.sh /nfs/public/rw/fg/atlas/solr_test.sh
# 572  ssh ebi-005 sudo -u fg_atlas /nfs/public/rw/fg/atlas/solr_test.sh forever
# 573  ssh ebi-005 sudo -u fg_atlas /nfs/public/rw/fg/atlas/solr_test.sh stop
# 574  ssh ebi-005 sudo -u fg_atlas ssh ves-hx-77 /nfs/public/rw/fg/atlas/solr_test.sh stop
# 575  ssh ebi-005 sudo -u fg_atlas ssh ves-hx-77 /nfs/public/rw/fg/atlas/solr_test.sh forever
# 576  history | tail
# 577  history | tail 30
# 578  history | tail -n30
# 579  scp atlas-misc/scripts/solr/solr_standalone.sh ebi-005:/var/tmp
# 580  sudo -u fg_atlas ssh ves-hx-69 /nfs/public/rw/fg/atlas/solr_standalone.sh stop
# 581  ssh ebi-005 sudo -u fg_atlas ssh ves-hx-69 /nfs/public/rw/fg/atlas/solr_standalone.sh stop
# 582  ssh ebi-005 sudo -u fg_atlas cp /var/tmp/solr_standalone.sh /nfs/public/rw/fg/atlas/solr_standalone.sh
# 583  ssh ebi-005 sudo -u fg_atlas ssh ves-hx-69 /nfs/public/rw/fg/atlas/solr_standalone.sh forever
# 584  ssh ebi-005 sudo -u fg_atlas ssh ves-hx-69 tail /srv/gxa/solr/log/console.log
# 585  ssh ebi-005 sudo -u fg_atlas ssh ves-pg-75 /nfs/public/rw/fg/atlas/solr_standalone.sh stop
# 586  ssh ebi-005 sudo -u fg_atlas ssh ves-pg-75 /nfs/public/rw/fg/atlas/solr_standalone.sh forever
# 587  ssh ebi-005 sudo -u fg_atlas scp /nfs/public/rw/fg/atlas/solr_standalone.sh  ves-pg-75:/nfs/public/rw/fg/atlas/solr_standalone.sh
# 588  ssh ebi-005 sudo -u fg_atlas ssh ves-pg-75 /nfs/public/rw/fg/atlas/solr_standalone.sh forever
# 589  ssh ebi-005 sudo -u fg_atlas ssh ves-oy-75 /nfs/public/rw/fg/atlas/solr_standalone.sh stop
# 590  ssh ebi-005 sudo -u fg_atlas scp /nfs/public/rw/fg/atlas/solr_standalone.sh ves-oy-75:/nfs/public/rw/fg/atlas/solr_standalone.sh
# 591  ssh ebi-005 sudo -u fg_atlas ssh ves-oy-75 /nfs/public/rw/fg/atlas/solr_standalone.sh forever
# 592  cd atlas-misc/scripts/solr/
# 593  ls
# 594  scp solr_integration.sh lime:/var/tmp/solr_integration.sh
# 595  ssh lime sudo -u ma-svc /nfs/ma/home/atlas3/solr_integration.sh stop
# 596  ssh lime sudo -u ma-svc cp /var/tmp/solr_integration.sh /nfs/ma/home/atlas3/solr_integration.sh
# 597  ssh lime sudo -u ma-svc /nfs/ma/home/atlas3/solr_integration.sh forever
# 598  scp solr_standalone.sh ebi-005:/var/tmp
# 599  scp solr_test.sh ebi-005:/var/tmp
# 600  ssh ebi-005 sudo -u fg_atlas /nfs/ma/home/atlas3/atlas_test.sh stop
# 601  ssh ebi-005 sudo -u fg_atlas /nfs/public/rw/fg/atlas/solr_test.sh stop
# 602  ssh ebi-005 sudo -u fg_atlas ssh ves-hx-77 /nfs/public/rw/fg/atlas/solr_test.sh stop
# 603  ssh ebi-005 sudo -u fg_atlas scp /var/tmp/solr_test.sh ves-hx-77:/nfs/public/rw/fg/atlas/solr_test.sh
# 604  ssh ebi-005 sudo -u fg_atlas ssh ves-hx-77 /nfs/public/rw/fg/atlas/solr_test.sh forever
# 605  ssh ebi-005 sudo -u fg_atlas ssh ves-oy-75 /nfs/public/rw/fg/atlas/solr_standalone.sh stop
# 606  ssh ebi-005 sudo -u fg_atlas scp /var/tmp/solr_standalone.sh ves-oy-75:/nfs/public/rw/fg/atlas/solr_standalone.sh
# 607  ssh ebi-005 sudo -u fg_atlas ssh ves-oy-75 /nfs/public/rw/fg/atlas/solr_standalone.sh forever
# 608  curl ves-oy-75:9893 | head
# 609  curl ves-oy-75:8983 | head
# 610  ssh ebi-005 sudo -u fg_atlas ssh ves--hx-69 /nfs/public/rw/fg/atlas/solr_standalone.sh stop
# 611  ssh ebi-005 sudo -u fg_atlas ssh ves-hx-69 /nfs/public/rw/fg/atlas/solr_standalone.sh stop
# 612  ssh ebi-005 sudo -u fg_atlas scp /var/tmp/solr_standalone.sh ves-hx-69:/nfs/public/rw/fg/atlas/solr_standalone.sh
# 613  ssh ebi-005 sudo -u fg_atlas ssh ves-hx-69 cat /nfs/public/rw/fg/atlas/solr_standalone.sh
# 614  ssh ebi-005 sudo -u fg_atlas ssh ves-hx-69 /nfs/public/rw/fg/atlas/solr_standalone.sh forever
# 615  ssh ebi-005 sudo -u fg_atlas ssh ves-oy-75 /nfs/public/rw/fg/atlas/solr_standalone.sh stop
# 616  ssh ebi-005 sudo -u fg_atlas scp /var/tmp/solr_standalone.sh ves-oy-75:/nfs/public/rw/fg/atlas/solr_standalone.sh
# 617  ssh ebi-005 sudo -u fg_atlas ssh ves-oy-75 /nfs/public/rw/fg/atlas/solr_standalone.sh forever
