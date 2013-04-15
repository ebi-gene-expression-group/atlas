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
my ($atlasXML, $ereapConfig) = init();


# Get experiment accession from contrasts filename.
(my $exptAcc = basename($atlasXML)) =~ s/(E-\w{4}-\d+).*/$1/;
print "Experiment accession: $exptAcc\n";


# Read Atlas XML config and put relevant info into hash. For all platforms we
# want Atlas display name. For microarray we also want assay accessions so we
# can do DE analysis. Don't need these for RNA-seq. We don't get an array
# design accession for RNA-seq so here we just put the word "rnaseq" as the
# key.
# $contrastHash->{ "A-AFFY-35" }->{ "atlasName" } = "genotype: 'mutant X' vs. 'wild type'"
#                               ->{ "reference" } = [ "WT1", "WT2", "WT3" ]
#                               ->{ "test" } = [ "mutX1", "mutX2", "mutX3" ]
#              ->{ "rnaseq" }->{ "atlasName" } = "genotype: 'mutant Y' vs. 'wild type'"
#                            ->{ "deseqFile" } = "./deSeqResults.genes_de.tsv"
my $contrastHash = readAtlasXML($atlasXML);

# If we have an RNA-Seq experiment, get DESeq results filenames.
if($ereapConfig) { 
	$contrastHash = readEreapConf($contrastHash, $ereapConfig, $exptAcc); 
}


# Get DE results and write them.
getDEresults($contrastHash, $exptAcc, $mvaScript, $limmaScript);

# end
#####








###############
# subroutines #
###############

# init
# 	Get commandline arguments using Getopt::Long.
# 		--atlasxml <Atlas contrasts XML file> (required)
# 		--ereapconf <eREAP config file> (for RNA-seq)
sub init {
	
	use vars qw/ %opt /;
	use Getopt::Long;

	my ($atlasXML, $ereapConfig);
	GetOptions(
		'atlasxml=s' => \$atlasXML,
		'ereapconf=s' => \$ereapConfig,
	);

	unless($atlasXML) {
		die "Cannot proceed without Atlas contrasts XML file. Please provide it with \"--atlasxml <filename>\"\nFor RNA-seq experiments please also provide \"--ereapconf <eREAP config filename>\"\n";
	}
	
	return ($atlasXML, $ereapConfig);
}





