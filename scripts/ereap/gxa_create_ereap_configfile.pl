#!/usr/bin/perl

# POD documentation - main docs before the code
=pod

=head1 NAME
  Karyn Megy - 31-August-12
  kmegy@ebi.ac.uk
  gxa_create_ereap_configfile.pl
  Modified by Robert Petryszak (petry@ebi.ac.uk)

=head1 SYNOPSIS
  Generate the config file from a *sdrf file (ArrayExpress format)
  ... also possibility to set up the working directory structure required by ereap
  ... and to parse selected experiment only (e.g. PE or SE only)

=head1 DESCRIPTION
   Generate the config file from a *sdrf file (ArrayExpress format)
  ... also possibility to set up the working directory structure required by ereap
  ... and to parse selected experiment only (e.g. PE or SE only)
                                                                                                                                 
=head1 OPTIONS
  - generate the config file (-conf, default: YES)
  - PE or SE experiments only (-read, def.: ALL)

=head1 FUTURE DEVELOPMENTS
  1- Have options to:
    -- generate the config file only (currently only option)
    AND / OR
    -- build the directory structure (data/raw_data/SPECIES etc.)
    -- and generate symbolic links
    AND / OR
    -- deal with PE or SE only
    AND / OR
    -- allow for some runs to be excluded

 2- Improvements: 
    - use gxa_fastq_qualityencoding.pl as you cannot trust the SDRF file (fastq files might be ENA modified)
    - use gxa_fastq_readlength.pl to get the read length?? 

head1 EXAMPLES
  gxa_create_ereap_configfile.pl -f file.sdrf.txt -xml file-configuration.xml -d data_directory -filter filter_file -rel ensembl_release -mapper tophat1 -quant_method cufflinks1_nd [-read PE] [-calc_rs Y]

=cut

use strict ;
use Getopt::Long ;
use File::Basename;
use List::Util qw(first);
use XML::Simple qw(:strict); # calling strict 'locally' to get more detailled error messages

## Initialise $, @ and %
my ($help, $sdrf, $xml, $data_dir, $dna_file, $gtf_file, $ensembl_release, $required_libs_file, $species) ;
my (%H_assaygrp, %H_contr) ; #XML parsing
my $config = "Y" ;
my $read = "ALL" ;
my $calc_rs = "N";
my ($mapper, $quant_method, $de_method) ;
my $qual_error ; #store error messages in the quality encoding generation

my $required_libs; # TODO populate from an external file provided at run time

my %H_Config_info ;
my @command_args ;

# Hashmap to store SDRF column indexes for various values needed to ereap configuration
my %SDRF_Col_info;


######################
## Get arguments
GetOptions('help' => \$help,
           'Help' => \$help,
           'h'    => \$help,
           'H'    => \$help,
           'sdrf=s'  => \$sdrf,
           'xml=s'  => \$xml,
           'd=s'  => \$data_dir,
           'rel=s' =>  \$ensembl_release,
           'filter=s' =>  \$required_libs_file,
           'conf=s'  => \$config,
           'read=s'  => \$read,
           'mapper=s'  => \$mapper,
           'quant_method=s'  => \$quant_method,
	   'de_method=s' => \$de_method,
           'calc_rs=s' => \$calc_rs,
	   
          ) or usage(\@command_args) ;

if ($help) { usage(\@command_args) ; }

if (!$sdrf || !$data_dir || !$ensembl_release || !$mapper || !$quant_method) { usage(\@command_args) ; die "Missing -sdrf, -d, rel, mapper or quant_method\n" ; }
#xml and de_method are not compulsory - only for differential experiment

