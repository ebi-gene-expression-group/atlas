#!/usr/bin/perl

use strict;
use warnings;

# Useful modules.
use List::Util qw(sum);
use File::Basename;

# For commandline options
use vars qw/ %opt /;

$| = 1;

# Get the base name of this script.
my $basename = basename($0);

# Print a helpful message and exit if called without arguments.
if(!@ARGV) { usage(); }

# Make a string out of the commandline arguments so that we can add it to the
# log file.
my $argString = join " ", @ARGV;


# Get commandline options.
# These are:
# 	- "-t" : *must* be present, followed by either "genes" or "isoforms";
# 	dictates whether we collect FPKMs per gene or transcript.
# 	- "-a" : optional, if present we average for replicates.
# 	- "-d" : optional, if present we log extra information about Cufflinks FPKM statuses.
# 	
# $doAvg and $debug are true or false, and come from "-a" and "-d" respectively.
# $genesTranscripts gets the argument of "-t", either "genes" or "isoforms".
my ($doAvg, $genesTranscripts, $debug) = init();


# Get the SDRF filename.
my $sdrfFile = shift;

# Get the experiment accession from the SDRF filename.
(my $expt_acc = basename($sdrfFile)) =~ s/\.sdrf\.txt//;


# Start a log file.
# Name it with the name of this script, the experiment accession, and the
# current process ID.
# E.g.
# 	summarizeFPKMs_E-GEOD-30352_log_12345.txt
(my $logfile = $basename) =~ s/\.pl$/_$expt_acc\_log_$$\.txt/;

open(my $logfileHandle, ">", $logfile) or die "Can't create log file: $!\n";

# Begin log file and add information from the command line arguments.
printf($logfileHandle "Log for $basename\n\n");
printf($logfileHandle "Call:\n\t$0 $argString\n\n");
printf($logfileHandle "SDRF: $sdrfFile\n");

# What remains of @ARGV should now just be the directories containing the
# Cufflinks FPKM tracking files.
my $dirs = \@ARGV;

# Get Cufflinks FPKM tracking filenames and run accessions with getCufflinksFiles().
# - $cufflinksFiles : arrayref for all FPKM tracking files for the specified
# annotation type (genes or isoforms).
# - $runAccessions : arrayref for all run accessions, in the same order as
# the corresponding FPKM tracking files.
my ($cufflinksFiles, $runAccessions) = getCufflinksFiles($logfileHandle, $genesTranscripts, $dirs);


# Build a hash with EFVs and their corresponding run accessions (for writing
# matrix column headings at the end).
# TODO: make readSDRF() use Bio::MAGETAB module instead? Are we always going to read
# the SDRF here though? Also do we need to do this if we are not averaging?
#
# # If we are passed the "-a" option, that means we want to calculate the average
# FPKM for each group of replicates (e.g. for each tissue) in the dataset, and
# not just return the FPKM for every run individually.
# To do this we need to find out which run accessions (and hence FPKM tracking files) are
# associated with which factor values. The mapping between run accessions and
# factor values is available in the experiment's SDRF file. Run accessions are
# in a column called "Comment[ENA_RUN]" and factor values in the
# "FactorValue[xxx]" column(s).
#
# Here we read the SDRF file and create a hash mapping run accessions to factor
# values. This must also be done "per organism", in cases where an experiment
# contains data from more than one organism. E.g. we could have data from human
# and mouse liver, but potentially the organism names might not be in a factor
# value column (though they should be?). In this case we would have human and
# mouse runs assigned "liver" as the factor value and no way to tell them
# apart. So we'll create a key for each organism and after that a key for each
# factor value assigned to each organism. Assigned to each factor value will be
# an array of the run accessions that were found to correspond to them in the
# SDRF.
#
# The hash ends up like e.g.:
# 	$efvs2runIDs->{ "Homo sapiens" }->{ "liver" } = [ "SRR00001", "SRR00002", "SRR00003", ... ]
# 									->{ "heart" } = [ "SRR00004", "SRR00005", "SRR00006", ... ]
my $efvs2runIDs = readSDRF($logfileHandle, $sdrfFile, $runAccessions);


# Flag if true stipulating that FPKMs for individual transcripts needs to be
# obtained. We need to remember this for two reasons:
#	1) For genes we just save the Ensembl gene ID ("ENSGxxxx") from the FPKM
#	tracking file, but for transcripts we save the Ensembl transcript ID as
#	well and stick them together with "-" ("ENSGxxx-ENSTxxx").
# 	2) the logic for keeping FPKMs that Cufflinks has decided are
# 	"low-confidence" is different for genes and transcripts. For genes, we only
# 	want to keep the "OK" FPKMs. For transcripts we'll also keep ones that are
# 	"LOWDATA", but we will distinguish between them.
my $getTranscripts = $genesTranscripts eq "isoforms";



# Get FPKMs for each gene from the *.$genesTranscripts.fpkm_tracking files.
# This is returned as a hash with a key for each gene(-transcript) and
# corresponding arrays of FPKMs for those, e.g.:
# 	$geneTranscriptFPKMs->{ "ENSG0001-ENST0001" } = [ 12, 43, 11, ... ]
#
# The ordering of the FPKMs in the hash corresponds to the run accession at the
# same array index in the $runAccessions array. E.g. the FPKMs at array index 0
# in all arrays in $geneTranscriptFPKMs are from the run with accession at
# array index 0 in $runAccessions. (is this too confusing? TODO: discuss.)
my $geneTranscriptFPKMs = getGeneTranscriptFPKMs($logfileHandle, $cufflinksFiles, $genesTranscripts, $getTranscripts);


