#!/bin/bash
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

EXPERIMENTS_RESULTS=$(curl ${EXPERIMENTS})
len=$(echo "$EXPERIMENTS_RESULTS" | jq .[] | jq length)

for((i=0; i<$len; i++)) {
    experimentAccession=$(echo "$EXPERIMENTS_RESULTS" | jq -r .aaData["$i"].experimentAccession)
    experimentType=$(echo "$EXPERIMENTS_RESULTS" | jq -r .aaData["$i"].experimentType)

    if [ "${experimentType}" == "RNASEQ_MRNA_BASELINE" ]
    then
        GENE_DISTRIBUTION=${ATLAS_ROOT_URL}/json/experiments/${experimentAccession}/genedistribution
        curl -s -o /dev/null -w "HTTP status code: %{http_code} Size: %{size_download} Time: %{time_total}\\n" ${GENE_DISTRIBUTION}
    fi
}

echo "Finished"
