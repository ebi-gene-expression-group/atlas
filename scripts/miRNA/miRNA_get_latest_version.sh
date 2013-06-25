#!/bin/bash
# A script to populate the latest miRBase release version into the microRNA analysis methods template
# Author: rpetry@ebi.ac.uk
pushd /nfs/ma/home/atlas3-production/arraydesigns/microRNA
wget ftp://anonymous:anonymous\@mirbase.org/pub/mirbase/CURRENT/README
ver=`head -1 README | awk '{print $NF}'`
perl -pi -e "s|miRBase release [\d.]+? only|miRBase release $ver only|" /nfs/ma/home/atlas3-production/analysis/differential/microarray/onecolour-mirna-microarray-differential-analytics-methods.tsv
perl -pi -e "s|miRBase release [\d.]+? only|miRBase release $ver only|" /nfs/ma/home/atlas3-production/analysis/differential/microarray/twocolour-mirna-microarray-differential-analytics-methods.tsv
mv README miRBase
popd