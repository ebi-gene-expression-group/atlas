#!/bin/bash
# @author: omannnion
# @date:   10 Jan 2014

if [ $# -lt 1 ]; then
        echo "Accesses the json experiments URL, which on the first access after application startup will load all experiments into the experiments cache."
        echo "First access will take around 15 minutes, subsequent accesses will take between 1 and 2 seconds"
        echo
        echo "Usage: $0 HOSTNAME"
        exit;
fi

HOSTNAME=$1
PORT=8080
ATLAS_ROOT_URL="http://${HOSTNAME}:${PORT}/gxa"
EXPERIMENTS=${ATLAS_ROOT_URL}/json/experiments

echo ${EXPERIMENTS}
curl -s -o /dev/null -w "HTTP status code: %{http_code} Size: %{size_download} Time: %{time_total}\n" ${EXPERIMENTS}