# If we want to average the FPKMs for each group of replicates, $doAvg will be
# true.
if($doAvg) {

	# - $efvs2runIDs : a hash with a key for each organism and corresponding
	# arrays of factor values. The averages are calculated per factor value
	# and stored in a new hash under the corresponding factor value as the
	# key. When the final FPKM matrix is output, we want to put
	# comma-separated lists of run accessions as the column headings, rather
	# than the factor value(s) corresponding to each average. So we need to
	# remember the factor values for each organism, in the same order as the
	# average FPKM values in $outHash. TODO: this seems way too complicated!
	# There must be a simpler way?
	# 	$efvsHash->{ "Homo sapiens" } = [ "liver", "heart", "lung" ]
	#
	# - $outHash : a hash with the average FPKM for each group of replicates,
	# for each gene, for each organism.
	# 	$outHash->{ "ENSG001-ENST002" }->{ "Homo sapiens" } = [ 21, 852, 0 ]
	# 
	# TODO: $outHash is a rubbish name, give it a better one.
	my ($efvsHash, $outHash) = getAvgs($logfileHandle, $efvs2runIDs, $runAccessions, $geneTranscriptFPKMs, $getTranscripts, $debug);

	$outHash = roundAveragedFPKMs($outHash);

	# Print FPKM matrix to STDOUT
	printAveragedMatrix($logfileHandle, $efvsHash, $efvs2runIDs, $outHash, $getTranscripts);
}
# If we don't want to average, we just need to round FPKMs.
else {

	printf($logfileHandle "\nSummarizing FPKMs for each run separately.\n");
	
	$geneTranscriptFPKMs = removeNonOKAndRoundNonAveragedFPKMs($geneTranscriptFPKMs, $getTranscripts);
	printNonAveragedMatrix($logfileHandle, $runAccessions, $geneTranscriptFPKMs, $getTranscripts);
}

my $elapsed_seconds = time - $^T;
printf($logfileHandle "\nRuntime: $elapsed_seconds seconds.\n");
close($logfileHandle);

# end
################################################################################





################################################################################
# Subroutines
################################################################################

# init()
# - Get commandline arg(s).
# These are:
# 	- "-t" : *must* be present, followed by the type of annotation, either
# 	"genes" or "isoforms";
# 	dictates whether we collect FPKMs per gene or transcript.
# 	- "-a" : optional, if present we average for replicates.
# 	- "-d" : optional, if present we log extra information about Cufflinks FPKM statuses.
sub init {
	
	# module for commandline options
    use Getopt::Std;

	# We have three options, "-a", "-t", and "-d". "-t" takes an argument so it
	# has a ":" after it so we know to expect one. The argument gets added to
	# the $opt hash by getopts().
    my $opt_string = 'at:d';
    getopts( "$opt_string", \%opt );
	
	# Check we have a "-t" option, can't proceed without it.
    if($opt{t}) {
		
		# Unless the argument for "-t" is "genes" or "isoforms".
		if($opt{t} ne "genes" && $opt{t} ne "isoforms") { 
			
			print "Unknown annotation type: \"", $opt{t}, "\". Should be either \"genes\" or \"isoforms\".\n";
			usage();
		}
    }
	# If we didn't find a "-t" option, exit with usage message.
    else { usage(); }
    
	# Return all the options. "-a" and "-d" might not exist but we check that
	# later.
    return $opt{a}, $opt{t}, $opt{d};
}






# getCufflinksFiles()
# Create an array of all the Cufflinks FPKM tracking files in the directories
# provided at command line. These are either *.genes.fpkm_tracking or
# *.isoforms.fpkm_tracking.
# Also create an array of run accessions (e.g. "ERR0012345") in the same order
# as their corresponding FPKM tracking file.
#
# Arguments:
# 	- $logfileHandle : to write to log file.
# 	- $genesTranscripts : "genes" or "transcripts".
# 	- $dirs : arrayref for directories with Cufflinks FPKM tracking files.
#
# Returns:
# 	- $cufflinksFiles : arrayref for all FPKM tracking files for the specified
# 	annotation type (genes or isoforms).
# 	- $runAccessions : arrayref for all run accessions, in the same order as
# 	the corresponding FPKM tracking files.
sub getCufflinksFiles {

	# Get the arguments:
	# 	- $logfileHandle : to write to log file.
	# 	- $genesTranscripts : "genes" or "transcripts".
	# 	- $dirs : arrayref for directories with Cufflinks FPKM tracking files.
	$_ = shift for my ($logfileHandle, $genesTranscripts, $dirs);

	# Log what's happening.
	printf($logfileHandle "\nLocating *.$genesTranscripts.fpkm_tracking files...\n");

	# Arrayref for the FPKM tracking filenames.
	my $cufflinksFiles = [];
	
	# Arrayref for run IDs.
	my $runAccessions = [];

	# Go through the directories provided.
	foreach my $dir (@{ $dirs }) {
		
		# Get all the *.genes.fpkm_tracking or *.isoforms.fpkm_tracking
		# filenames in this directory.
		my @dirFiles = glob("$dir/*.$genesTranscripts.fpkm_tracking");
		
		# If we didn't find any, die and say so. This probably isn't the right
		# directory.
		unless(@dirFiles) { die "No *.$genesTranscripts.fpkm_tracking files found in $dir!\n"; }
		
		# Go through each of the files we found.
		foreach my $filename (@dirFiles) {
			
			# Log the filename.
			printf($logfileHandle "\t$filename\n");

			# Get run accession from filename.
			# Assumes filename begins with run ID, which is at least one
			# capital letter followed by at least one number, and before the
			# first underscore.
			my @splitFilename = split "/", $filename;
			my $run = $splitFilename[-1];
			$run =~ s/^([A-Z]+\d+)_.*/$1/;

			# Add the run accession to the $runAccessions array.
			push @{ $runAccessions }, $run;
		}
		
		# Add all the FPKM tracking filenames we found to the $cufflinksFiles
		# array.
		push @{ $cufflinksFiles }, @dirFiles;
	}

	# Return the FPKM tracking filenames and the run accessions.
	return($cufflinksFiles, $runAccessions);
}