if ($read ne "ALL" && $read ne "PE" && $read ne "SE") { print STDERR "!!! WARNING !!! Incorrect value for -read ! Should be PE, SE or ALL !!!\n\n" ; usage(\@command_args) ; exit ;}
if ($config ne "Y" && $config ne "N") { print STDERR "!!! WARNING !!! Incorrect value for -conf ! Should be Y or N !!!\n\n" ; usage(\@command_args) ; exit ;}
if ($calc_rs ne "Y" && $calc_rs ne "N") { print STDERR "!!! WARNING !!! Incorrect value for -calc_rs ! Should be Y or N !!!\n\n" ; usage(\@command_args) ; exit ;}

if ($required_libs_file) {
    $required_libs = `cat $required_libs_file`;
    $required_libs =~ s/\n/ /g;
}

#Analysis name - based on input file name
my $name = $sdrf ;
#If file name contains path
if ($sdrf =~ /\/{1,}.+\/(.+?)\./) { $name = $1 ; }
elsif ( $sdrf =~ /^(.+?)\./) { $name = $1 ; }

# Specify SDRF required columns;
my $fastq_uri_col = "[FASTQ_URI]";
my $fastq_uri_col1 = "[FASTQ_URI_1]";
my $fastq_uri_col2 = "[FASTQ_URI_2]";
my $organism_name_col = "[Organism]";
# Either of the two columns below (if present) may contain information about whether a library is paired or single
my $library_layout_col = "[LIBRARY_LAYOUT]";
my $ena_run_col = "[ENA_RUN]";
my $quality_encoding_col = "[quality_encoding]";
my $read_size_col = "[SPOT_LENGTH]";
my $read_size_col1 = "[SEQUENCE_LENGTH]";
my $read_size_col2 = "[CYCLE_COUNT]";
my ($rs1, $rs2, $rs3);
my $pairsingle ; #needed in XML parsing, so making it global


