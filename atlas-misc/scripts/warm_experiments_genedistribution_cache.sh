#!/bin/bash
set -euo pipefail
IFS=$'\n\t'
# @author: barrera
# @date:   27 Sept 2017

if [ $# -lt 1 ]; then
        echo "Accesses the json URL gene distribution per experiment accession and after application startup"
        echo "will load all gene distribution per experiments into cache."
        echo
        echo "Usage: $0 HOSTNAME"
        exit;
fi

HOSTNAME=$1
PORT=8080
ATLAS_ROOT_URL="http://${HOSTNAME}:${PORT}/gxa"
EXPERIMENTS=${ATLAS_ROOT_URL}/json/experiments

EXPERIMENTS_RESULTS=$(curl -s -S ${EXPERIMENTS})
len=$(echo "$EXPERIMENTS_RESULTS" | jq .[] | jq length)

curl -s -S ${ATLAS_ROOT_URL}/json/experiments | jq -r '.aaData | map(select(.experimentType=="RNASEQ_MRNA_BASELINE")) | map(.experimentAccession)[]' | while read -r experimentAccession; do {
	GENE_DISTRIBUTION=${ATLAS_ROOT_URL}/json/experiments/${experimentAccession}/genedistribution
    STATUS=$(curl -s -S -o /dev/null -w '%{http_code}' ${GENE_DISTRIBUTION})
    if [ "$STATUS" != 200 ]; then
        echo "Got error HTTP $STATUS for: $GENE_DISTRIBUTION " >&2
        sleep 10
    fi
}
done

echo "Finished!"
