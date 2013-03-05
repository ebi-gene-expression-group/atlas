#!/usr/bin/perl
#
# Read in eREAP experiment config file and results from DESeq differential
# expression analysis;
# Make MvA plot for each contrast found in config file using ggplot2 R package;
# Summarize results for all contrasts into a single tab-delimited text file.
#
# To run:
# 	./diffAtlas_DESeq.pl /path/to/<exptAcc>_<species>.conf /path/to/<exptAcc>-contrasts.txt /path/to/diffAtlas_mvaPlot.R

use strict;
use warnings;

use File::Basename;

# Commandline arguments are:
# 	- $confFile : eREAP experiment config file containing information about
# 	location of DESeq results files and contrasts names.
# 	- $atlasContrastsFile : file with assay group and contrast definitions for Atlas.
# 	- $mvaScript : filename of R script for creating "MvA" plot with ggplot2.
$_ = shift for my ($confFile, $atlasContrastsFile, $mvaScript);


# Read config file and get directory containing DESeq results files, hash of
# assay group pairs and their contrast names in eREAP, and experiment accession.
my ($deseqDir, $contrasts, $exptAcc) = readConf($confFile);

# Add Atlas contrast names to contrasts hash
$contrasts = readAtlasContrasts($atlasContrastsFile, $contrasts);


# - Get hash of all DESeq results:
# $deseqResHash->{ "gene1" }->{ "contrast 1" } = [ "p-value", "logFC" ]
#                           ->{ "contrast 2" } = [ "p-value", "logFC" ]
#              ->{ "gene2" }->{ "contrast 1" } = [ "p-value", "logFC" ]
#                           ->{ "contrast 2" } = [ "p-value", "logFC" ]
# - Make an MvA plot for each one contrast.
my $deseqResHash = getDeseqRes($deseqDir, $contrasts, $exptAcc);

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

	# Empty hash to put contrast names in
	my $contrasts = {};
	
	# Get experiment accession, mapper, quantification method and DE method
	# from config file to create full path to DESeq results files.
	# $inContrasts is flag for getting contrast names.
	my ($exptAcc, $mapper, $qMethod, $deMethod, $inContrasts);
	
	open(CONF, $confFile) or die("Can't open $confFile: $!\n");

	print "Reading eREAP config: $confFile...";
	while(defined(my $line = <CONF>)) {

		chomp $line;

		if($line =~ /^name=(.*)/) { $exptAcc = $1; }
		elsif($line =~ /^mapper=(.*)/) { $mapper = $1; }
		elsif($line =~ /^quant_method=(.*)/) { $qMethod = $1; }
		elsif($line =~ /^de_method=(.*)/) { $deMethod = $1; }
		elsif($line =~ /^#Contrasts/) { $inContrasts++; }
		elsif($inContrasts && $line !~ /^contrasts/) {
			
			my $assayGroups = (split "=", $line)[1];

			# join assay group numbers with an underscore.
			my $assayGroupPair = join "_", (split " ", $assayGroups);
			# use assay group string as key in hash, add eREAP's contrast name
			# under "ereap" key.
			$contrasts->{ $assayGroupPair }->{ "ereap" } = (split "=", $line)[0];
		}
		elsif($line =~ /^contrasts/) { last; }
	}
	close(CONF);
	print "done\n";
	
	print "Experiment: $exptAcc\n";
	my $numContrasts = keys %{ $contrasts };
	print "$numContrasts contrasts found in eREAP config:\n";
	foreach my $assayGroupPair (keys %{ $contrasts }) {

		print $contrasts->{ $assayGroupPair }->{ "ereap" }, "\n";
	}
	print "\n";

	$deseqDir = $deseqDir."$exptAcc/$mapper/$qMethod/$deMethod";

	return ($deseqDir, $contrasts, $exptAcc);
}