######################
## Parse the config file
# Config file should be in sdrf format (ArrayExpress format)
open (F, $sdrf) || die "Can't open SDRF file to parse $sdrf\n" ;
while (my $line=<F>) {
    chomp $line ;
    my @A_line = split("\t", $line) ;
    if ($line =~ /^Source Name/) {
        # Find indexes of all required SDRF columns
        $SDRF_Col_info{$fastq_uri_col} = first { $A_line[$_] eq "Comment$fastq_uri_col"} 0..$#A_line;
        $SDRF_Col_info{$fastq_uri_col1} = first { $A_line[$_] eq "Comment$fastq_uri_col1"} 0..$#A_line;
        $SDRF_Col_info{$fastq_uri_col2} = first { $A_line[$_] eq "Comment$fastq_uri_col2"} 0..$#A_line;
        $SDRF_Col_info{$organism_name_col} = first { $A_line[$_] eq "Characteristics$organism_name_col" } 0..$#A_line;
        $SDRF_Col_info{$library_layout_col} = first { $A_line[$_] eq "Comment$library_layout_col" } 0..$#A_line;
        $SDRF_Col_info{$ena_run_col} = first { $A_line[$_] eq "Comment$ena_run_col" } 0..$#A_line;
        $SDRF_Col_info{$quality_encoding_col} = first { $A_line[$_] eq "Comment$quality_encoding_col" } 0..$#A_line;
        $SDRF_Col_info{$read_size_col} = first { $A_line[$_] eq "Comment$read_size_col" } 0..$#A_line;
        $rs1 = first { $A_line[$_] eq "Comment$read_size_col" } 0..$#A_line;
        $rs2 = first { $A_line[$_] eq "Comment$read_size_col1" } 0..$#A_line;
        $rs3 = first { $A_line[$_] eq "Comment$read_size_col2" } 0..$#A_line;
        if ($rs1) { $SDRF_Col_info{$read_size_col} = $rs1; }
        elsif ($rs2) { $SDRF_Col_info{$read_size_col} = $rs2; }
        elsif ($rs3) { $SDRF_Col_info{$read_size_col} = $rs3; }
        for (keys %SDRF_Col_info){
            if (&empty_test($SDRF_Col_info{$_}) < 1) {
                # Sometimes column headings start with 'Comment[' and sometimes with 'Comment ['
                my $col_name = $_;
                if ($_ =~ /Organism/) {
                    $SDRF_Col_info{$_} = first { $A_line[$_] eq "Characteristics $col_name"} 0..$#A_line;
                } else {
                    $SDRF_Col_info{$_} = first { $A_line[$_] eq "Comment $col_name"} 0..$#A_line;
                }

                if (&empty_test($SDRF_Col_info{$_}) < 1) {
                    print STDERR "WARNING: Column: $_ could not be found in file: $sdrf\n";
                    $col_name = quotemeta "$_";
                    # Exit only for essential columns
                    if (grep( /^$col_name$/, ($organism_name_col, $ena_run_col ))) {
                        exit 1;
                    }
                    if (grep( /^$col_name$/, ($fastq_uri_col, $library_layout_col ))) {
                        if (grep( /^$col_name$/, ($fastq_uri_col1 )) || grep( /^$col_name$/, ($fastq_uri_col2 ))) {
                              exit 1;
                        }
                    }
                }
            }
        }
    } else {

	##Populate the various values
	#Fastq file name (minus path)
	my $fastq;
	my $fastq2;
	if (&empty_test($SDRF_Col_info{$fastq_uri_col}) == 1) {
	   $fastq = basename($A_line[$SDRF_Col_info{$fastq_uri_col}]);
	} else {
	    # In some experiments (e.g. E-MTAB-513) fastq uri two mates in paired-end library are in two separate lines in one column 'Comment[FASTQ_URI]'
	    # In other experiments however (e.g. E-GEOD-26284, they are included in one line and two columns: 'Comment [FASTQ_URI_1]' and 'Comment [FASTQ_URI_2]'
	    $fastq = basename($A_line[$SDRF_Col_info{$fastq_uri_col1}]);
	    $fastq2 = basename($A_line[$SDRF_Col_info{$fastq_uri_col2}]);
	    # fastq files in ENA are always gzipped - so if sdrf does not reflect that - fix it
	    if ($fastq2 !~ /.gz$/) {
		$fastq2 .= ".gz";
	    }
	}
	# fastq files in ENA are always gzipped - so if sdrf does not reflect that - fix it
	if ($fastq !~ /.gz$/) {
	    $fastq .= ".gz";	}
       
	$pairsingle = "se"; # default as $library_layout_col can sometimes be missing
	if (&empty_test($SDRF_Col_info{$library_layout_col}) == 1) {
	    $pairsingle = $A_line[$SDRF_Col_info{$library_layout_col}] ;
	    $pairsingle =~ s/PAIRED/pe/ ; $pairsingle =~ s/SINGLE/se/ ;
	} elsif (&empty_test($SDRF_Col_info{$fastq_uri_col1}) == 1 && 
		 &empty_test($SDRF_Col_info{$fastq_uri_col1}) == 1 && 
		 trim($A_line[$SDRF_Col_info{$fastq_uri_col1}]) && 
		 trim($A_line[$SDRF_Col_info{$fastq_uri_col2}])) {
	    $pairsingle = "pe" ;
	}
	my $run_name = $A_line[$SDRF_Col_info{$ena_run_col}] ; #name for the experiment
	my $run_name_store = $run_name."_".$pairsingle ;
	my $ins;
	my $sd;
	my $numAlignmentsComment;

	#Does the library qualify? 
	if (!library_qualifies($run_name, $pairsingle, $read, $required_libs)) { 
	    print STDERR "LIBRARY DOESN't QUALIFY! Testing: [$required_libs =~ /$run_name/ OR $required_libs empty] AND [$read eq ALL OR uc($pairsingle) eq $read]\n" ;
	}

        if (library_qualifies($run_name, $pairsingle, $read, $required_libs)) {
	    #Species name
	    my $tmp_species = $A_line[$SDRF_Col_info{$organism_name_col}] ;
	    $tmp_species = lc($tmp_species) ;
       	    if ($species && $species != $tmp_species) {
	        print STDERR "Multiple species detected in experiment: '$tmp_species' and '$species' - only one species is allowed.";
	        exit 1;
	    }
	    $species = $tmp_species;
	    my $nb = $species =~ s/ /_/ ;

	    #If >1 species, print a warning
	    #(assuming: space, comma, semi-colon separated)
	    if ($species =~ /[,;]/) { print STDERR "WARNING! Species name contains non-alpha characters, there might be more than one species name.\n" ; }
	    if ($nb > 1) { print STDERR "WARNING! More than one space in the species name, there might more than one species name.\n" ; }

	    $dna_file=`ls $data_dir/reference/$species/*.${ensembl_release}.dna.toplevel.fa.gz | awk -F \"/\" '{print \$NF}' \| tr -d '\n'`;
	    $gtf_file=`ls $data_dir/reference/$species/*.${ensembl_release}.gtf.gz | awk -F \"/\" '{print \$NF}' \| tr -d '\n'`;
        }

	if ($pairsingle eq "pe" && library_qualifies($run_name, $pairsingle, $read, $required_libs))  {
	    # This comment stores a log of the number of alignments produced in the mapping of the random sample
	    # of the fastq file against the transcriptome reference.
	    # Typically it should be, say, over 10% of the sample size. If it (much) less, some problem in the mapping
	    # or the sample data may exist that needs further investigation.
	    $numAlignmentsComment = `grep '#Alignments:' $data_dir/raw_data/$species/ins_sd_sampling/ins_sd_${run_name_store}.out | tr -d '\n'` ;

	    my $ins_sd_line = `grep 'Avg. insert size' $data_dir/raw_data/$species/ins_sd_sampling/ins_sd_${run_name_store}.out` ;
	    $ins =`echo \"$ins_sd_line\" \| awk -F\":\" '{print \$2}' \| awk '{print \$1}' \| tr -d '\n'`;
	    $sd = `echo \"$ins_sd_line\" \| awk -F\":\" '{print \$3}' \| tr -d '\n'`;

	    if (!$ins || $ins !~ /^[0-9]+$/ || !$sd || $sd !~ /^[0-9]+$/) {
		print STDERR "Insert size/std deviation was not generated correctly for $run_name_store: ins='$ins' sd='$sd'" ;
		exit 1;
	    }
	}

	my $encoding = $A_line[$SDRF_Col_info{$quality_encoding_col}] ;
	#Sample quality score from *encodqual* pre-computed files
	# ... the pre-compute happened as part of the fastq download (downlaod_fastq.sh) 
	# N.B. In the case of sdrf containing both mates of the paired-end library in one line, the quality
	# encoding of both mates is assumed to be the same - that of the first mate ($fastq)
	my $qual;
	my $mate = "";
	if (library_qualifies($run_name, $pairsingle, $read, $required_libs)) {
	    $qual = 33; # default quality encoding
	    if ($pairsingle eq "pe") {
		$mate = "_1"; # For paired-end library, work out quality encoding from just of the mates
	    }

	    my $ls_encodqual = `ls $data_dir/raw_data/$species/*encodqual* | awk -F \"/\" '{print \$NF}'` ;
	    my (@ls_encodqual) = split ("\n",$ls_encodqual) ; 

	    #Read each files - contains Q33/Q64 or ERROR message?
	    for my $a (0..$#ls_encodqual) {	
		my $encod_qual = `cat $data_dir/raw_data/$species/$ls_encodqual[$a]` ;
		chomp $encod_qual;
		if ($encod_qual =~ /^[3364]{2,}$/) { $qual = $encod_qual; }
		else {
			if ($encod_qual =~ /^(.+?)\n/) { $qual="$ls_encodqual[$a] $1" ; }
			else { $qual="$ls_encodqual[$a] $encod_qual" ; }
			$qual_error .= "$qual\n" ;
		}
	    }
	}

        #Read length
        my $rs;
        if (library_qualifies($run_name, $pairsingle, $read, $required_libs)) {
            if ($calc_rs eq "Y") {
                 #The file from which we're obtaining read length will have been created by download_fastq.sh. that as a by-product generated samples of fastq files
                 $rs = `grep 'Min:' $data_dir/raw_data/$species/${name}_${run_name}.err.aux | awk '{print \$2}' | tr -d '\n'`;
            } else {
                 $rs = $A_line[$SDRF_Col_info{$read_size_col}];
                 if ($pairsingle eq "pe") { $rs = $rs/2; }
            }
                 
            if ($rs !~ /^[0-9]+$/ ) {
                 print STDERR "Non-numeric read length, '$rs', reported in: $data_dir/raw_data/$species/${name}_${run_name}.err.aux ";
                 exit 1;
	    }
	}


	#Are all values populated?
	#print "[INFO] Testing for empty values\n" ;
	if (library_qualifies($run_name, $pairsingle, $read, $required_libs)) {
	    my $empty_value = "" ;
	    if (&empty_test($name) < 1) { $empty_value .= "analysis name," ; }
	    if (&empty_test($mapper) < 1) { $empty_value .= "mapper," ; }
	    if (&empty_test($quant_method) < 1) { $empty_value .= "quant_method," ; }
	    if (&empty_test($fastq) < 1) { $empty_value .= "fastq," ; }
	    if ($SDRF_Col_info{$fastq_uri_col2} && &empty_test($fastq2) < 1) { $empty_value .= "fastq2," ; }
	    if (&empty_test($species) < 1) { $empty_value .= "species," ; }
	    if (&empty_test($pairsingle) < 1) { $empty_value .= "pair or single end read information (pe and se)," ; }
	    if (&empty_test($run_name) < 1) { $empty_value .= "run name," ; }
	    if (&empty_test($qual) < 1) { $empty_value .= "quality score encoding (_qual)," ; }
	    if (&empty_test($rs) < 1) { $empty_value .= "read size (_rs)," ; }
	    if ($pairsingle eq "pe") {
		if (&empty_test($ins) < 1) { $empty_value .= "insert size (_ins)," ; }
		if (&empty_test($sd) < 1) { $empty_value .= "standard deviation (_sd)," ; }
	    }
	    chop $empty_value ;
	    if ($empty_value ne "") {print STDERR "ERROR: No value found for $empty_value\n" ; exit 1}
	}

	#Extra check if paired end - and store the additional fastq file name
	if ( ($pairsingle eq "pe") && library_qualifies($run_name, $pairsingle, $read, $required_libs) && (exists $H_Config_info{$species}{$pairsingle}{$run_name_store}{"FASTQ"})) {
	    if ($H_Config_info{$species}{$pairsingle}{$run_name_store}{"READ_SIZE"} ne $rs) { print STDERR "Warning! Paired end read file and different READ SIZE! Exiting!\n" ; exit ;}
	    if ($H_Config_info{$species}{$pairsingle}{$run_name_store}{"INSERT"} ne $ins) { print STDERR "Warning! Paired end read file and different INSERT SIZE! Exiting!\n" ; exit ;}
	    if ($H_Config_info{$species}{$pairsingle}{$run_name_store}{"QUALITY"} ne $qual) { print STDERR "Warning! Paired end read file and different QUALITY ENCODING! Exiting!\n" ; exit ;}
	    $H_Config_info{$species}{$pairsingle}{$run_name_store}{"FASTQ_2"} = $fastq;
	}
	
	#If all OK and SE or 1st PE, store
	if ( (($pairsingle eq "se") || ($pairsingle eq "pe")) && library_qualifies($run_name, $pairsingle, $read, $required_libs) && (!exists $H_Config_info{$species}{$pairsingle}{$run_name_store}{"FASTQ"})) {
	    $H_Config_info{$species}{$pairsingle}{$run_name_store}{"FASTQ"} = $fastq;
	    $H_Config_info{$species}{$pairsingle}{$run_name_store}{"PESE"} = $pairsingle ;
	    $H_Config_info{$species}{$pairsingle}{$run_name_store}{"RUN"} = $run_name ;
	    $H_Config_info{$species}{$pairsingle}{$run_name_store}{"QUALITY"} = $qual ;
	    $H_Config_info{$species}{$pairsingle}{$run_name_store}{"INSERT"} = $ins ;
	    $H_Config_info{$species}{$pairsingle}{$run_name_store}{"NUM_ALIGNMENTS_COMMENT"} = $numAlignmentsComment ;
	    $H_Config_info{$species}{$pairsingle}{$run_name_store}{"STANDARD_DEV"} = $sd ;
	    $H_Config_info{$species}{$pairsingle}{$run_name_store}{"READ_SIZE"} = $rs ;
	}
    }
}
close F ;


