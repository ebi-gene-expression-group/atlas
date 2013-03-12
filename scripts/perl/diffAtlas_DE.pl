#!/usr/bin/perl
#
# diffAtlas_DE.pl
# 	- Get results of differential expression for either microarray or RNA-seq data.
# 	- For microarray data, this entails finding contrast definitions in contrasts XML file, running
# 	limma for each contrast, creating an MvA plot for each contrast, and then
# 	summarizing statistics results for all contrasts into a single
# 	tab-delimited text file.
# 	- For RNA-seq, differential expression stats for each contrast have already
# 	been calculated using DESeq via eREAP. For this data, find contrast
# 	definitions in eREAP config and display names in Atlas contrasts XML file,
# 	read DESeq results for each contrast, create an MvA plot for each contrast,
# 	and then summarize statistics results for all contrasts into a single
# 	tab-delimited text file.

use strict;
use warnings;

use File::Basename;

# Flush after every print not every newline
$| = 1;

# assumes R scripts directory is in PATH
my $mvaScript = "diffAtlas_mvaPlot.R";
my $limmaScript = "diffAtlas_DE_limma.R";


# Get commandline arguments
my ($atlasContrastXML, $ereapConfig, $normExpr) = init();



# Use $techType to remember if this is microarray or RNA-seq data. Set to
# "rnaseq" if we get an eREAP config file, to "microarray" otherwise.
my $techType = ($ereapConfig ? "rnaseq" : "microarray");

# Hash to pass filenames around
# $fileHash->{ "normExpr" } = "normalizedExpressions.txt"
#          ->{ "limmaScript" } = "../R/diffAtlas_DE_limma.R"
#          ->{ "mvaScript" } = "../R/diffAtlas_mvaPlot.R"
#          ->{ "nameRoot" } = "E-MTAB-1066_A-AFFY-35"
# "normExpr' and "limmaScript" only needed for microarray.
my $fileHash = {};
$fileHash->{ "mvaScript" } = $mvaScript;
if($techType eq "microarray") {
	$fileHash->{ "limmaScript" } = $limmaScript;
	$fileHash->{ "normExpr" } = $normExpr;
}


# Get experiment accession from contrasts filename.
(my $exptAcc = basename($atlasContrastXML)) =~ s/(E-\w{4}-\d+).*/$1/;
print "Experiment accession: $exptAcc\n";

$fileHash->{ "nameRoot" } = $exptAcc;

# Log what we think tech type is.
print "Technology type: $techType\n";

# Get the array design for microarray.
if($techType eq "microarray") {
	(my $arrayDes = basename($atlasContrastXML)) =~ s/.*(A-\w{4}-\d+).*/$1/;
	print "Array design: $arrayDes\n";

	$fileHash->{ "nameRoot" } = $exptAcc."_".$arrayDes;

}


#########################
# COLLECT CONTRAST INFO #
#
# $contrastHash will contain all information regarding all contrasts in the
# experiment (for selected array design for microarray experiments).
#
# When it's built it will look like:
#
# $contrastHash->{ "g1_g2" }->{ "atlasName" } = "genotype: 'mutant X ' vs. 'wild type'"
#                           ->{ "deseqFile" } = "mutantX_vs_WT.genes_de.tsv"
#                           ->{ "reference" } = [ "WT1", "WT2", "WT3" ]
#                           ->{ "test" } = [ "mutX1", "mutX2", "mutX3" ]
#
# * "atlasName" is present for both microarray and RNA-Seq experiments.
# * "deseqFile" is only present for RNA-Seq experiments.
# * "reference" and "test" are only present for
# microarray experiments.

# First, get the Atlas display name, and reference and test assay accessions if
# we have a microarray experiment.
my $contrastHash = readAtlasContrasts($atlasContrastXML, $techType);

# If we have an RNA-Seq experiment, get DESeq results filenames.
if($techType eq "rnaseq") { 
	$contrastHash = readEreapConf($contrastHash, $ereapConfig, $exptAcc); 
}


