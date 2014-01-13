#!/bin/bash
# @author: omannnion
# @date:   10 Jan 2014

if [ $# -lt 1 ]; then
        echo "Accesses the json experiments URL, which on the first access after application startup will load all experiments into the experiments cache."
        echo "First access will take >30sec, subsequent accesses will take 0.2sec"
        echo
        echo "Usage: $0 HOSTNAME"
        exit;
fi

HOSTNAME=$1
PORT=8080
ATLAS_ROOT_URL="http://${HOSTNAME}:${PORT}/gxa"
EXPERIMENTS=${ATLAS_ROOT_URL}/json/experiments

echo ${EXPERIMENTS}
curl -s -o /dev/null -w "Time: %{time_total} Size: %{size_download}\n" ${EXPERIMENTS}
