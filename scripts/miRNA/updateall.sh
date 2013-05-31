#!/bin/bash
# A wrapper script to update all array designs in the current directory to the latest miRBase release
# Author: rpetry@ebi.ac.uk

wget ftp://anonymous:anonymous\@mirbase.org/pub/mirbase/CURRENT/aliases.txt.gz
gunzip aliases.txt.gz

for ad in $(ls -d A-* ); do
   scripts/miRNA_arraydesign_to_mirBase.pl -adf $ad/$ad.adf.txt -out $ad/${ad}_reporter_mirbase.txt
done

mv aliases.txt miRBase