###############
# GET RESULTS #
#
# $diffExpRes is a hash with statistics results in.
#
# For RNA-seq it looks like:
# 	$diffExpRes->{ "gene1" }->{ "g1_g2" } = [ <adjusted p-value>, <log2 fold change> ]
# For microarray it looks like:
# 	$diffExpRes->{ "designElement1" }->{ "g1_g2" } = [ <adjusted p-value>, <moderated t-statistic>, <log2 fold change> ]
#
# Also create MvA plot for each contrast.
my $diffExpRes = getDEresults($techType, $contrastHash, $fileHash);

# Write them to a tab-delimited text file.
writeResults($diffExpRes, $contrastHash, $fileHash->{ "nameRoot" }, $techType);

# end
#####








###############
# subroutines #
###############

# init
# 	Get commandline arguments using Getopt::Long.
# 		--atlasxml <Atlas contrasts XML file> (required)
# 		--ereapconf <eREAP config file> (for RNA-seq)
# 		--normexpr <microarray normalized expressions matrix> (for microarray)
sub init {
	
	use vars qw/ %opt /;
	use Getopt::Long;

	my ($atlasContrastXML, $ereapConfig, $normExpr);
	GetOptions(
		'atlasxml=s' => \$atlasContrastXML,
		'ereapconf=s' => \$ereapConfig,
		'normexpr=s' => \$normExpr
	);

	unless($atlasContrastXML) {
		die "Cannot proceed without Atlas contrasts XML file. Please provide it with \"--atlasxml <filename>\"\n";
	}
	
	# We need exactly one of eREAP config file OR microarray normalized
	# expressions matrix. Die if we don't have either or we have both, neither
	# cases make any sense.
	my $msg = "Please supply either:\n\teREAP config file (--ereapconf <filename>)\nor\n\tmicroarray normalized expressions matrix (--normexpr <filename>)\n";
	unless($ereapConfig || $normExpr) { die $msg; }
	if($ereapConfig && $normExpr) { die $msg; }

	return ($atlasContrastXML, $ereapConfig, $normExpr);
}








# readAtlasContrasts
# 	- read Atlas XML contrast definitions file. This file is identical for microarray and RNA-seq.
sub readAtlasContrasts {

	$_ = shift for my ($atlasContrastXML, $techType);

	my $contrastHash = {};
	
	# load XML::Simple with strict mode -- gives helpful error messages.
	use XML::Simple qw(:strict);
	use Data::Dumper;
	
	print "\nReading Atlas XML config from $atlasContrastXML...";
	# contrastDefs contains assay group and contrast definitions parsed from XML file.
	my $contrastDefs = XMLin($atlasContrastXML, ForceArray => ['assay', 'contrast', 'assay_group'], KeyAttr => { contrast => 'id', assay_group => 'id' });

	
	foreach my $assayGroupPair (keys %{ $contrastDefs->{ "contrasts" }->{ "contrast" }}) {

		# Take this contrast to a new hash (just for readability)
		my $thisContrast = $contrastDefs->{ "contrasts" }->{ "contrast" }->{ $assayGroupPair };

		# Get the Atlas display name
		$contrastHash->{ $assayGroupPair }->{ "atlasName" } = $thisContrast->{ "name" };
	
		# For microarray experiments, we also need to find out the assay
		# accessions for the reference and test assay groups.
		if($techType eq "microarray") {
			
			# Get assay group numbers
			my $refAGnum = $thisContrast->{ "reference_assay_group" };
			my $testAGnum = $thisContrast->{ "test_assay_group" };

			# All the assay groups
			my $assayGroupDefs = $contrastDefs->{ "assay_groups" }->{ "assay_group" };
			
			# Get arrayref of assay accessions for reference and test assay groups
			$contrastHash->{ $assayGroupPair }->{ "reference" } = $assayGroupDefs->{ $refAGnum }->{ "assay" };
			$contrastHash->{ $assayGroupPair }->{ "test" } = $assayGroupDefs->{ $testAGnum }->{ "assay" };
		}
		
		
		# Check we've got what we came for
		unless(exists($contrastHash->{ $assayGroupPair }->{ "atlasName" })) {
			die "\nNo Atlas display name found in $atlasContrastXML!\n";
		}
		if($techType eq "microarray") {
			my $cName = $contrastHash->{ $assayGroupPair }->{ "atlasName" };

			# Die if we don't have test AND reference assays for this contrast.
			unless(defined($contrastHash->{ $assayGroupPair }->{ "reference" }) && defined($contrastHash->{ $assayGroupPair }->{ "test" })) {
				die "\nCould not find reference and test assays for contrast \"$cName\" ($assayGroupPair)!\n";
			}
		}

	}
	print "done\n";

	# Log what we've found
	my $numContrasts = keys %{ $contrastHash };
	print "$numContrasts contrasts found in Atlas contrasts XML file:\n";
	foreach my $assayGroupPair (keys %{ $contrastHash }) {
		print $contrastHash->{ $assayGroupPair }->{ "atlasName" }, "\n";
	}

	return $contrastHash;
}








