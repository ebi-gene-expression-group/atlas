#!/usr/bin/perl
#
# Read in eREAP experiment config file and results from DESeq differential
# expression analysis;
# Make MvA plot for each contrast found in config file using ggplot2 R package;
# Summarize results for all contrasts into a single tab-delimited text file.
#
# To run:
# 	./diffAtlas_DESeq.pl </path/to/<exptAcc>_<species>.conf </path/to/diffAtlas_mvaPlot.R>

use strict;
use warnings;

use File::Basename;

# Commandline arguments are:
# 	- $confFile : eREAP experiment config file containing information about
# 	location of DESeq results files and contrasts names.
# 	- $mvaScript : filename of R script for creating "MvA" plot with ggplot2.
$_ = shift for my ($confFile, $mvaScript);


# Read config file and get directory containing DESeq results files, array of
# contrast names, and experiment accession.
my ($deseqDir, $contrasts, $exptAcc) = readConf($confFile);


# - Get hash of all DESeq results:
# $deseqResHash->{ "gene1" }->{ "contrast 1" } = [ "p-value", "logFC" ]
#                           ->{ "contrast 2" } = [ "p-value", "logFC" ]
#              ->{ "gene2" }->{ "contrast 1" } = [ "p-value", "logFC" ]
#                           ->{ "contrast 2" } = [ "p-value", "logFC" ]
# - Make an MvA plot for each one contrast.
my $deseqResHash = getDeseqRes($deseqDir, $contrasts);

# write differential expression statistics to a file
writeDeseqResults($exptAcc, $deseqResHash, $contrasts);

# end
#####



###############
# subroutines #
###############

# readConf
#  - read eREAP config file and find out directory of DESeq results files,
#  experiment accession, and contrast names.
sub readConf {

	my $confFile = shift;

	# Directory of config file is the same as the beginning of path to
	# DESeq files.
	my $deseqDir = (fileparse($confFile))[1];

	# Empty array to put contrast names in
	my $contrasts = [];
	
	# Get experiment accession, mapper, quantification method and DE method
	# from config file to create full path to DESeq results files.
	my ($exptAcc, $mapper, $qMethod, $deMethod);

	open(CONF, $confFile) or die("Can't open $confFile: $!\n");

	print "Reading config file: $confFile...";
	while(defined(my $line = <CONF>)) {

		chomp $line;

		if($line =~ /^name=(.*)/) { $exptAcc = $1; }
		elsif($line =~ /^mapper=(.*)/) { $mapper = $1; }
		elsif($line =~ /^quant_method=(.*)/) { $qMethod = $1; }
		elsif($line =~ /^de_method=(.*)/) { $deMethod = $1; }
		elsif($line =~ /^contrasts=(.*)/) { @{ $contrasts } = split " ", $1; }
	}
	print "done\n";
	
	print "Experiment: $exptAcc\n";
	my $numContrasts = @{ $contrasts };
	print "$numContrasts contrasts.\n\n";
	
	$deseqDir = $deseqDir."$exptAcc/$mapper/$qMethod/$deMethod";

	return ($deseqDir, $contrasts, $exptAcc);
}




