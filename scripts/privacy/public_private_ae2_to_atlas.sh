#!/bin/bash
# @author: rpetry
# @date:   7 Aug 2013§

# This script serves to reflect in Atlas private/public experiment status switch on the AE2 side.
IFS="
"

if [ $# -ne 5 ]; then
        echo "Usage: $0 ATLAS_URL AE2_URL ATLAS_ADMIN_UID ATLAS_ADMIN_PASS ERROR_NOTIFICATION_EMAILADDRESS"
        echo "e.g. $0 www-test.ebi.ac.uk/gxa peach:8480 rpetry@ebi.ac.uk"
        exit 1;
fi

ATLAS_URL=$1
AE2_URL=$2
ATLAS_ADMIN_UID=$3
ATLAS_ADMIN_PASS=$4
ERROR_NOTIFICATION_EMAILADDRESS=$5

SUCCESS_HTTP_RESPONSE=200
process_file="/tmp/publicprivate_ae2_to_atlas."`eval date +%Y%m%d`
all_atlas_experiments_file=$process_file.all_atlas_exps
all_ae2_experiments_file=$process_file.all_ae2_exps
exps_not_in_ae2=$process_file.$$.exps_not_in_ae2

# Remove any previous $process_file
rm -rf $process_file
curl -u ${ATLAS_ADMIN_UID}:${ATLAS_ADMIN_PASS} -X GET -s -o $all_atlas_experiments_file "$ATLAS_URL/admin/listExperiments"
perl -pi -e 's|{|\n{|g' $all_atlas_experiments_file
num_all_atlas_exps=`wc -l $all_atlas_experiments_file | awk '{print $1}'`
echo "Found $num_all_atlas_exps experiments in $ATLAS_URL. Processing..."  > $process_file.log

# Retrieve all AE2 experiments into $all_ae2_experiments_file (ssv)
curl -X GET -s "http://${AE2_URL}/api/privacy.txt" > $all_ae2_experiments_file
if [ ! -f $all_ae2_experiments_file ]; then
   err_msg="Updating private/public status of experiments on ${ATLAS_URL} was unsuccessful due failure to retrieve all AE2 experiments"
   echo $err_msg >> $process_file.log
   mailx -s "[gxa/cron] $err_msg" ${ERROR_NOTIFICATION_EMAILADDRESS} < $process_file.log
fi

for l in $(cat "$all_atlas_experiments_file" | grep 'experimentAccession'); do
    exp_accession=`echo $l | awk -F":" '{print $3}' | awk -F"," '{print $1}' | sed 's/"//g'`
    atlas_private_flag=`echo $l | awk -F":" '{print $NF}' | awk -F"}" '{print $1}'`
    if [ ! -z $exp_accession ]; then
          # Now get the experiment's private/public status in AE2
          # E.g. line in $all_ae2_experiments_file: accession:E-MEXP-31 privacy:public releasedate:2004-03-01
          ae2_experiment=`grep -P "accession:$exp_accession\t" $all_ae2_experiments_file`
          if [ ! -z "$ae2_experiment" ]; then
               ae2_public_status=`echo $ae2_experiment | grep -Po 'privacy:public'`
               ae2_private_status=`echo $ae2_experiment | grep -Po 'privacy:private'`
               if [ ! -z $ae2_public_status ]; then
                  if [ $atlas_private_flag == "true" ]; then
                       # Experiment public in AE2 and private in Atlas - make it public in Atlas
                       echo "$exp_accession - AE2: public; Atlas: private - status change in Atlas: private->public"  >> $process_file.log
		       curl -u ${ATLAS_ADMIN_UID}:${ATLAS_ADMIN_PASS} -X GET -s -w %{http_code} "http://${ATLAS_URL}/admin/updateExperiment?accession=${exp_accession}&private=false" >> $process_file.log
		       # At this point, the last line of $process_file.log should contain something like 'Experiment E-GEOD-10406 successfully updated.200'
		       # where 200 is the http response code - fail if the response code is not $SUCCESS_HTTP_RESPONSE
		       httpCode=`tail -1 $process_file.log | awk -F"." '{print $NF}'`
		       if [ "$httpCode" -ne "$SUCCESS_HTTP_RESPONSE" ]; then
			  err_msg="http://${ATLAS_URL}/admin/updateExperiment?accession=${exp_accession}&private=false returned non-success http code: $httpCode. Failing..." >> $process_file.log
			  echo $err_msg >> $process_file.log
			  mailx -s "[gxa/cron] $err_msg" ${ERROR_NOTIFICATION_EMAILADDRESS} < $process_file.log
			  exit 1
		       else
			  # Make the experiment's public ftp directory readable by public
			  chmod 755 /ebi/ftp/pub/databases/microarray/data/atlas/experiments/$exp_accession
	               fi
                  fi
               elif [ ! -z $ae2_private_status ]; then
                   if [ $atlas_private_flag == "false" ]; then
                      # Experiment private in AE2 and public in Atlas - make it private in Atlas
                      echo "$exp_accession - AE2: private; Atlas: public - status change in Atlas: public->private" >> $process_file.log
		      curl -u ${ATLAS_ADMIN_UID}:${ATLAS_ADMIN_PASS} -X GET -s -w %{http_code} "http://${ATLAS_URL}/admin/updateExperiment?accession=${exp_accession}&private=true" >> $process_file.log
		      httpCode=`tail -1 $process_file.log | awk -F"." '{print $NF}'`
		      if [ "$httpCode" -ne "$SUCCESS_HTTP_RESPONSE" ]; then
		         err_msg="http://${ATLAS_URL}/admin/updateExperiment?accession=${exp_accession}&private=false returned non-success http code: $httpCode. Failing..." >> $process_file.log
		         echo $err_msg >> $process_file.log
		         mailx -s "[gxa/cron] $err_msg" ${ERROR_NOTIFICATION_EMAILADDRESS} < $process_file.log
		         exit 1
                      else
		         # Make the experiment's public ftp directory unreadable by public
			 chmod 750 /ebi/ftp/pub/databases/microarray/data/atlas/experiments/$exp_accession
		      fi		  
                   fi
               fi
          else
             err_msg="Updating private/public status of experiments on ${ATLAS_URL} unsuccessful: failed to find $exp_accession in AE2"
             echo $err_msg >> $process_file.log
             echo "$exp_accession" >>  $exps_not_in_ae2
	     exit 1
          fi
     else
          err_msg="Updating private/public status of experiments on ${ATLAS_URL} failed due to incorrect format of Atlas API call output"
          echo $err_msg >> $process_file.log
          mailx -s "[gxa/cron] $err_msg" ${ERROR_NOTIFICATION_EMAILADDRESS} < $process_file.log
          exit 1
     fi
done

# Notify of any experiments in Atlas but not in AE2
if [ -e $exps_not_in_ae2 ]; then
   mailx -s "[gxa/cron] Experiments in Atlas but not in AE2" ${ERROR_NOTIFICATION_EMAILADDRESS} < $exps_not_in_ae2
fi

echo "Processed $num_all_atlas_exps experiments successfully" >> $process_file.log

# Remove auxiliary file created by this script
rm -rf $all_atlas_experiments_file
rm -rf $all_ae2_experiments_file
rm -rf $exps_not_in_ae2
