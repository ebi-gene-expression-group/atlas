#!/bin/bash
# This script should be run from the same directory in which it exists
# Author: Robert Petryszak
if [ $# -lt 3 ]; then
  echo "Please provide:
  echo Ensembl release number, species, genome build identifier and public ftp server base (and optionally 'clean' if previous reference should be removed), e.g.:"
  echo "Example usage:"
  echo "$0 68 homo_sapiens GRCh37 ftp://ftp.ensembl.org/pub clean"
  exit 1
fi 

function capitalize_first_letter {
    arg=$1
    echo -n $arg | sed 's/\(.\).*/\1/' | tr "[:lower:]" "[:upper:]"; echo -n $arg | sed 's/.\(.*\)/\1/'
}

VERSION=$1
ORGANISM=$2
BUILD_IDENTIFIER=$3
PUB_FTP_BASE=$4
REMOVE_PREVIOUS_REFERENCE=$5

UC_ORGANISM=`capitalize_first_letter $ORGANISM`

pushd $EREAP_CLONE/data/reference

# Create an organism-specifc subdirectory - if it doesn't already exist
if [ ! -d "$ORGANISM" ]
then
    mkdir -p "$ORGANISM"
fi

if [ $REMOVE_PREVIOUS_REFERENCE ]; then
   echo "rm -rf $ORGANISM/*"
fi

pushd "$ORGANISM"

# reference
wget -L ${PUB_FTP_BASE}/release-${VERSION}/fasta/${ORGANISM}/dna/${UC_ORGANISM}.${BUILD_IDENTIFIER}.${VERSION}.dna.toplevel.fa.gz
wget -L ${PUB_FTP_BASE}/release-${VERSION}/gtf/${ORGANISM}/${UC_ORGANISM}.${BUILD_IDENTIFIER}.${VERSION}.gtf.gz
wget -L ${PUB_FTP_BASE}/release-${VERSION}/fasta/${ORGANISM}/cdna/${UC_ORGANISM}.${BUILD_IDENTIFIER}.${VERSION}.cdna.all.fa.gz

popd
popd

exit 0