## Print for a test
#foreach my $SpEcIeS (sort keys %H_Config_info) {
#    print "SPECIES: $SpEcIeS\n" ;
#    foreach my $PeSe (sort keys %{$H_Config_info{$SpEcIeS}}) {
#	foreach my $NaMe (sort keys %{$H_Config_info{$SpEcIeS}{$PeSe}}) {
#	    print "RUN: $NaMe\n" ;
#	    print "\tFastq file: $H_Config_info{$SpEcIeS}{$PeSe}{$NaMe}{FASTQ}\n" ;
#	    print "\tPE or SE: $H_Config_info{$SpEcIeS}{$PeSe}{$NaMe}{PESE}\n" ;
#	    print "\tQuality encoding: $H_Config_info{$SpEcIeS}{$PeSe}{$NaMe}{QUALITY}\n" ;
#	    print "\tRead size: $H_Config_info{$SpEcIeS}{$PeSe}{$NaMe}{READ_SIZE}\n" ;
#	    print "\tInsert size: $H_Config_info{$SpEcIeS}{$PeSe}{$NaMe}{INSERT}\n" ;
#	    print "\tSD file: $H_Config_info{$SpEcIeS}{$PeSe}{$NaMe}{STANDARD_DEV}\n" ;	
#	    print "\tNumber of alignments: $H_Config_info{$SpEcIeS}{$PeSe}{$NaMe}{NUM_ALIGNMENTS_COMMENT}\n" ;
#	}
#   }
#}


