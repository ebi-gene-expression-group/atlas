#!/bin/bash
# Script to update the DEV Atlas instance withe the content of the STAGING (= 'data production') Atlas instance

# Source script from the same (prod or test) Atlas environment as this script
scriptDir=$(cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )
# source ${scriptDir}/../db/scripts/experiment_loading_routines.sh

IFS="
"

# Load the experiments

load_experiment() {
    expAcc=$1
    isPrivate=$2
    tomcat=$3
    USERID=$4
    PASS=$5
    report=$6
    realUser=$7
    aux="/tmp/${expAcc}.$$.aux"
    echo -e "\nAbout to load experiment $expAcc into $tomcat with private flag: $isPrivate"
    response=`curl -u ${USERID}:${PASS} -o $aux -X GET -s "${tomcat}/admin/importExperiment?accession=$expAcc&private=$isPrivate"`
    grep "Experiment $expAcc loaded" $aux > /dev/null
    if [ $? -ne 0 ]; then
        error=`cat $aux`
        echo "ERROR: REST call: ${tomcat}/admin/importExperiment?accession=$expAcc&private=$isPrivate did not produce a successful response: $error" >> $report
        return 1
    else
        accessKey=`cat $aux | awk '{print $NF}'`
        if [ ! -z "$realUser" ]; then
           curatorInfo="(curated by: $realUser)"
    fi
        echo -e "[LOADED] $tomcat/experiments/$expAcc?accessKey=$accessKey $curatorInfo" >> $report
    fi
    rm -rf $aux
}

# Get experiment accessions from $TOMCAT admin interface, and put them into file $AUX
get_experiments() {
    TOMCAT=$1
    USERID=$2
    PASS=$3
    AUX=$4
    # Retrieve the list of all experiments in DEV
    curl -u ${USERID}:${PASS} -s -o $AUX "${TOMCAT}/admin/listExperiments"
    if [ $? -ne 0 ]; then
       echo "Failed to retrieve ${TOMCAT}/admin/listExperiments"
       rm -rf $AUX
       return 1
    fi
    # Remove superfluous characters
    perl -pi -e 's|},{|\n|g' $AUX
    perl -pi -e 's|^\[\{||g' $AUX
    perl -pi -e 's|^\}\]||g' $AUX
    # Capture experiment accession and private status
    rm -rf ${AUX}.bak
    for l in $(cat $AUX); do
       experiment=`echo $l | awk -F"," '{print $2}' | awk -F":" '{print $2}' | sed 's/"//g'`
       private=`echo $l | awk -F"," '{print $6}' | awk -F":" '{print $2}' | sed 's/"//g'`
       echo -e "${experiment}\t${private}" >> ${AUX}.bak
    done
    sort -k 1 ${AUX}.bak > ${AUX}
}

## Main code starts

if [ $# -ne 5 ]; then
        echo "Usage: $0 ATLAS_EXPS ATLAS_DEV_EXPS PROD_TOMCAT DEV_TOMCAT USERID"
        exit;
fi

ATLAS_EXPS=$1
ATLAS_DEV_EXPS=$2
PROD_TOMCAT=$3
DEV_TOMCAT=$4 
USERID=$5 

today="`eval date +%Y-%m-%d`"
base="/nfs/public/rw/homes/fg_atlas/tmp/atlas3_stage_to_dev.${today}"
log="${base}.log"
aux="${base}.aux"

PASS=`get_curator_pass $USERID`
if [ $? -ne 0 ]; then
    echo "ERROR: Failed to retrieve curator pass" >> $log
    exit 1
fi 

# Sanity tests to check we are in fact copying from prod to test and not the other way round...
echo $ATLAS_EXPS | grep 'experiments$' > /dev/null
if [ $? -ne 0 ]; then
    echo "ERROR: Make sure the first argument is a stage data directory" >> $log
    exit 1
fi 
echo $ATLAS_DEV_EXPS | grep 'experiments_dev$' > /dev/null
if [ $? -ne 0 ]; then
    echo "ERROR: Make sure the second argument is a dev directory" >> $log
    exit 1
fi

# Copy to $ATLAS_DEV_EXPS all files from $ATLAS_EXPS that are newer in source dir compared to target dir
cd $ATLAS_EXPS
find . -xdev -depth -print | cpio -pdm $ATLAS_DEV_EXPS > $log 2>&1

# Now identify all experiments in stage that are not currently in dev, and load them into dev
# Retrieve the list of all experiments in PROD into ${aux}.prod
get_experiments $PROD_TOMCAT $USERID $PASS ${aux}.prod
if [ $? -ne 0 ]; then
    echo "ERROR: Failed to retrieve experiments from $PROD_TOMCAT" >> $log
    exit 1
fi
get_experiments $DEV_TOMCAT $USERID $PASS ${aux}.dev
if [ $? -ne 0 ]; then
    echo "ERROR: Failed to retrieve experiments from $DEV_TOMCAT" >> $log
    exit 1
fi
# Identify experiments that need loading into DEV_TOMCAT
comm -3 ${aux}.dev ${aux}.prod | grep -P '^\t' > ${aux}.new
# Load the new experiments into DEV_TOMCAT
dev_host=`echo $DEV_TOMCAT | awk -F":" '{print $1}'`
for l in $(cat ${aux}.new); do 
    experiment=`echo $l | awk -F"\t" '{print $2}'`
    private=`echo $l | awk -F"\t" '{print $3}'`
    load_experiment $experiment $private $DEV_TOMCAT $USERID $PASS
    if [ $? -ne 0 ]; then
	echo "ERROR: FAiled to load $exp $DEV_TOMCAT" >> $log
	exit 1
    fi 
done

# Tidy up auxiliary files
rm ${aux}.*