# readSDRF()
# TODO: use Bio::MAGETAB module here (if we will always be reading SDRF here)?
# 
# If we are passed the "-a" option, that means we want to calculate the average
# FPKM for each group of replicates (e.g. for each tissue) in the dataset, and
# not just return the FPKM for every run individually.
# To do this we need to find out which run accessions (and hence FPKM tracking files) are
# associated with which factor values. The mapping between run accessions and
# factor values is available in the experiment's SDRF file. Run accessions are
# in a column called "Comment[ENA_RUN]" and factor values in the
# "FactorValue[xxx]" column(s).
#
# Here we read the SDRF file and create a hash mapping run accessions to factor
# values. This must also be done "per organism", in cases where an experiment
# contains data from more than one organism. E.g. we could have data from human
# and mouse liver, but potentially the organism names might not be in a factor
# value column (though they should be?). In this case we would have human and
# mouse runs assigned "liver" as the factor value and no way to tell them
# apart. So we'll create a key for each organism and after that a key for each
# factor value assigned to each organism. Assigned to each factor value will be
# an array of the run accessions that were found to correspond to them in the
# SDRF.
#
# The hash ends up like e.g.:
# 	$efvs2runIDs->{ "Homo sapiens" }->{ "liver" } = [ "SRR00001", "SRR00002", "SRR00003", ... ]
# 									->{ "heart" } = [ "SRR00004", "SRR00005", "SRR00006", ... ]
#
# Arguments:
# 	- $logfileHandle : to write to log file.
# 	- $sdrfFile : filename of SDRF.
# 	- $runAccessions : arrayref of run accessions.
#
# Returns:
# 	$efvs2runIDs : hash with mappings between factor values (for each organism) and run accessions.
sub readSDRF {
	
	# Get the arguments:
	# 	- $logfileHandle : to write to log file.
	# 	- $sdrfFile : filename of SDRF.
	# 	- $runAccessions : arrayref of run accessions.
	$_ = shift for my ($logfileHandle, $sdrfFile, $runAccessions);

	# Log what we are doing.
	printf($logfileHandle "\nReading annotations from SDRF...\n");

	# Simple way to check we have an SDRF.
	if($sdrfFile !~ /\.sdrf\.txt$/) {
		die "$sdrfFile doesn't look like an SDRF file to me. If it is, please append \".sdrf.txt\" to its name.\n";
	}

	# Open SDRF.
	open(my $sdrfHandle, "<", $sdrfFile) or die "Can't open $sdrfFile: $!\n";

	# Variables to store run name and factor value column indices.
	my ($SDRFrunInd, @efvIndArray, $SDRForgInd);

	# Hash to remember which run IDs belong to which combination of factor values.
	# $efvs2runIDs->{ "Gallus gallus" }->{ "brain" } = [ "SRR0005", "SRR0006", ... ]
	my $efvs2runIDs = {};

	# Remember the run IDs we've seen in the SDRF, because in SDRFs with paired-end
	# data, we might have two rows per run!
	my $seenRuns = [];

	# Loop through file.
	while(defined (my $line = <$sdrfHandle>))  {
		
		# Remove newlines
		chomp($line);

		# split on tabs
		my @lineSplit = split("\t", $line);

		# Get column indices of run names and factor value(s) from the first
		# line with headers.
		# Characteristics[Organism] should be present in every SDRF header line in
		# some form.
		if($line =~ /characteristics\s*\[\s*organism\s*\]/i) {

			# Index of Characteristics[Organism] column. Technically it should
			# be in a FactorValue[] column if it varies but just in case we'll
			# account for possibility of different organisms here.
			my @orgIndArray = grep $lineSplit[$_] =~ /characteristics\s*\[\s*organism\s*\]/i, 0..$#lineSplit;
			$SDRForgInd = $orgIndArray[0];

			# Index of run names -- this is under Comment[ENA_RUN].
			my @runIndArray = grep $lineSplit[$_] =~ /ENA_RUN/i, 0..$#lineSplit;
			$SDRFrunInd = $runIndArray[0];

			# Index of FactorValue[xxxx] column(s)
			@efvIndArray = grep $lineSplit[$_] =~ /factor\s*value\s*\[/i, 0..$#lineSplit;
		
		} else {
			
			# Check we got indices for organism, run IDs and EFVs
			unless(defined($SDRForgInd) && defined($SDRFrunInd) && @efvIndArray) {
				die("Do not know SDRF columns for Organism, ENA run, and FactorValues.\n");
			}


			# Get the run ID and factor values from their indices.
			my $runID = $lineSplit[$SDRFrunInd];
		
			# Get the organism
			my $organism = $lineSplit[$SDRForgInd];
			

			# Skip to next line if we have already seen this run ID -- SDRFs for
			# paired-end data have run IDs in twice.
			if( grep $_ eq $runID, @{ $seenRuns } ) { next; }
			# Otherwise add this run's ID to the seenRuns array so we'll skip it next time.
			else { push @{ $seenRuns }, $runID; }
			
			# Make sure we have seen a *.cuff.gtf file for this run. If not, skip it.
			unless( grep $_ eq $runID, @{ $runAccessions } ) { next; }
			
			my @efvArray = @lineSplit[@efvIndArray];
			my $efvString = join " ", @efvArray;

			
			printf($logfileHandle "\t$organism:\tRun=$runID\tFactor values=$efvString\n");

			
			# Add runID to the right array in %efvs2runIDs
			if(exists($efvs2runIDs->{ $organism }->{ $efvString })) {

				push @{ $efvs2runIDs->{ $organism }->{ $efvString } }, $runID;
			}
			else {

				$efvs2runIDs->{ $organism }->{ $efvString } = [ $runID ];
			}

			# Find the index of this run ID in the $runAccessions array, created earlier.
			my @runAccsMatches = grep ${ $runAccessions }[$_] eq $runID, 0..$#{ $runAccessions };
			my $runAccIndex = $runAccsMatches[0];
		}
	}
	# Close the file handle.
	close($sdrfHandle);

	# Log that we're done reading.
	printf($logfileHandle "\n\tFinished reading SDRF.\n");

	# Return the hash of mappings between factor values and run accessions.
	return($efvs2runIDs);
}







# getGeneTranscriptFPKMs()
# 
# Create a hash containing all the FPKMs for all genes(-transcripts) in all
# FPKM tracking files. The FPKMs will be in the same order as the FPKM tracking
# files in the array containing them, and hence in the same order as the run
# accessions contained in $runAccessions array.
#
# Arguments:
#	- $logfileHandle : to write to log file.
# 	- $cufflinksFiles : arrayref of FPKM tracking filenames.
# 	- $genesTranscripts : either "genes" or "isoforms".
# 	- $getTranscripts : true or false.
# Returns:
# 	- $geneTranscriptFPKMs : hash with a key for each gene(-transcript) and
# 	arrays of FPKMs corresponding to each of them.
sub getGeneTranscriptFPKMs {
	
	# Get arguments:
	# 	- $logfileHandle : to write to log file.
	# 	- $cufflinksFiles : arrayref of FPKM tracking filenames.
	# 	- $genesTranscripts : either "genes" or "isoforms".
	# 	- $getTranscripts : true or false.
	$_ = shift for my ($logfileHandle, $cufflinksFiles, $genesTranscripts, $getTranscripts);
	
	# Log what we are doing.
	printf($logfileHandle "\nReading in FPKMs from *.$genesTranscripts.fpkm_tracking files...\n");

	# Empty hashref in which to store arrays of FPKMs for each gene for each run.
	# 	$geneTranscriptFPKMs->{"geneID"} = [ <run1_FPKM>, <run2_FPKM>, ... ].
	# 
	# Each key in this hash (gene ID [or gene-transcript ID]) first gets an
	# array of -1s, the same length as the number of run IDs. The FPKMs are
	# then inserted at the appropriate index. If we see a gene(/transcript) in
	# some runs but not others, the -1s at indices corresponding to the runs we
	# did not see that gene(/transcript) are left so that we'll have -1 if a
	# gene was missing.
	my $geneTranscriptFPKMs = {};

	# This is an array of -1s the same length as the number of FPKM tracking
	# files we have.
	my @minusones = (-1) x @{ $cufflinksFiles };
		
	# Counter for run indices, increment for each FPKM tracking file.
	my $runIndex = 0;
	
	# Go through the FPKM tracking files.
	foreach my $filename (@{ $cufflinksFiles }) {
	
		# Loop through file line-by-line and extract relevant info.
		open(my $fpkmsHandle, "<", $filename) or die "Can't open $filename: $!\n";
		while(defined (my $line = <$fpkmsHandle>))  {

			# Skip header line, which starts with "tracking_id".
			if($line =~ /^tracking_id/) { next; } 
			# Otherwise it's a line with info that we want.
			else {
				
				# Remove newline.
				chomp($line);

				# Split on tabs.
				my @splitLine = split /\t/, $line;

				# Get gene ID.
				my $geneTranscript = $splitLine[3];
			
				# Append transcript information if required.
				if ($getTranscripts) {
				   $geneTranscript .= "-$splitLine[0]";
				}

				# Get the FPKM.
				my $FPKM = $splitLine[9];

				# Get the status. This is either:
				# 	- "OK" : Cufflinks worked out the FPKM without any troubles.
				# 	- "LOWDATA" : Cufflinks worked out an FPKM, but it has low confidence in it because either:
				# 		-- there are not enough reads to support expression of
				# 		this gene/transcript.
				# 		-- the locus was too complex for Cufflinks to get a
				# 		reliable estimate or expression.
				# 	- "HIDATA" : there were too many reads mapping to the locus
				# 	and not enough memory for Cufflinks to work out the FPKM.
				# 	- "FAIL" : something else went wrong and Cufflinks couldn't
				# 	calculate the FPKM.
				my $status = $splitLine[12];


				# If status is "FAIL" or "HIDATA" the FPKM value is zero. But
				# this isn't a "real" zero, it's only zero because the actual
				# FPKM could not be calculated. In that case, we don't want to
				# use it so we set it to -1 here. This distinguishes them from
				# "real" zeros we have. Later (during averaging) we will act
				# upon the -1 values as follows:
				# 	- if all values for a given EFV are -1, we will return zero
				# 	and this will not be shown in the interface.
				# 	- if some (but not all) values for a given EFV are -1, we
				# 	will only use the positive values to calculate the average.
				if($status eq "FAIL" || $status eq "HIDATA") { $FPKM = -1; } # TODO: why don't we just skip to next one here?
				
				# For transcripts, we will also keep FPKMs that have status
				# "LOWDATA" and indicate in the interface that the
				# evidence for these transcripts has low confidence.
				my $lowdata = 0;
				if($status eq "LOWDATA") {
				
					if($getTranscripts) { $lowdata = 1; }
				
					# For genes, make the FPKM -1 because we don't want to use it, we
					# only want ones that are OK.
					else { $FPKM = -1; } # TODO: could we skip to the next one here?
				
				}

				
				# If this is not the first FPKM tracking file, we probably
				# already have an entry for this gene/transcript.  If we do,
				# put the FPKM in the right position in its array (using
				# $runIndex).
				#
				if(exists($geneTranscriptFPKMs->{ $geneTranscript })) {
				
					# We need to look out for multiple entries for the same
					# gene/transcript in FPKM tracking files -- in some cases
					# (possibly due to bug in Cufflinks?) different transcripts
					# from the same gene appear separately so that the same gene ID
					# appears more than once, when they should be aggregated into
					# one count per gene ID.
					
					# We also need to look out for transcripts that were
					# low-confidence (LOWDATA) when we last saw them in this
					# FPKM tracking file (does this bug also happen for
					# transcripts or just genes?).
					# If it was, there will be some parentheses so remember
					# this and then strip them off, because we can't add FPKMs
					# if they have non-numeric values in.
					my $prevLowdata = 0;
					if(${ $geneTranscriptFPKMs->{ $geneTranscript }}[ $runIndex ] =~ /\(/) {
					
						$prevLowdata = 1;
						${ $geneTranscriptFPKMs->{ $geneTranscript }}[ $runIndex ] =~ s/\(|\)//g;
					}
				

					# If this gene's previously-found FPKM is not -1 AND
					# neither is the current one, add them together. This
					# accounts for the case where the same gene/transcript has
					# multiple entries in the same FPKM tracking file.
					if ($FPKM != -1) {

						if(${ $geneTranscriptFPKMs->{ $geneTranscript }}[ $runIndex ] != -1) {

							# Add the FPKM to existing one at that position.
							${ $geneTranscriptFPKMs->{ $geneTranscript } }[ $runIndex ] += $FPKM;
						}

						# If the previously-found FPKM is -1 but the current one is
						# not, replace the previous one with the current one.
						else {

							${ $geneTranscriptFPKMs->{ $geneTranscript }}[ $runIndex ] = $FPKM;
						}
					}


					# If transcript status is "LOWDATA" then we still want the
					# FPKM, but we want to remember that we are less confident in
					# it. Put parentheses round it.
					if($lowdata || $prevLowdata) {

						${ $geneTranscriptFPKMs->{ $geneTranscript }}[ $runIndex ] = "(".${ $geneTranscriptFPKMs->{ $geneTranscript }}[ $runIndex ].")";
					}
				}
				


				# If we've never seen this gene before, probably because this
				# is the first FPKM tracking file we've looked at, add an array
				# of -1s then replace the appropriate one with the FPKM.
				else {
					
					# Add array of -1s
					@{ $geneTranscriptFPKMs->{ $geneTranscript } } = @minusones;

					# Overwrite the appropriate -1 with the FPKM we found. This
					# will probably always be the FPKM at array index 0, but
					# perhaps not necessarily if there happens to be a
					# gene/transcript with an entry in some FPKM tracking files
					# and not others.
					${ $geneTranscriptFPKMs->{ $geneTranscript } }[ $runIndex ] = $FPKM;
					
					# if the transcript is low-confidence, put parentheses round it.
					if($lowdata) {
						${ $geneTranscriptFPKMs->{ $geneTranscript }}[ $runIndex ] = "(".${ $geneTranscriptFPKMs->{ $geneTranscript }}[ $runIndex ].")";
					}
				}
			}
		}
		# Close filehandle.
		close($fpkmsHandle);

		# Increment run index so that we put the FPKMs into the right positions
		# in the arrays for each gene.
		$runIndex++;
		
		# Log that we've read this FPKM tracking file.
		printf($logfileHandle "\tFinished reading $filename\n");
	}
	
	# Return the hash filled with FPKMs. Yum.
	return($geneTranscriptFPKMs);
}






# getAvgs
#
# Arguments:
# 	- $logfileHandle : to write to log file.
# 	- $cufflinksFiles : arrayref of FPKM tracking filenames.
# 	- $genesTranscripts : either "genes" or "isoforms".
# 	- $getTranscripts : true or false.
# 	- $debug : true or false -- print out some extra stuff about low-confidence
# 	FPKMs if true.
# Returns:
#	- $efvs2runIDs : a hash with a key for each organism and corresponding
# 	arrays of factor values. The averages are calculated per factor value
# 	and stored in a new hash under the corresponding factor value as the
# 	key. When the final FPKM matrix is output, we want to put
# 	comma-separated lists of run accessions as the column headings, rather
# 	than the factor value(s) corresponding to each average. So we need to
# 	remember the factor values for each organism, in the same order as the
# 	average FPKM values in $outHash. TODO: this seems way too complicated!
# 	There must be a simpler way?
# 	- $outHash : a hash with the average FPKM for each group fo replicates,
# 	for each gene, for each organism.
sub getAvgs {
	
	# Get the arguments:
	# 	- $logfileHandle : to write to log file.
	# 	- $efvs2runIDs : hashref with mapping between each factor value and its
	# 	corresponding run accessions.
	# 	- $runAccessions : arrayref with all the run accessions in the same
	# 	order as the FPKMs in $geneTranscriptFPKMs.
	# 	- $geneTranscriptFPKMs : hashref with a key for each gene(-transcript)
	# 	and a corresponding array of FPKMs found for that gene. The ordering of
	# 	the FPKMs corresponds to the ordering of the run accessions in
	# 	$runAccessions.
	# 	- $getTranscripts : true if we are collecting info for transcripts,
	# 	false if for genes.
    $_ = shift for my ($logfileHandle, $efvs2runIDs, $runAccessions, $geneTranscriptFPKMs, $getTranscripts, $debug);
	
	# Empty hashes.
	# 	- $efvs2runIDs : a hash with a key for each organism and corresponding
	# 	arrays of factor values. The averages are calculated per factor value
	# 	and stored in a new hash under the corresponding factor value as the
	# 	key. When the final FPKM matrix is output, we want to put
	# 	comma-separated lists of run accessions as the column headings, rather
	# 	than the factor value(s) corresponding to each average. So we need to
	# 	remember the factor values for each organism, in the same order as the
	# 	average FPKM values in $outHash. TODO: this seems way too complicated!
	# 	There must be a simpler way?
	# 		$efvsHash->{ "Homo sapiens" } = [ "liver", "heart", "lung" ]
	#
	# 	- $outHash : a hash with the average FPKM for each group of replicates,
	# 	for each gene, for each organism.
	# 		$outHash->{ "ENSG001-ENST002" }->{ "Homo sapiens" } = [ 21, 852, 0 ]
    my ($efvsHash, $outHash) = {};

    # Empty hash for FPKMs to be averaged.
	# It will end up like:
    # $FPKMs4avg->{ "Macaca mulatta" }->{ "prefrontal cortex" }->{ "ENSG00001234" } = [ 12, 15, ... ]
    #                                                          ->{ "ENSG00007890" } = [ 6, 2, ... ]
    #                                 ->{ "heart" }->{ "ENSG00001234" } = [ 0, 1, ... ]
    #                                              ->{ "ENSG00007890" } = [ 543, 498, ... ]
    my $FPKMs4avg = {};
	
	# Go through the keys of $efvs2runIDs -- these are organisms.
    foreach my $organism (keys %{ $efvs2runIDs }) {
	
		# Go through the keys for each organism, which are factor values.
		foreach my $efvString (keys %{ $efvs2runIDs->{ $organism }}) {
			
			# Get the array of runs accessions for this factor value into an
			# array (it's easier to read later).
			my @efvRunIDs = @{ $efvs2runIDs->{ $organism }->{ $efvString }};
			

			# Find the index of each of these runs in $runAccessions to enable
			# access of the right FPKMs in $geneTranscriptFPKMs.
			#
			# Counter to increment for run accession index.
			my $runAccIdx = 0;

			# Go through the $runAccessions array.
			foreach my $runAccsID (@{ $runAccessions }) {
				
				# Go through the run accessions in the array for this factor value.
				foreach my $efvRunID (@efvRunIDs) {

					# If the current run accession from the array for this
					# factor value matches the current run accessions from the
					# $runAccessions array, add the FPKMs at the current run
					# index position to the hash for averaging.
					if($runAccsID eq $efvRunID) {
						
						# If this is not the first time we've seen this factor
						# value in this organism, push the FPKMs onto the
						# existing arrays.
						if(exists($FPKMs4avg->{ $organism }->{ $efvString })) {
							
							# Go through the genes(-transcripts) in $geneTranscriptFPKMs.
							foreach my $geneTranscript (keys %{ $geneTranscriptFPKMs }) {

								push @{ $FPKMs4avg->{ $organism }->{ $efvString }->{ $geneTranscript } }, ${ $geneTranscriptFPKMs->{ $geneTranscript } }[ $runAccIdx ];
							}
						} 
						# If this is the first time we've seen this factor
						# value for this organism, create anonymous arrays for
						# each gene(-transcript).
						else {

							# Go through the genes(-transcripts) in $geneTranscriptFPKMs.
							foreach my $geneTranscript (keys %{ $geneTranscriptFPKMs }) {

								$FPKMs4avg->{ $organism }->{ $efvString }->{ $geneTranscript } = [ ${ $geneTranscriptFPKMs->{ $geneTranscript } }[ $runAccIdx ] ];
							}
						}
					}
				}
				# Increment the counter for the run accession index.
				$runAccIdx++;
			}

		}
    }
	
	# Log that we are now calculating averages.
    printf($logfileHandle "\nComputing mean FPKMs for:\n");

	# Go through the organisms in $FPKMs4avg.
	foreach my $organism (keys %{ $FPKMs4avg }) {
		
		# Log the organism we're looking at now.
		printf($logfileHandle "\t$organism...\n");

		# Go through the factor values for this organism.
		foreach my $efvs (keys %{ $FPKMs4avg->{ $organism }}) {	

			# Add factor value to %efvsHash, for this organism.
			# The factor values in the arrays in this hash will be in the same
			# order as the average FPKM values that are computed now.
			if(exists($efvsHash->{ $organism })) {
				push @{ $efvsHash->{ $organism} }, $efvs;
			} else {
				$efvsHash->{ $organism } = [ $efvs ];
			}
			
			# Go through all the genes(-transcripts) for this factor value for
			# this organism.
			foreach my $geneTranscript (keys %{ $FPKMs4avg->{ $organism }->{ $efvs } }) {
				
				# Get the mean using avg() function.
				# TODO: perhaps put most of avg() function back here instead?
				# Is it too confusing to have two functions with "avg" in the
				# name? Then split getAvgs() in two, with above loop creating
				# $FPKMs4avg in a separate function from this loop.
				my $average = avg($logfileHandle, $FPKMs4avg->{ $organism }->{ $efvs }->{ $geneTranscript }, $geneTranscript, $efvs, $getTranscripts, $debug);
				
				# Add the mean for this factor value to $outHash, in an array
				# for this organism for this gene(-transcript).
				if(exists($outHash->{ $geneTranscript }->{ $organism })) {

					push @{ $outHash->{ $geneTranscript }->{ $organism } }, $average;
				}
				else {

					$outHash->{ $geneTranscript }->{ $organism } = [ $average ];
				}
			}
		}
    }
	
	# Return $efvsHash and $outHash.
    return($efvsHash, $outHash);
}





# removeNonOKAndRoundNonAveragedFPKMs()
# - This is for rounding un-averaged FPKMs and removing non-OK (i.e. -1) FPKM values
# 	Arguments:
# 		- reference to $geneTranscriptFPKMs hash.
#        - flag to indicate if transcripts are being processed
# 	Returns:
# 		- reference to $geneTranscriptFPKMs hash (with FPKMs rounded and FPKM=-1 converted to 0)
sub removeNonOKAndRoundNonAveragedFPKMs {

    $_ = shift for my ($geneTranscriptFPKMs, $getTranscripts);

    foreach my $geneTranscript (keys %{ $geneTranscriptFPKMs }) {
	    
	# Arrayref for rounded FPKMs for this gene.
	my $rounded = [];

	# Iterate over this gene's array of FPKMs and round them, adding each
	# one to the new array.
	foreach my $FPKM (@{ $geneTranscriptFPKMs->{ $geneTranscript } }) {

	    # If this is for genes, not transcripts, change all -1s to zeros.
	    if(!$getTranscripts) {
		if($FPKM == -1) { $FPKM = 0; }
	    }
	    $FPKM = round($FPKM);
	    push @{ $rounded }, $FPKM;
	}
	# Replace the array of un-rounded FPKMs with the array of rounded ones.
        $geneTranscriptFPKMs->{ $geneTranscript } = $rounded;
    }
    return($geneTranscriptFPKMs);
}

# roundAveragedFPKMs()
# - This is for rounding un-averaged FPKMs.
# 	Arguments:
# 		- reference to $geneTranscriptFPKMs hash.
# 	Returns:
# 		- reference to $geneTranscriptFPKMs hash (with FPKMs rounded).
sub roundAveragedFPKMs {

    $_ = shift for my ($geneTranscriptFPKMs);

    foreach my $geneTranscript (keys %{ $geneTranscriptFPKMs }) {
        # Iterate over this gene-organism's array of FPKMs and round them, adding each
        # one to the new array.
	foreach my $organism (keys %{ $geneTranscriptFPKMs->{ $geneTranscript } }){
        
		# Arrayref for rounded FPKMs for this gene.                                                                                                                            
	    my $rounded = [];
	    foreach my $FPKM (@{ $geneTranscriptFPKMs->{ $geneTranscript }->{ $organism } }) {

		$FPKM = round($FPKM);
		push @{ $rounded }, $FPKM;
	    }
	    # Replace the array of un-rounded FPKMs with the array of rounded ones.
	    $geneTranscriptFPKMs->{ $geneTranscript }->{ $organism } = $rounded;
	}
    }
    return($geneTranscriptFPKMs);
}




# round()
# - Round values >1 to nearest integer; round values <=1 to nearest 1dp.
sub round {
    
	my $num = shift;

	# If this number comes from a low-confidence transcript (LOWDATA in
	# Cufflinks output) then it'll have parentheses round it so strip them off
	# before doing calculation.
	my $lowdata = ( ($num =~ /\(/) ? 1 : 0 ); 
	if($lowdata) { $num =~ s/\(|\)//g; }

	# round
	if($num > 1) {
		# to nearest integer if >1
		$num = int($num + 0.5);
	}
	elsif($num > 0) {
		# to nearest 1dp if 0 < $num < 1
		$num = int((10*$num) + 0.5)/10;
	}
	
	# Add parentheses back if there were some to start with.
	if($lowdata) { $num = "(".$num.")"; }

	return($num);
}



# convertLowQualityToNegative()
# To allow the consuming code to store this matrix as numbers, under the assumption that real
# FPKMs are always >= 0, the lowdata numbers are converted to strings representing
# negative FPKMs.
sub convertLowQualityToNegative {
    my $num = shift;
    # If this number comes from a low-confidence transcript (LOWDATA in
    # Cufflinks output) then it'll have parentheses round it so strip them off
    # before doing calculation.
    my $lowdata = ( ($num =~ /\(/) ? 1 : 0 );
    if($lowdata) { $num =~ s/\(|\)//g; $num = "-$num"; }
    return($num);
}



# printAveragedMatrix()
# - Print the FPKM matrix to STDOUT.
# 	Arguments:
# 		- reference to hash of unique factor value combinations for each organism.
# 		- reference to hash of factor value combinations and their run ID(s).
# 		- reference to hash of factor value combinations and their average FPKMs.
#       - flag if true stipulating that FPKMs for individual transcripts needs to be obtained
sub printAveragedMatrix {
    
    $_ = shift for my ($logfileHandle);

    printf($logfileHandle "\nPrinting averaged FPKM matrix to STDOUT...");

    $_ = shift for my ($efvsHash, $efvs2runIDs, $outHash, $getTranscripts);

    # Write matrix to STDOUT
    # Write headers
    print "GeneID";
    if ($getTranscripts) {
	print "\tTranscriptID";
    }

    foreach my $organism (keys %{ $efvsHash }) {

	foreach my $efvs (@{ $efvsHash->{ $organism } }) {

	    print "\t";
	    
	    my $c = 0;
	    foreach my $runID (@{ $efvs2runIDs->{ $organism }->{$efvs} }) {

		print "$runID";
		unless($c == $#{ $efvs2runIDs->{ $organism }->{$efvs} }) {
		    print ",";
		}
		$c++;
	    }
	}
    }
    print "\n";

    # Add gene IDs and FPKMs
    foreach my $geneTranscript (keys %{ $outHash }) {

	if (!$getTranscripts) {
	    print "$geneTranscript";
	} else {
	    my @splitGeneTranscript = split "-", $geneTranscript;
	    print "$splitGeneTranscript[0]\t$splitGeneTranscript[1]";
	}

	foreach my $organism (keys %{ $outHash->{ $geneTranscript } }){

	    foreach my $FPKM (@{ $outHash->{ $geneTranscript }->{ $organism } }) {

		print "\t".convertLowQualityToNegative($FPKM);
	    }
	}
	print "\n";
    }
    printf($logfileHandle "done.\n");
}



# printNonAveragedMatrix()
# - Print the FPKM matrix to STDOUT.
# 	Arguments:
# 		- reference to hash of unique factor value combinations for each organism.
# 		- reference to hash of factor value combinations and their run ID(s).
# 		- reference to hash of factor value combinations and their average FPKMs.
#       - flag if true stipulating that FPKMs for individual transcripts needs to be obtained
sub printNonAveragedMatrix {
	
    $_ = shift for my ($logfileHandle);
	
    printf($logfileHandle "\nPrinting non-averaged FPKM matrix to STDOUT...");
    $_ = shift for my ($runAccessions, $geneTranscriptFPKMs, $getTranscripts);
		
    print "Gene ID";
    if ($getTranscripts) {
	print "\tTranscriptID";
    }
    foreach my $runID (@{ $runAccessions }) {
	print "\t$runID";
    }
    print "\n";
    foreach my $geneTranscript (keys %{ $geneTranscriptFPKMs }) {
		    
	if (!$getTranscripts) {
			
	    print "$geneTranscript";
		    
	} else {
			
	    my @splitGeneTranscript = split "-", $geneTranscript;
	    print "$splitGeneTranscript[0]\t$splitGeneTranscript[1]";
	}
		    
	foreach my $FPKM (@{ $geneTranscriptFPKMs->{$geneTranscript} }) {

	    print "\t".convertLowQualityToNegative($FPKM);
	}
	print "\n";
    }

    printf($logfileHandle "done.\n");
}





# Sub &avg()
# 	Arguments:
# 		- reference to an array of numbers.
# 	Returns:
# 		- Arithmetic mean of those numbers.
#
# Also added checks for parentheses which indicate LOWDATA FPKMs for
# transcripts, and "-1"s which indicate FAIL/HIDATA(/LOWDATA for genes) FPKMs.
# For transcipts:
# 	- Do not use FAIL/HIDATA FPKMs in average calculation. Return 0 if all FPKMs had FAIL/HIDATA.
# For genes:
# 	- As above but also do not use LOWDATA FPKMs in average calculation and return 0 if all FPKMs has LOWDATA.
sub avg {

	$_ = shift for my ($logfileHandle, $nums, $geneTranscript, $efvs, $getTranscripts, $debug);

	my $idType = ( $getTranscripts ? "Transcript" : "Gene" );

	my @nums = @{ $nums };
	
	$_ = 0 for my ($lowdata, $minusOnes); 
	my @numsToUse = ();
	
	# If there are brackets, increment $lowdata. Use it both as a flag and for
	# comparing number of low-confidence FPKMs with total number.
	# If there are "-1"s, increment $minusOnes.
	# Push FPKMs that aren't -1 to @numsToUse.
	foreach my $num (@nums) {
		
		if($num =~ /\(/) { 
			$lowdata++;
			$num =~ s/\(|\)//g;
			push @numsToUse, $num;
		}
		elsif($num == -1) {
			$minusOnes++;
		}
		else {
			push @numsToUse, $num;
		}


	}

	# Number of FPKMs for this EFV.
	my $len = @nums;

	# If all FPKMs were -1, just return zero
	if($minusOnes == $len) { 
	
		# Log this if required
		if($debug) {
			printf($logfileHandle "\t\t$idType $geneTranscript had no FPKMs that were not HIDATA or FAIL ");
			# For genes, we also give LOWDATA FPKMs a "-1" but for transcripts we don't.
			unless($getTranscripts) { printf($logfileHandle "or LOWDATA "); }
			printf($logfileHandle "for $efvs.\n");
		}

		return 0;
	}


	# Log LOWDATA/HIDATA/FAIL info if required.
	if($debug) {
		# If we're still here and we had some brackets, that means we had some
		# LOWDATA transcripts. For cases where all were LOWDATA, this shows in the
		# resulting FPKM matrix. But when not all were LOWDATA it won't, so log it
		# here.
		if($lowdata && $lowdata != $len) {

			printf($logfileHandle "\t\tTranscript $geneTranscript had $lowdata FPKMs with LOWDATA out of $len for $efvs.\n");
		}
		
		# If we are still here and $minusOnes is not 0, that means some (not all)
		# genes or transcripts had FAIL/HIDATA(/LOWDATA for genes). Log this.
		if($minusOnes) {

			printf($logfileHandle "\t\t$idType $geneTranscript had $minusOnes FPKMs with HIDATA and/or FAIL ");
			unless($getTranscripts) { printf($logfileHandle "and/or LOWDATA "); }
			printf($logfileHandle "out of $len for $efvs.\n");
		}
	}


	my $average = sum(@numsToUse) / @numsToUse;

	# If there are the same number of runs as the value of $lowdata, add the
	# parentheses back. Otherwise, don't, because either $lowdata=0 so no runs
	# are low-confidence, or some runs were "OK", so we'll leave it.
	if($lowdata == @numsToUse) { $average = "(".$average.")";}

	return $average;
}



# Sub &usage()
# Print a helpful message and exit.
sub usage {
	print "Usage:
	$basename [-a] -t <genes|isoforms> [-d] <SDRF filename> <directory of cufflinks files 1> [<directory 2> <directory 3> ...]

Options:
	-t	*you must specify either \"-t genes\" or \"-t isoforms\" to collect results for either transcript isoforms or genes*
	-a	calculate mean FPKMs for each factor value
	-d	debug, print out some extra info about Cufflinks FPKM status calls
";
	exit;
}