# readEreapConf
#  - read eREAP config file to get filename for DESeq results for each contrast.
#  - check experiment accession in eREAP config matches that of Atlas contrasts XML.
#  - die if we find an extra contrast in the eREAP config that was not in Atlas contrasts XML.
#  - die if we couldn't find a contrast from the Atlas contrasts XML in the eREAP config.
#  - match contrasts between the two files based on "assay group pairs" e.g. "g1_g2".
sub readEreapConf {

	$_ = shift for my ($contrastHash, $ereapConfig, $exptAcc);

	# Directory of config file is the same as the beginning of path to
	# DESeq files.
	my $deseqDir = (fileparse($ereapConfig))[1];

	# Get experiment accession, mapper, quantification method and DE method
	# from config file to create full path to DESeq results files.
	# $inContrasts is flag for getting contrast names.
	my ($ereapExptAcc, $mapper, $qMethod, $deMethod, $inContrasts, $numContrasts);
	
	open(CONF, $ereapConfig) or die("Can't open $ereapConfig: $!\n");

	print "\nReading eREAP config: $ereapConfig...";
	while(defined(my $line = <CONF>)) {

		chomp $line;

		if($line =~ /^name=(.*)/) { 
			$ereapExptAcc = $1; 
		
			# Check experiment accession from eREAP config matches that in filename of
			# Atlas contrasts file.
			unless($ereapExptAcc eq $exptAcc) {
				die "\neREAP experiment accession is: $ereapExptAcc -- does not match Atlas experiment accession ($exptAcc)\n";
			}
		}
		
		elsif($line =~ /^mapper=(.*)/) { $mapper = $1; }
		elsif($line =~ /^quant_method=(.*)/) { $qMethod = $1; }
		elsif($line =~ /^de_method=(.*)/) { $deMethod = $1; }
		elsif($line =~ /^#\s*Contrasts/) { $inContrasts++; }
		elsif($inContrasts && $line !~ /^contrasts/) {

			$numContrasts++;
			
			my $assayGroups = (split "=", $line)[1];

			# join assay group numbers with an underscore.
			my $assayGroupPair = join "_", (split " ", $assayGroups);
			# use assay group string as key in hash, add eREAP's contrast name
			# under "ereap" key.
			my $contrastName = (split "=", $line)[0];
			
			# Get the filename of DESeq results for this contrast.
			my $deseqFile = $deseqDir."$exptAcc/$mapper/$qMethod/$deMethod/$contrastName.genes_de.tsv";
			
			# Add DESeq filename to hash, as long as already have an entry for
			# this contrast. If not, die.
			if(exists($contrastHash->{ $assayGroupPair })) {
				$contrastHash->{ $assayGroupPair }->{ "deseqFile" } = $deseqFile;
			} else {
				die "\nDidn't have contrast \"$contrastName\" ($assayGroupPair) in Atlas XML.\n";
			}

		}
		elsif($line =~ /^contrasts/) { last; }
	}
	close(CONF);
	print "done\n";
	

	# Log what we've found.
	# Die if we have a contrast in the Atlas XML file for which we did not find
	# a corresponding contrast in the eREAP config.
	print "$numContrasts found in eREAP config:\n";
	foreach my $assayGroupPair (keys %{ $contrastHash }) {
		if(exists($contrastHash->{ $assayGroupPair }->{ "deseqFile" })) {

			my $contrastName = basename($contrastHash->{ $assayGroupPair }->{ "deseqFile" });
			$contrastName =~ s/\.genes_de\.tsv//;
			print "$contrastName\n";
		} else {
			die "Contrast \"", $contrastHash->{ $assayGroupPair }->{ "atlasName "}, "\" ($assayGroupPair) was not found in eREAP config.\n";
		}
	}

	return ($contrastHash);
}






# getDEresults
# 	- For RNA-Seq, pull statistics results for each contrast out of DESeq results file from eREAP run.
# 	- For microarray, run limma for each contrast and take statistics results from there.
# 	- For both, for each contrast, create an MvA plot in a PNG file.
sub getDEresults {

	# $contrastHash has all info about contrasts.
	# $fileHash has filenames for R script(s) and normalized expressions for
	# microarray.
	# Taking $techType too although it may not be 100% necessary, but makes it
	# more obvious what's going on.
	$_ = shift for my ($techType, $contrastHash, $fileHash, $exptAcc);

	my $normExpr = $fileHash->{ "normExpr" };
	my $mvaScript = $fileHash->{ "mvaScript" };
	my $limmaScript = $fileHash->{ "limmaScript" };


	# Empty hash to put results in.
	my $diffExpRes = {};
	
	# Temp file for MvA plot data.
	my $plotDataTempFile = ".plotData.txt";

	if($techType eq "rnaseq") {

		# use to check certain values are numeric.
		use Scalar::Util qw(looks_like_number);

		print "\nCollecting DESeq results...\n";
		
		foreach my $assayGroupPair (keys %{ $contrastHash }) {

			my $deseqFile = $contrastHash->{ $assayGroupPair }->{ "deseqFile" };
			my $atlasName = $contrastHash->{ $assayGroupPair }->{ "atlasName" };
		
			# Need to get column indices of "baseMean", "log2FoldChange", and "padj" in DESeq results.
			my ($basemeanIdx, $logfcIdx, $adjpvalIdx);

			open(DESEQRES, $deseqFile) or die("Can't open $deseqFile: $!\n");

			open(PLOTDATA, ">$plotDataTempFile") or die("Can't open file to write temporary plot data to: $!\n");
			printf(PLOTDATA "ID\tavgExpr\tlogFC\tadjPval");

			print "Reading DESeq results from $deseqFile...";
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
					# Ensure we get numbers for the things that are supposed to
					# be numbers (weird prob with DESeq output).
					unless(looks_like_number($baseMean)) {
						die "\nDid not get numeric value for baseMean:\nGene ID: $geneID\nbaseMean: $baseMean\n";
					}
					unless(looks_like_number($adjPval)) {
						die "\nDid not get numeric value for adjusted p-value:\nGene ID: $geneID\nadjusted p-value: $adjPval\n";
					}
					unless(looks_like_number($logFC)) {
						die "\nDid not get numeric value for log2FoldChange:\nGene ID: $geneID\nlog2FoldChange: $logFC\n";
					}
					
					# Add to hash for file of all contrasts' results.
					$diffExpRes->{ $geneID }->{ $assayGroupPair } = [ $adjPval, $logFC ];
					# Add to file for MvA plot.
					printf(PLOTDATA "\n$geneID\t$baseMean\t$logFC\t$adjPval");
				}
			}
			close(PLOTDATA);
			close(DESEQRES);
			print "done\n";

			my $plotFile = $fileHash->{ "nameRoot" }."-".$assayGroupPair."-mvaPlot.png";
			
			makeMvaPlot($techType, $plotFile, $plotDataTempFile, $atlasName, $mvaScript);
			

		}
	} else {

		# Temp file for limma results
		my $limmaResTempFile = ".limma_res.txt";
		print "\n";

		foreach my $assayGroupPair (keys %{ $contrastHash }) {

			my $atlasName = $contrastHash->{ $assayGroupPair }->{ "atlasName" };
			
			# Reference and test assay accessions for limma script
			my $refAssays = join ",", @{ $contrastHash->{ $assayGroupPair }->{ "reference" }};
			my $testAssays = join ",", @{ $contrastHash->{ $assayGroupPair }->{ "test" }};

			print "Computing differential expression statistics for contrast \"", $atlasName, "\"...";

			# Run limma script
			my $R_limmaOutput = `$limmaScript $normExpr $refAssays $testAssays $limmaResTempFile $plotDataTempFile 2>&1`;
	

			# Check for errors.
			if($R_limmaOutput =~ /error/i) {
				
				# Can't continue without results from limma so may as well quit.
				die("\nError during differential expression analysis, outout from R below.\n------------\n$R_limmaOutput\n------------\n");
			
			} else {
				print "done\n";
			}


			# Add results to hash.
			open(LIMMARES, "<$limmaResTempFile") or die("Can't open file $limmaResTempFile: $!\n");
			while(defined(my $line = <LIMMARES>)) {
				
				# potentially don't need headers in the limma results file?
				unless($line =~ /^designElements/) {
					
					chomp $line;
					
					# Split on tabs
					my @lineSplit = split "\t", $line;
					
					# Design element (probeset) ID is the first element, take it off.
					my $designElement = shift @lineSplit;
					
					# Add array [p-value, t-statistic, logFoldChange] to hash for this
					# design element for this contrast.
					$diffExpRes->{ $designElement }->{ $assayGroupPair } = \@lineSplit;
				}
			}
			close(LIMMARES);


			# Filename for MvA plot
			my $plotFile = $fileHash->{ "nameRoot" }."-".$assayGroupPair."-mvaPlot.png";

			# Create MvA
			makeMvaPlot($techType, $plotFile, $plotDataTempFile, $atlasName, $mvaScript);
		}
		print "Statistics for all contrasts computed.\n";
	}

	return $diffExpRes;
}






