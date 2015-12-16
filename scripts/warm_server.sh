#!/usr/bin/env bash
# @author: amunoz
# @date:   16 Dec 2015

if [ $# -lt 1 ]; then
        echo "Warms Atlas server after startup for quick look-up of experiments and EFO terms"
        echo
        echo "Usage: $0 HOSTNAME"
        exit;
fi


function test_command_status {
    $@
    local status=$?
    if [ ${status} -ne 0 ]; then
        echo "Error with $1" >&2
    fi
    return ${status}
}

test_command_status "./warm_experiment_cache.sh $1"
test_command_status "./build_efo_tree.sh $1"
