#!/bin/bash
# This script should be run from the same directory in which it exists
# It downloads all the required fastq files for the experiment
# Author: Robert Petryszak

if [ $# -lt 2 ]; then
  echo "Please provide ArrayExpress experiment's sdrf file location, processing directory (and optionally pe/se - to indicate the required libraries (paired-end or single-end only respectively). If the last argument is not specified, all libraries will be fetched."
  echo "Example usage:"
  echo "$0 $EREAP_CLONE/processing E-GEOD-26284.sdrf.txt pe"
  exit 1
fi 
  
PROCESSING_DIR=$1 
PATH_TO_SDRF=$2
LIBS=$3

# Remove auxiliary files  
rm -rf *.aux

EXP_ACC=`echo $PATH_TO_SDRF | awk -F"." '{print $1}' | awk -F"/" '{ print $NF }'`

# Find out all organisms in the experiment (from Characteristics[Organism] column)
IFS='	' read -a header_arr <<< "`head -1 $PATH_TO_SDRF`"; IFS=
i=0
for h in "${header_arr[@]}"; do
    i=$[$i+1]
    if [ `echo "$header_arr[$h]" | grep "Organism]"` ]; then
	awk 'NR != 1 { print }' $PATH_TO_SDRF | awk -F"\t" -v var=$i '{print $var}' >> ${EXP_ACC}_organisms.aux
    fi
done
cat ${EXP_ACC}_organisms.aux | sort | uniq > ${EXP_ACC}_unique_organisms.aux

while read o; do
    echo "Processing ${o}..."
    # Create organism directory name
    organism=`echo $o | python -c "print raw_input().lower().replace (' ', '_')"`

    # Create organism directory if it doesn't already exist    
    mkdir -p "$EREAP_DIR/../ereap_data/$organism"

    # Create experiment-organism directory in $PROCESSING_DIR (if it doesn't already exist)
    mkdir -p "$PROCESSING_DIR/${EXP_ACC}_$organism"

    # Remove organism-specific auxiliary files                                                                                                                                                                                                                          
    rm -rf $organism/*.aux
    rm -rf $PROCESSING_DIR/${EXP_ACC}_$organism/${EXP_ACC}_${organism}.filter.txt

    # If symbolic link $organism does not exist in the current dir, create it and point it at $EREAP_DIR/../ereap_data/$organism
    if [ ! -L $organism ]; then
	ln -s $EREAP_DIR/../ereap_data/$organism $organism
    fi

    # Assemble fastq file paths across all columns matching FASTQ_URI into a list of unique fastq files.    
    # Currently known cases: Comment[FASTQ_URI], Comment [FASTQ_URI], Comment [FASTQ_URI_1] and Comment [FASTQ_URI_2] 
    IFS='	' read -a header_arr <<< "`head -1 $PATH_TO_SDRF`"; IFS=
    i=0
    for h in "${header_arr[@]}"; do
	i=$[$i+1]
	if [ `echo "$header_arr[$h]" | grep "FASTQ_URI"` ]; then
            echo "Creating ${organism}/${EXP_ACC}_fastq.aux" 

	    ##Original command line
	    #awk 'NR != 1 { print }' $PATH_TO_SDRF | grep "$o" | awk -F"\t" -v var=$i '{print $var}' >> ${organism}/${EXP_ACC}_fastq.aux
	    ##Print same $var but w/o the gzip extension in the file name
	    awk 'NR != 1 { print }' $PATH_TO_SDRF | grep "$o" | awk -F"\t" -v var=$i '{ gsub(/.gz/,"",$var); print $var}' >> ${organism}/${EXP_ACC}_fastq.aux		
	fi
    done
  
    # Make sure fastq files are unique (and remove empty lines)
    cat ${organism}/${EXP_ACC}_fastq.aux | sort | uniq | tr -d ' ' | sed '/^$/d' > ${organism}/${EXP_ACC}_fastq_uniq.aux



    # Filter files if pe/se option was chosen
    sample_size=1000000
    if [ "$LIBS" = "pe" ]; then
	echo 'Filter (pe)'
	cat ${organism}/${EXP_ACC}_fastq_uniq.aux | grep -P '\d.fastq' > ${organism}/${EXP_ACC}_fastq_pe.aux 

	# Retrieve file name stems for all PE libraries - will need them for fastq_randsample call
	cat ${organism}/${EXP_ACC}_fastq_pe.aux | awk -F"/" '{print $NF}' | awk -F"_" '{print $1}' | sort | uniq >  ${organism}/${EXP_ACC}_fastq_pe_stems.aux 
  
    elif [ "$LIBS" = "se" ]; then
	echo 'Filter (se)'
	cat ${organism}/${EXP_ACC}_fastq_uniq.aux | egrep -v '\_1.fastq' | egrep -v '\_2.fastq' > ${organism}/${EXP_ACC}_fastq_se.aux

    else
	echo 'Filter (all)' 
	cat ${organism}/${EXP_ACC}_fastq_uniq.aux | grep -P '_\d.fastq' > ${organism}/${EXP_ACC}_fastq_pe.aux 
	# Retrieve file name stems for all PE libraries - will need them for fastq_randsample call
	cat ${organism}/${EXP_ACC}_fastq_pe.aux | awk -F"/" '{print $NF}' | awk -F"_" '{print $1}' | sort | uniq >  ${organism}/${EXP_ACC}_fastq_pe_stems.aux 
	cat ${organism}/${EXP_ACC}_fastq_uniq.aux | egrep -v '\_1.fastq' | egrep -v '\_2.fastq' > ${organism}/${EXP_ACC}_fastq_se.aux
    fi 

    # Fetch all the fastq files via lsf, then generate a sample file containing 1M reads from each fastq file
    pushd ${organism} 
    IFS="
"

    # Generate soft links and sample fastq files of size: $sample_size for any SE libraries
    if [ -e ${EXP_ACC}_fastq_se.aux ]; then
	echo 'Soft links and sampling (se)'
	for remote_fastq in $(cat ${EXP_ACC}_fastq_se.aux | sed 's|.fastq$|.fastq.gz|' | sed 's|ftp://ftp.sra.ebi.ac.uk|/nfs/era-pub|'); do
	    fastq=`echo $remote_fastq | awk -F"/" '{print $NF}' | awk -F"." '{print $1}'`
	    # Append the current library to the filter file that will be used to generate the config file for ${EXP_ACC}_$organism analysis
	    echo $fastq >> $PROCESSING_DIR/${EXP_ACC}_$organism/${EXP_ACC}_${organism}.filter.txt
	
	    # Pipes because depends on previous command's output 
	    bsub -q research-rh6 -cwd `pwd` -o ${EXP_ACC}_${fastq}.out.aux -e ${EXP_ACC}_${fastq}.err.aux "ln -s $remote_fastq ${fastq}.fastq.gz && gunzip -c ${fastq}.fastq.gz > ${fastq}.fastq && fastq_randsample -1 ${fastq}.fastq -n $sample_size -o sample.${fastq} -x && rm -rf ${fastq}.fastq | $EREAP_CLONE/scripts/Fastq_QualityEncoding.pl -f sample.${fastq}.fastq -verbose 0 > ${fastq}.encodqual.log"
	done
    fi
    
    # Generate soft links for any PE libraries
    if [ -e ${EXP_ACC}_fastq_pe.aux ]; then
	echo 'Soft links and sampling (pe)' 
	for remote_fastq in $(cat ${EXP_ACC}_fastq_pe.aux | sed 's|.fastq$|.fastq.gz|' | sed 's|ftp://ftp.sra.ebi.ac.uk|/nfs/era-pub|'); do
	    fastq=`echo $remote_fastq | awk -F"/" '{print $NF}' | awk -F"." '{print $1}'`
	    ln -s $remote_fastq ${fastq}.fastq.gz
	done
    fi

    # Generate sample fastq files of size: $sample_size for any PE libraries
    if [ -e ${EXP_ACC}_fastq_pe_stems.aux ]; then
	for pe_fastq_stem in $(cat ${EXP_ACC}_fastq_pe_stems.aux); do
	    # Append the current library to the filter file that will be used to generate the config file for ${EXP_ACC}_$organism analysis
            echo $pe_fastq_stem >> $PROCESSING_DIR/${EXP_ACC}_$organism/${EXP_ACC}_${organism}.filter.txt

            # Pipes because depends on previous command's output 
	    bsub -q research-rh6 -cwd `pwd` -o ${EXP_ACC}_${pe_fastq_stem}.out.aux -e ${EXP_ACC}_${pe_fastq_stem}.err.aux "gunzip -c ${pe_fastq_stem}_1.fastq.gz > ${pe_fastq_stem}_1.fastq && gunzip -c ${pe_fastq_stem}_2.fastq.gz > ${pe_fastq_stem}_2.fastq && fastq_randsample -1 ${pe_fastq_stem}_1.fastq -2 ${pe_fastq_stem}_2.fastq -n $sample_size -o sample.${pe_fastq_stem} -x  && rm -rf ${pe_fastq_stem}_1.fastq && rm -rf ${pe_fastq_stem}_2.fastq | $EREAP_CLONE/scripts/Fastq_QualityEncoding.pl -f $EREAP_CLONE/data/raw_data/homo_sapiens/sample.${pe_fastq_stem}_1.fastq -verbose 0 > ${pe_fastq_stem}_1.encodqual.log && $EREAP_CLONE/scripts/Fastq_QualityEncoding.pl -f $EREAP_CLONE/data/raw_data/homo_sapiens/sample.${pe_fastq_stem}_2.fastq -verbose 0 > ${pe_fastq_stem}_2.encodqual.log"
	done
    fi

    IFS=

    popd
done < ${EXP_ACC}_unique_organisms.aux

exit 0