# readAtlasContrasts
# 	- read Atlas *-contrasts.txt and get pretty contrast names for display. Add
# 	them to the $contrasts hash under the appropriate assay group pair number.
sub readAtlasContrasts {

	$_ = shift for my ($atlasContrastsFile, $contrasts);

	# Read contrasts file
	print "Reading Atlas contrasts from $atlasContrastsFile...";
	open(ATLASCONF, $atlasContrastsFile) or die("Can't open $atlasContrastsFile: $!\n");

	my ($inContrasts, $numContrasts);
	while(defined(my $line = <ATLASCONF>)) {
		
		chomp $line;

		if($line =~ /^#Contrasts/) { $inContrasts++; }
		elsif($inContrasts) {
			$numContrasts++;
			my $assayGroups = (split "=", $line)[1];

			# join assay group numbers with an underscore.
			my $assayGroupPair = join "_", (split " ", $assayGroups);
			# use assay group string as key in hash, add Atlas contrast name
			# under "atlas" key.
			if(exists($contrasts->{ $assayGroupPair })) {
				$contrasts->{ $assayGroupPair }->{ "atlas" } = (split "=", $line)[0];
			} else {
				# Die if we see a contrast in the Atlas contrasts file that we haven't found in the eREAP config.
				die "Contrast \"", (split "=", $line)[0], "\" ($assayGroupPair) found in Atlas contrasts but not in eREAP config file! Can't proceed.\n";
			}
		}
	}
	close(ATLASCONF);
	print "done\n";

	print "$numContrasts contrasts found in Atlas contrasts file:\n";
	foreach my $assayGroupPair (keys %{ $contrasts }) {
		if(exists($contrasts->{ $assayGroupPair }->{ "atlas" })) {
			print $contrasts->{ $assayGroupPair }->{ "atlas" }, "\n";
		} else {
			die "Contrast ", $contrasts->{ $assayGroupPair }->{ "ereap" }, " ($assayGroupPair) was not found in Atlas contrasts file! Can't proceed.\n";
		}
	}

	print "\n";
	return $contrasts;
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

	$_ = shift for my ($deseqDir, $contrasts, $exptAcc);
	my $deseqResHash = {};
	
	# file for temporary storage of data for MvA plot.
	my $plotDataTempFile = ".plotData.txt";
	

	foreach my $assayGroupPair (keys %{ $contrasts }) {

		my $ereapContrastName = $contrasts->{ $assayGroupPair }->{ "ereap" };
		my $deseqResFile = $deseqDir."/$ereapContrastName.genes_de.tsv";

		my $atlasContrastName = $contrasts->{ $assayGroupPair }->{ "atlas" };
		
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
				$deseqResHash->{ $geneID }->{ $atlasContrastName } = [ $adjPval, $logFC ];
				# Add to file for MvA plot.
				printf(PLOTDATA "\n$geneID\t$baseMean\t$logFC\t$adjPval");
			}
		}
		close(PLOTDATA);
		close(DESEQRES);
		print "done\n";

		my $plotFile = $exptAcc."-".$assayGroupPair."-mvaPlot.png";

		print "Making MvA plot...";
		# Create MvA plot with MvA plot script
		my $R_mvaOutput = `Rscript $mvaScript $plotDataTempFile \"$atlasContrastName\" $plotFile rnaseq 2>&1`;
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
	foreach my $assayGroupPair (keys %{ $contrasts }) {

		my $contrastName = $contrasts->{ $assayGroupPair }->{ "atlas" };

		printf(RESFILE "\t$contrastName.p-value\t$contrastName.log2foldchange");
	}

	# write stats
	foreach my $geneID (keys %{ $deseqResHash }) {

		printf(RESFILE "\n$geneID");

		# Use the hash of contrasts to write the headers, in case the
		# ordering of contrast names as keys in $deseqResHash is different from the
		# ordering in contrasts hash.
		foreach my $assayGroupPair (keys %{ $contrasts }) {

			my $contrastName = $contrasts->{ $assayGroupPair }->{ "atlas" };
			my $statsString = join "\t", @{ $deseqResHash->{ $geneID }->{ $contrastName }};

			printf(RESFILE "\t$statsString");
		}
	}
	close(RESFILE);
}