# getDeseqRes
# 	- Read files containing DESeq's results from each contrast and summarize relevant values into a hash.
# 	- Create an MvA plot for each contrast by writing a temporary tab-delimited
# 	file and calling MvA plotting R script.
#
# $deseqResHash->{ "gene1" }->{ "contrast 1" } = [ "p-value", "logFC" ]
#                           ->{ "contrast 2" } = [ "p-value", "logFC" ]
#              ->{ "gene2" }->{ "contrast 1" } = [ "p-value", "logFC" ]
#                           ->{ "contrast 2" } = [ "p-value", "logFC" ]
# 
sub getDeseqRes {

	$_ = shift for my ($deseqDir, $contrasts);
	my $deseqResHash = {};
	
	# file for temporary storage of data for MvA plot.
	my $plotDataTempFile = ".plotData.txt";
	

	foreach my $contrastName (@{ $contrasts }) {

		my $deseqResFile = $deseqDir."/$contrastName.genes_de.tsv";
		
		# Need to get column indices of "baseMean", "log2FoldChange" and "padj" in DESeq results.
		my ($basemeanIdx, $logfcIdx, $adjpvalIdx);
		
		open(DESEQRES, $deseqResFile) or die("Can't open $deseqResFile: $!\n");

		open(PLOTDATA, ">$plotDataTempFile") or die("Can't open file to write temporary plot data to: $!\n");
		printf(PLOTDATA "ID\tavgExpr\tlogFC\tadjPval");

		print "Reading DESeq results from $deseqResFile...";
		while(defined(my $line = <DESEQRES>)) {

			chomp $line;
			my @lineSplit = split "\t", $line;
			
			# On first line, get column indices
			if($line =~ /^id\t/) {

				$basemeanIdx = (grep { $lineSplit[$_] eq "baseMean" } 0..$#lineSplit)[0];
				$logfcIdx = (grep { $lineSplit[$_] eq "log2FoldChange" } 0..$#lineSplit)[0];
				$adjpvalIdx = (grep { $lineSplit[$_] eq "padj" } 0..$#lineSplit)[0];

				if($basemeanIdx == 0 || $logfcIdx == 0 || $adjpvalIdx == 0) {
					die("Couldn't get column indices for all required columns.\n");
				}
			}
			else {
				
				# Use indices found above to get values (gene ID is always the
				# very first element).
				my $geneID = $lineSplit[0];
				my $baseMean = $lineSplit[$basemeanIdx];
				my $logFC = $lineSplit[$logfcIdx];
				my $adjPval = $lineSplit[$adjpvalIdx];
				
				# Add to hash for file of all contrasts' results.
				$deseqResHash->{ $geneID }->{ $contrastName } = [ $adjPval, $logFC ];
				# Add to file for MvA plot.
				printf(PLOTDATA "\n$geneID\t$baseMean\t$logFC\t$adjPval");
			}
		}
		close(PLOTDATA);
		close(DESEQRES);
		print "done\n";

		my $plotFile = $contrastName."-mvaPlot.png";

		print "Making MvA plot...";
		# Create MvA plot with MvA plot script
		my $R_mvaOutput = `Rscript $mvaScript $plotDataTempFile \"$contrastName\" $plotFile rnaseq 2>&1`;
		if($R_mvaOutput =~ /error/i) {

			# Report the error but don't worry about dying as we can live
			# without the MvA plot.
			print "\nError creating MvA plot, output from R below.\n------------\n$R_mvaOutput\n------------\n";

		} else {
			print "done.\n";
		}
		# clean up
		`rm $plotDataTempFile`;
		`rm Rplots.pdf`;
	}
	
	print "\nStatistics for all contrasts collected.\n";
	return ($deseqResHash);
}





sub writeDeseqResults {
	
	$_ = shift for my ($exptAcc, $deseqResHash, $contrasts);
	
	# Make filename
	my $allResultsFile = $exptAcc."-analytics.tsv";

	print "Writing statistics to $allResultsFile\n";

	open(RESFILE, ">$allResultsFile") or die("Can't open $allResultsFile: $!\n");

	# Write column headings
	printf(RESFILE "Gene ID");
	foreach my $contrastName (@{ $contrasts }) {
		printf(RESFILE "\t$contrastName.p-value\t$contrastName.log2foldchange");
	}

	# write stats
	foreach my $geneID (keys %{ $deseqResHash }) {

		printf(RESFILE "\n$geneID");

		# Use the array of contrasts used to write the headers, in case the
		# ordering of contrast names as keys in the hash is different fromt eh
		# ordering in this array.
		foreach my $contrastName (@{ $contrasts }) {
			
			my $statsString = join "\t", @{ $deseqResHash->{ $geneID }->{ $contrastName }};

			printf(RESFILE "\t$statsString");
		}
	}
	close(RESFILE);
}