## Check number of species
# If > 1, then might be issue in determining a reference genome
my $Species_Nb = scalar keys %H_Config_info;
if ($Species_Nb > 1) {
    print "WARNING! $Species_Nb different species! Issues with defining a reference genome.\n" ;
    print "\tSpecies:" ;
    foreach my $SpEcIeS (sort keys %H_Config_info) { print " $SpEcIeS" ; }
    print "\n" ;
}


######################
## Parse the XML file
# ... if exists! Only needed for differential experiments, 
# ... not for baseline experiments!

if (&empty_test($xml) >= 1) {
	open (Fx, $xml) || die "Can't open XML file to parse $xml\n" ;
	my $xml_line = XMLin($xml, ForceArray => ['analytics', 'contrast', 'assay_group'], KeyAttr => { contrast => 'id', assay_group => 'id' });
	foreach my $ana (@{ $xml_line->{ "analytics" } }) {

        	##Assay group
        	my $AssayGp = $ana->{ "assay_groups" }->{ "assay_group" };
        	foreach my $thisAssayGp (keys %{$AssayGp}) {
                	#print "This is assay_group $thisAssayGp\n" ;
                	my @refAGarray = @{ $ana->{ "assay_groups" }->{ "assay_group" }->{ $thisAssayGp }->{ "assay" } };
                	my @AGarray = \@refAGarray ;

                	foreach $a (0..$#AGarray) {
                        	foreach my $b (0..$#{$AGarray[$a]}) {
                                	#print "\tassay: $AGarray[$a][$b]\n" ;  #print
                                	$H_assaygrp{$thisAssayGp}{$AGarray[$a][$b]} = 1 ;  #store
                        	}
                	}
        	}

       		##Contrasts
        	my $Contrasts = $ana->{ "contrasts" }->{ "contrast" };
        	foreach my $thisContrast (keys %{$Contrasts}) {
                	#print "This is contrast $thisContrast\n" ;
                	my $contrast_name = $ana->{ "contrasts" }->{ "contrast" }->{ $thisContrast }->{ "name" } ;
                	#print "\tname: $contrast_name\n" ; #print

			#If contains semi-colon, only store what's on the right
			#Remove quote
			#E.g. clinical_information:'non-pregnant'_vs_'pregnant'
			if ($contrast_name =~ /^.+?:(.+?)$/) { $contrast_name = $1 ; }
			$contrast_name =~ s/\'//g ;
                	$H_contr{$thisContrast} = $contrast_name ; #store
        	}
	}
}
close Fx ;


