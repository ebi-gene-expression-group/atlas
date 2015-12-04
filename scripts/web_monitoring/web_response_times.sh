#!/bin/bash
# @author: rpetry
# @date:   8 Feb 2013

# This script measures web response times on a given hostname for selected Baseline Atlas queries

if [ $# -lt 1 ]; then
        echo "Usage: $0 HOSTNAME"
        exit;
fi

HOSTNAME=$1
ATLAS_ROOT_URL="http://${HOSTNAME}/gxa"

echo "Web response times (secs) for ${ATLAS_ROOT_URL}:"
echo "------------------------------------------------------------------------------------------------------------------------------------------------------------------------"
echo "query                                                                                                      http_code    time_connect    time_starttransfer    time_total"
curl -o /dev/null -X GET -s -w %{http_code}:%{time_connect}:%{time_starttransfer}:%{time_total} "${ATLAS_ROOT_URL}/experiments/E-GEOD-31234" | awk -F":" '{print "/experiments/E-GEOD-30352                                                                                  "$1"          "$2"           "$3"                 "$4}'
curl -o /dev/null -X GET -s -w %{http_code}:%{time_connect}:%{time_starttransfer}:%{time_total} "${ATLAS_ROOT_URL}/experiments" | awk -F":" '{print "/experiments                                                                                               "$1"          "$2"           "$3"                 "$4}'
curl -o /dev/null -X GET -s -w %{http_code}:%{time_connect}:%{time_starttransfer}:%{time_total} "${ATLAS_ROOT_URL}/proteins/ENSP00000355434" | awk -F":" '{print "/proteins/ENSP00000355434                                                                                  "$1"          "$2"           "$3"                 "$4}'
curl -o /dev/null -X GET -s -w %{http_code}:%{time_connect}:%{time_starttransfer}:%{time_total} "${ATLAS_ROOT_URL}/genes/AT3G11340" | awk -F":" '{print "/genes/AT3G11340                                                                                           "$1"          "$2"           "$3"                 "$4}'
curl -o /dev/null -X GET -s -w %{http_code}:%{time_connect}:%{time_starttransfer}:%{time_total} "${ATLAS_ROOT_URL}/widgets/heatmap/referenceExperiment?geneQuery=R-HSA-162582&geneSetMatch=true" | awk -F":" '{print "/widgets/heatmap/referenceExperiment?geneQuery=REACT_111102&geneSetMatch=true                              "$1"          "$2"           "$3"                 "$4}'
curl -o /dev/null -X GET -s -w %{http_code}:%{time_connect}:%{time_starttransfer}:%{time_total} "${ATLAS_ROOT_URL}/widgets/heatmap/referenceExperiment?geneQuery=ENSG00000187003+ENSG00000185264&species=homo%20sapiens" | awk -F":" '{print "/widgets/heatmap/referenceExperiment?geneQuery=ENSG00000187003+ENSG00000185264&species=homo%20sapiens      "$1"          "$2"           "$3"                 "$4}'
curl -o /dev/null -X GET -s -w %{http_code}:%{time_connect}:%{time_starttransfer}:%{time_total} "${ATLAS_ROOT_URL}/query?condition=carcinoma" | awk -F":" '{print "/query?condition=carcinoma                                                                                 "$1"          "$2"           "$3"                "$4}'
curl -o /dev/null -X GET -s -w %{http_code}:%{time_connect}:%{time_starttransfer}:%{time_total} "${ATLAS_ROOT_URL}/query?geneQuery=ENSG00000157107%20ENSG00000172482" | awk -F":" '{print "/query?geneQuery=ENSG00000157107%20ENSG00000172482                                                         "$1"          "$2"           "$3"                 "$4}'
curl -o /dev/null -X GET -s -w %{http_code}:%{time_connect}:%{time_starttransfer}:%{time_total} "${ATLAS_ROOT_URL}/das/s4/features?segment=ENSG00000012048" | awk -F":" '{print "/das/s4/features?segment=ENSG00000012048                                                                   "$1"          "$2"           "$3"                 "$4}'
echo ""
echo ""