# makeMvaPlot
# 	- Create MvA plot using MvA plot script.
sub makeMvaPlot {

	$_ = shift for my ($techType, $plotFile, $plotDataTempFile, $atlasName, $mvaScript);

	print "Making MvA plot...";
	# Create MvA plot with MvA plot script
	my $R_mvaOutput = `$mvaScript $plotDataTempFile \"$atlasName\" $plotFile $techType 2>&1`;
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






# writeResults
#	- write all results to a tab-delimited text file.
sub writeResults {

	$_ = shift for my ($diffExpRes, $contrastHash, $filenameRoot, $techType);

	my $resFile = $filenameRoot."-analytics.tsv";
	print "\nWriting results to $resFile\n";

	open(RESFILE, ">$resFile") or die "Can't open $resFile: $!\n";

	# Write column headers
	if($techType eq "rnaseq") { printf(RESFILE "Gene ID"); }
	else { printf(RESFILE "Design Element"); }
	foreach my $assayGroupPair (keys %{ $contrastHash }) {
		printf(RESFILE "\t$assayGroupPair.p-value");
		if($techType eq "microarray") { printf(RESFILE "\t$assayGroupPair.t-statistic"); }
		printf(RESFILE "\t$assayGroupPair.log2foldchange");
	}

	# Write statistics
	foreach my $id (keys %{ $diffExpRes }) {

		printf(RESFILE "\n$id");

		# Use ordering of assay group pairs from %contrastHash so stats are in
		# the same order as the headers.
		foreach my $assayGroupPair (keys %{ $contrastHash }) {

			my $statsString = join "\t", @{ $diffExpRes->{ $id }->{ $assayGroupPair }};
			printf(RESFILE "\t$statsString");
		}
	}
}