######################
## Print the config file - format as required
# current format is easy to read, so keep it, 
# require going through hash several times but they are small, so OK.

## Check the QUALITY value doesn't contain any errors
## If so, print them, see Naples, and die.
if ($qual_error ne "" ) { die "ERROR in the QUALITY ENCODING -$qual_error -!\n" ; }

#print "Printing the config file\n" ;
foreach my $SpEcIeS (sort keys %H_Config_info) {
    #Generic data
    print "#Generic configuration\n" ;
    print "name=$name\n" ;   #analysis name
    print "data_dir=$data_dir\n" ;  #data directory location
    
    #Reference data
    print "\n#Reference data\n" ;
    print "species=$SpEcIeS\n" ;  #species
    print "reference=$dna_file\n" ;  #DNA reference
    print "gtf_file=$gtf_file\n" ;  #GTF file

    #Mapper/assembly/differential expression methods config
    print "\n#Mapper/assembly/differential expression methods config\n" ;
    print "mapper=$mapper\n" ;   #mapper
    print "quant_method=$quant_method\n" ;   #assembler
    if (&empty_test($de_method) >= 1) { print "de_method=$de_method\n" ; } #differential expression method, only if given as parameter

    #List libraries
    print "\n#Libraries\n" ;
    foreach my $PeSe (sort keys %{$H_Config_info{$SpEcIeS}}) {
	print "$PeSe=" ;
        foreach my $NaMe (sort keys %{$H_Config_info{$SpEcIeS}{$PeSe}}) { print "$NaMe " ; } 
	print "\n" ;
    }
    
    #List name and file name
    print "\n#Short name & file name\n" ;
    foreach my $PeSe (sort keys %{$H_Config_info{$SpEcIeS}}) {
	foreach my $NaMe (sort keys %{$H_Config_info{$SpEcIeS}{$PeSe}}) {
	    print "$NaMe=$H_Config_info{$SpEcIeS}{$PeSe}{$NaMe}{FASTQ}" ;
	    if ($PeSe eq "pe") { print " $H_Config_info{$SpEcIeS}{$PeSe}{$NaMe}{FASTQ_2}" ; }
	    print "\n" ;
	}
    }
    
    #Print the details for each experiment
    print "\n#Experiment details\n" ;
    foreach my $PeSe (sort keys %{$H_Config_info{$SpEcIeS}}) {
        foreach my $NaMe (sort keys %{$H_Config_info{$SpEcIeS}{$PeSe}}) {
        print "$H_Config_info{$SpEcIeS}{$PeSe}{$NaMe}{NUM_ALIGNMENTS_COMMENT}\n" ;
	    print "$NaMe"."_rs=$H_Config_info{$SpEcIeS}{$PeSe}{$NaMe}{READ_SIZE}\n" ;
	    if ($PeSe eq "pe") { 
		print "$NaMe"."_sd=$H_Config_info{$SpEcIeS}{$PeSe}{$NaMe}{STANDARD_DEV}\n" ;
		print "$NaMe"."_ins=$H_Config_info{$SpEcIeS}{$PeSe}{$NaMe}{INSERT}\n" ;
	    }
	    print "$NaMe"."_qual=$H_Config_info{$SpEcIeS}{$PeSe}{$NaMe}{QUALITY}\n\n" ;		
	}
    }   

    #XML-parsing info.
    if (&empty_test($xml) >= 1) {
    	print "\n#Lane groups\n" ;
    	foreach my $AssGrp (sort keys %H_assaygrp) {
        	print "$AssGrp=" ;
        	foreach my $Assay (sort keys %{$H_assaygrp{$AssGrp}}) {
                	print $Assay."_$pairsingle " ; #$pairsingle being 'se' or 'pe' 
        	} print "\n" ;
    	}

    	print "\n#Contrasts\n" ;
    	my $Con_line ;
    	foreach my $Cont (sort keys %H_contr) {
        	$H_contr{$Cont} =~ s/ /_/g ; print "$H_contr{$Cont}=" ;
        	$Con_line .= "$H_contr{$Cont} " ;
        	$Cont =~ s/_/ /g ; print "$Cont\n" ;
    	}
    	print "contrasts=$Con_line\n" ;
    }
}	


