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
echo "-----------------------------------------------------------------------------------------------"
echo "query                              http_code    time_connect    time_starttransfer    time_total"
curl -o /dev/null -X GET -s -w %{http_code}:%{time_connect}:%{time_starttransfer}:%{time_total} "${ATLAS_ROOT_URL}/experiments/E-MTAB-513" | awk -F":" '{print "/experiments/E-MTAB-513            "$1"          "$2"           "$3"                 "$4}'
curl -o /dev/null -X GET -s -w %{http_code}:%{time_connect}:%{time_starttransfer}:%{time_total} "${ATLAS_ROOT_URL}/experiments/E-MTAB-599" | awk -F":" '{print "/experiments/E-MTAB-599            "$1"          "$2"           "$3"                 "$4}'
curl -o /dev/null -X GET -s -w %{http_code}:%{time_connect}:%{time_starttransfer}:%{time_total} "${ATLAS_ROOT_URL}/experiments/E-GEOD-26284" | awk -F":" '{print "/experiments/E-GEOD-26284          "$1"          "$2"           "$3"                 "$4}'
curl -o /dev/null -X GET -s -w %{http_code}:%{time_connect}:%{time_starttransfer}:%{time_total} "${ATLAS_ROOT_URL}/experiments/E-GEOD-30352" | awk -F":" '{print "/experiments/E-GEOD-30352          "$1"          "$2"           "$3"                 "$4}'
curl -o /dev/null -X GET -s -w %{http_code}:%{time_connect}:%{time_starttransfer}:%{time_total} "${ATLAS_ROOT_URL}/experiments/E-MTAB-513.tsv" | awk -F":" '{print "/experiments/E-MTAB-513.tsv        "$1"          "$2"           "$3"                 "$4}'
curl -o /dev/null -X GET -s -w %{http_code}:%{time_connect}:%{time_starttransfer}:%{time_total} "${ATLAS_ROOT_URL}/experiments/E-MTAB-599.tsv" | awk -F":" '{print "/experiments/E-MTAB-599.tsv        "$1"          "$2"           "$3"                 "$4}'
curl -o /dev/null -X GET -s -w %{http_code}:%{time_connect}:%{time_starttransfer}:%{time_total} "${ATLAS_ROOT_URL}/experiments/E-GEOD-26284.tsv" | awk -F":" '{print "/experiments/E-GEOD-26284.tsv      "$1"          "$2"           "$3"                 "$4}'
curl -o /dev/null -X GET -s -w %{http_code}:%{time_connect}:%{time_starttransfer}:%{time_total} "${ATLAS_ROOT_URL}/experiments/E-GEOD-30352.tsv" | awk -F":" '{print "/experiments/E-GEOD-30352.tsv      "$1"          "$2"           "$3"                 "$4}'
curl -o /dev/null -X GET -s -w %{http_code}:%{time_connect}:%{time_starttransfer}:%{time_total} "${ATLAS_ROOT_URL}/experiments" | awk -F":" '{print "/experiments                       "$1"          "$2"           "$3"                 "$4}'
curl -o /dev/null -X GET -s -w %{http_code}:%{time_connect}:%{time_starttransfer}:%{time_total} "${ATLAS_ROOT_URL}/proteins/ENSP00000355434" | awk -F":" '{print "/proteins/ENSP00000355434          "$1"          "$2"           "$3"                 "$4}'
curl -o /dev/null -X GET -s -w %{http_code}:%{time_connect}:%{time_starttransfer}:%{time_total} "${ATLAS_ROOT_URL}/genes/ENSMUSG00000029816" | awk -F":" '{print "/genes/ENSMUSG00000029816          "$1"          "$2"           "$3"                 "$4}'
curl -o /dev/null -X GET -s -w %{http_code}:%{time_connect}:%{time_starttransfer}:%{time_total} "${ATLAS_ROOT_URL}/genesets/REACT_111102" | awk -F":" '{print "/genesets/REACT_111102             "$1"          "$2"           "$3"                 "$4}'
curl -o /dev/null -X GET -s -w %{http_code}:%{time_connect}:%{time_starttransfer}:%{time_total} "${ATLAS_ROOT_URL}/resources/html/widgettest.html" | awk -F":" '{print "/resources/html/widgettest.html    "$1"          "$2"           "$3"                 "$4}'
echo ""
echo ""
