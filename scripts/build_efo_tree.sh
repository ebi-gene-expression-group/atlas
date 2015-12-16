#!/bin/bash
# @author: amunoz
# @date:   16 Dec 2015

if [ $# -lt 1 ]; then
        echo "Search an EFO term, which on the first access after application startup will load build the EFO tree from efo.owl into memory."
        echo "First access will take around 15 minutes, subsequent accesses will take between 1 and 2 seconds"
        echo
        echo "Usage: $0 HOSTNAME"
        exit;
fi

HOSTNAME=$1
PORT=8080
ATLAS_ROOT_URL="http://${HOSTNAME}:${PORT}/gxa"
EFO_URL=${ATLAS_ROOT_URL}/query?condition=cancer

echo ${EFO_URL}
CURL_OUT=`curl -s -w "\nHTTP status code: %{http_code} Size: %{size_download} Time: %{time_total}\n" ${EFO_URL}`

echo ${CURL_OUT} | grep -q "Expression Atlas results" && echo ${CURL_OUT} | grep -o "HTTP status code: .* Size: .* Time: .*"
