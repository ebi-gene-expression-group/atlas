#!/bin/bash
# Script to update the DEV Atlas instance withe the content of the STAGING (= 'data production') Atlas instance

if [ $# -ne 2 ]; then
        echo "Usage: $0 ATLAS_PROD ATLAS_DEV"
        exit;
fi

ATLAS_PROD=$1
ATLAS_DEV=$2

# Sanity tests to check we are in fact copying from prod to test and not the other way round...
echo $ATLAS_PROD | grep production > /dev/null
if [ $? != 0 ]; then
    echo "ERROR: Make sure the first argument is a production directory"
fi 
echo $ATLAS_DEV | grep dev > /dev/null
if [ $? != 0 ]; then
    echo "ERROR: Make sure the second argument is a dev directory"
fi

# Copy to $ATLAS_DEV all files from $ATLAS_PROD that are newer in source dir compared to target dir
cd $ATLAS_PROD
find . -xdev -depth -print | cpio -pdm $ATLAS_DEV