## Subroutines
##############
sub usage {
    my ($command_args) = @_;
    
    print "Command line:\t".
	"gxa_create_ereap_configfile.pl ".join("\t", @$command_args), "\n\n";
    
    print "Program generating the config file for the ereap pipeline and/or the working directory structure\n".

	"\t- Compulsory parameters: -f FILE.sdrf -xml FILE-configuration.xml -d path/to/working/directory \n".
	"\t\t-mapper: mapper to be used, e.g. tophat1\n".
	"\t\t-quant_method: quantification method to be used, e.g. cufflinks1_nd\n".
	"\t\t-rel: Ensembl release number\n".

	"\t- Optional parameters: -xml FILE-configuration.xml -config [Y,N] -dir [Y,N] -read [PE, SE, ALL]\n".
        "\t\t-xml: XML configuration file (differential expriemnt only!)\n".
        "\t\t-de_method (default: none): method for assessing differential expression (differential experiment only!)\n".
	"\t\t-conf (default: Y): generate the config file\n".
	"\t\t-filter: path to the filter file required libraries, one library per line\n".
	"\t\t-calc_rs (default: N): calculate read size from pre-generated samples of fastq files; If N (default), the script will try to find the read size in the sdrf file\n";
}

sub empty_test {
    my $value = $_[0] ;
    my $populated ;
    #print "[SUB] Entering subroutine with -$value-\n" ;

    if (!defined $value) { $populated = 0 ; }
    else { $populated = 1 ; } 
    return $populated ;
}

# Function to remove whitespace from the start and end of the string
sub trim($)
{
    my $string = shift;
    $string =~ s/^\s+//;
    $string =~ s/\s+$//;
    return $string;
}

# Function to decide if the library should be considered or not
sub library_qualifies() {
    my $run_name = $_[0] ;
    my $pairsingle = $_[1] ;
    my $read = $_[2] ; # runtime argument for this script - indicating if what kind of libraries should be included
    my $required_libs = $_[3] ; # Additional filter for libraries to be included, provided to this script as a runtime argument
    #print "[SUB] uc($pairsingle) eq $read; READ:$read;  RUN_NAME: $run_name; REQUIRED_LIB:$required_libs\n" ;	
    return ( (!$required_libs || $required_libs =~ /$run_name/) && ($read eq "ALL" || uc($pairsingle) eq $read));
}