# readAtlasXML
# 	- read Atlas XML contrast definitions file.
#	- This file has an <analytics> section for each platform. For microarray
#	each analytics section has an array design accession in <array_design>
#	tags. We need to know which contrasts belong to which array design because
#	we have to select the appropriate normalized expressions file when we come
#	to do the DE analysis with limma.
#	- RNA-seq analytics section doesn't have an <array_design> section. All
#	RNA-seq data goes into the same <analytics> section. If there are multiple
#	reference genomes used this is taken care of in the eREAP config file but
#	this script doesn't care about that, because it is just retrieving
#	pre-computed results for each contrast found in the XML and eREAP config.
sub readAtlasXML {

	$_ = shift for my ($atlasXML);
	
	# empty hash for contrast info.
	my $contrastHash = {};
	
	# load XML::Simple with strict mode -- gives helpful error messages.
	use XML::Simple qw(:strict);
	use Data::Dumper;
	
	print "\nReading Atlas XML config from $atlasXML...\n";
	# xml contains assay group and contrast definitions parsed from XML file, for each <analytics> section.
	my $xml = XMLin($atlasXML, ForceArray => ['analytics', 'assay', 'contrast', 'assay_group'], KeyAttr => { contrast => 'id', assay_group => 'id' });

	# For each platform (array design or RNA-seq section)
	foreach my $ana (@{ $xml->{ "analytics" } }) { 

		my $arrayDesign = $ana->{ "array_design" };

		if(defined($arrayDesign)) { $arrayDesign =~ s/\s//g; }
		else { $arrayDesign = "rnaseq"; }

		my $thisContrast = $ana->{ "contrasts" }->{ "contrast" };
		foreach my $agPair (keys %{ $thisContrast }) {

			$contrastHash->{ $arrayDesign }->{ $agPair }->{ "atlasName" } = $thisContrast->{ $agPair }->{ "name" };
		
			unless($arrayDesign eq "rnaseq") {

				my $refAGnum = $thisContrast->{ $agPair }->{ "reference_assay_group" };
				my $testAGnum = $thisContrast->{ $agPair }->{ "test_assay_group" };

				# Have to create a new array for the assay accessions of each assay
				# group by de-referencing the arrayref in $ana.
				# Then pass the reference to this to $contrastHash.
				# This is because if the same assay group is used more than once,
				# passing the arrayref in $ana straight to $contrastHash does not
				# work. The first time it does, but the second time the assay group
				# is passed you just get e.g. (from Data::Dumper): 
				# 	'reference' => $VAR1->{'A-AFFY-35'}{'g2_g3'}{'reference'}
				# instead of:
				#	'reference' => [
				#                   'WT3',
				#                   'WT1',
				#                   'WT2'
				#                  ]
				# The second thing is what we actually wanted.
				# The following code achieves that.
				my @refAGarray = @{ $ana->{ "assay_groups" }->{ "assay_group" }->{ $refAGnum }->{ "assay" } };	
				$contrastHash->{ $arrayDesign }->{ $agPair }->{ "reference" } = \@refAGarray;

				my @testAGarray = @{ $ana->{ "assay_groups" }->{ "assay_group" }->{ $testAGnum }->{ "assay" }};
				$contrastHash->{ $arrayDesign }->{ $agPair }->{ "test" } = \@testAGarray;
			}
		}
	}

	# Log what we've found
	foreach my $arrayDesign (keys %{ $contrastHash }) {
		
		my $numContrasts = keys %{ $contrastHash->{ $arrayDesign }};
		print "$numContrasts contrast";
		unless($numContrasts == 1) { print "s";} 
		print " found for $arrayDesign:\n";
		
		foreach my $agPair (keys %{ $contrastHash->{ $arrayDesign } }) {

			print "\t", $contrastHash->{ $arrayDesign}->{ $agPair }->{ "atlasName" }, "\n";
		}
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

	print "\nReading eREAP config: $ereapConfig...\n";
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
			unless(-e $deseqFile) {
				die("\nCan't find DESeq results file for contrast \"$contrastName\".\nLooking for \"$deseqFile\"\n");
			}
			
			# Add DESeq filename to hash, as long as already have an entry for
			# this contrast. If not, die.
			if(exists($contrastHash->{ "rnaseq" }->{ $assayGroupPair })) {
				$contrastHash->{ "rnaseq" }->{ $assayGroupPair }->{ "deseqFile" } = $deseqFile;
			} else {
				die "\nDidn't have contrast \"$contrastName\" ($assayGroupPair) in Atlas XML.\n";
			}

		}
		elsif($line =~ /^contrasts/) { last; }
	}
	close(CONF);

	# Log what we've found.
	# Die if we have a contrast in the Atlas XML file for which we did not find
	# a corresponding contrast in the eREAP config.
	print "$numContrasts contrast";
	unless($numContrasts == 1) { print "s"; }
	print " found in eREAP config:\n";
	
	foreach my $assayGroupPair (keys %{ $contrastHash->{ "rnaseq"} }) {
		if(exists($contrastHash->{ "rnaseq" }->{ $assayGroupPair }->{ "deseqFile" })) {

			my $contrastName = basename($contrastHash->{ "rnaseq" }->{ $assayGroupPair }->{ "deseqFile" });
			$contrastName =~ s/\.genes_de\.tsv//;
			print "\t$contrastName\n";
		} else {
			die "Contrast \"", $contrastHash->{ "rnaseq" }->{ $assayGroupPair }->{ "atlasName "}, "\" ($assayGroupPair) was not found in eREAP config.\n";
		}
	}
	print "\n";

	return ($contrastHash);
}






# getDEresults
# 	- For RNA-Seq, pull statistics results for each contrast out of DESeq results file from eREAP run.
# 	- For microarray, run limma for each contrast and take statistics results from there.
# 	- For both, for each contrast, create an MvA plot in a PNG file.
# 	- Call writeResults() for each platform to write the results to a single TSV file.
sub getDEresults {

	$_ = shift for my ($contrastHash, $exptAcc, $mvaScript, $limmaScript);
	
	# Get results per "platform" i.e. array design accession (e.g. "A-AFFY-35") for
	# microarray or "rnaseq" for RNA-seq.
	foreach my $platform (keys %{ $contrastHash }) {

		# Empty hash to put results in.
		my $diffExpRes = {};
		
		# Temp file for MvA plot data.
		my $plotDataTempFile = ".plotData.txt";

		# Get RNA-seq results from DESeq results file(s).
		if($platform eq "rnaseq") {

			# use to check certain values are numeric.
			use Scalar::Util qw(looks_like_number);

			print "\nCollecting DESeq results...\n";
			
			# Get results for each contrast
			foreach my $assayGroupPair (keys %{ $contrastHash->{ "rnaseq" } }) {

				my $deseqFile = $contrastHash->{ "rnaseq" }->{ $assayGroupPair }->{ "deseqFile" };
				my $atlasName = $contrastHash->{ "rnaseq" }->{ $assayGroupPair }->{ "atlasName" };
			
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

				# Filename for MvA plot
				my $plotFile = "$exptAcc-".$assayGroupPair."-mvaPlot.png";
				# Create MvA
				makeMvaPlot("rnaseq", $plotFile, $plotDataTempFile, $atlasName, $mvaScript);
			
			} # foreach my $assayGroupPair (keys %{ $contrastHash->{ "rnaseq" } })
		
		} # if($platform eq "rnaseq")

		# Run limma to get DE results for microarray.
		else {

			# Temp file for limma results
			my $limmaResTempFile = ".limma_res.txt";
			print "\n";

			# Normalized expressions filename
			my $normExpr = $exptAcc."_".$platform."-normalized-expressions.tsv";
			unless(-e $normExpr) {
				die "Can't find normalized expressions matrix \"$normExpr\"\n";
			}
			
			# Get results for each contrast.
			foreach my $assayGroupPair (keys %{ $contrastHash->{ $platform } }) {

				my $atlasName = $contrastHash->{ $platform }->{ $assayGroupPair }->{ "atlasName" };
				
				# Reference and test assay accessions for limma script
				my $refAssays = join ",", @{ $contrastHash->{ $platform }->{ $assayGroupPair }->{ "reference" }};
				my $testAssays = join ",", @{ $contrastHash->{ $platform }->{ $assayGroupPair }->{ "test" }};

				print "Computing differential expression statistics for contrast \"", $atlasName, "\"...";

				# Run limma script
				my $R_limmaOutput = `$limmaScript $normExpr $refAssays $testAssays $limmaResTempFile $plotDataTempFile 2>&1`;
		

				# Check for errors. This does not catch warnings.
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
				my $plotFile = $exptAcc."_".$platform."-".$assayGroupPair."-mvaPlot.png";
				# Create MvA
				makeMvaPlot("microarray", $plotFile, $plotDataTempFile, $atlasName, $mvaScript);
			}
			
			print "Statistics for all contrasts computed for $platform.\n";
		} # if($platform eq "rnaseq"){}else
		
		# Write all results for this platform to a tab-delimited text file.
		writeResults($diffExpRes, $contrastHash, $exptAcc, $platform);

	} # foreach my $platform (keys %{ $contrastHash })
}






# makeMvaPlot
# 	- Create MvA plot using MvA plot script.
sub makeMvaPlot {

	$_ = shift for my ($platform, $plotFile, $plotDataTempFile, $atlasName, $mvaScript);

	print "Making MvA plot...";
	# Create MvA plot with MvA plot script
	my $R_mvaOutput = `$mvaScript $plotDataTempFile \"$atlasName\" $plotFile $platform 2>&1`;
	
	# Check for errors (this doesn't catch warnings).
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
#	- write all results for a single platform (e.g. "A-AFFY-35" or "rnaseq") to
#	a tab-delimited text file.
sub writeResults {

	$_ = shift for my ($diffExpRes, $contrastHash, $exptAcc, $platform);
	
	# Results file needs array design accession if it's microarray.
	my $resFile = ( $platform eq "rnaseq" ? $exptAcc."-analytics.tsv" : $exptAcc."_".$platform."-analytics.tsv" );
	
	print "\nWriting results to $resFile\n";

	open(RESFILE, ">$resFile") or die "Can't open $resFile: $!\n";

	# Write column headers
	if($platform eq "rnaseq") { printf(RESFILE "Gene ID"); }
	else { printf(RESFILE "Design Element"); }
	foreach my $assayGroupPair (keys %{ $contrastHash->{ $platform } }) {
		printf(RESFILE "\t$assayGroupPair.p-value");
		unless($platform eq "rnaseq") { printf(RESFILE "\t$assayGroupPair.t-statistic"); }
		printf(RESFILE "\t$assayGroupPair.log2foldchange");
	}

	# Write statistics
	foreach my $id (keys %{ $diffExpRes }) {

		printf(RESFILE "\n$id");

		# Use ordering of assay group pairs from %contrastHash so stats are in
		# the same order as the headers.
		foreach my $assayGroupPair (keys %{ $contrastHash->{ $platform } }) {

			my $statsString = "";
			if(exists($diffExpRes->{ $id }->{ $assayGroupPair })) {
				$statsString = join "\t", @{ $diffExpRes->{ $id }->{ $assayGroupPair }};
			} else {

				# Some genes are excluded if their counts are zero across all
				# assays. If there is more than one contrast in the experiment,
				# assays in one contrast may have non-zero counts while assays
				# in another contrast all have zero counts. In this case, we
				# have DESeq statistics results for one contrast and none for
				# the other. Where we don't have any results, we will put "NA"
				# values into the results file.
				if($platform eq "rnaseq") { $statsString = "NA\tNA"; }
				else { $statsString = "NA\tNA\tNA"; }
			}
			printf(RESFILE "\t$statsString");
		}
	}
}
