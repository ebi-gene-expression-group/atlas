#!/usr/bin/perl
#
# Microarray differential expression analysis using limma for Differential Atlas.
# Read assay group and contrast definitions;
# Compute statistics for differential expression for each contrast using limma
# R package;
# Create "MvA" plots for each contrast using ggplot2 R package.

use strict;
use warnings;

use File::Basename;

# Commandline arguments are:
# 	- $defsFile : file with assignment of assay accessions to assay groups and assay groups to contrasts.
# 	- $normExpr : file with normalized, summarized expressions.
# 	- $limmaScript : filename of R script for differential expression analysis using limma.
# 	- $mvaScript : filename of R script for creating "MvA" plot with ggplot2.
$_ = shift for my ($defsFile, $normExpr, $limmaScript, $mvaScript);

my @accessions = split "_", basename($defsFile);
my $exptAcc = $accessions[0];
(my $arrayDesAcc = $accessions[1]) =~ s/-contrasts\.txt//;

unless($exptAcc && $arrayDesAcc) { die "Error: Couldn't find experiment and array design accessions in filename: $defsFile\n"; }


# Read data from contrast definitions file into hash:
# contrastHash->{ "contrast 1" }->{ "reference" } = [ "wildType1", "wildType2", "wildType3" ]
#                               ->{ "test" } = [ "Xmutant1", "Xmutant2", "Xmutant3" ]
#                               ->{ "AGnums" } = "2_1"
#             ->{ "contrast 2" }->{ "reference" } = [ "wildType1", "wildType2", "wildType3" ]
#                               ->{ "test" } = [ "Ymutant1", "Ymutant2", "Ymutant3" ]
#                               ->{ "AGnums" } = "2_3"
my $contrastHash = readContrasts($defsFile);
# Check we've got things in $contrastHash.
unless(%{ $contrastHash }) { die "Error: No contrasts found in $defsFile\n"; }

# Run differential expression analysis and get results, make MvA plots.
# $limmaResultsHash->{ "designElement1" }->{ "contrast 1" } = [ "p-value", "t-stat", "logFC" ]
# 		                                 ->{ "contrast 2" } = [ "p-value", "t-stat", "logFC" ]
# 		           ->{ "designElement2" }->{ "contrast 1" } = [ "p-value", "t-stat", "logFC" ]
#                                        ->{ "contrast 2" } = [ "p-value", "t-stat", "logFC" ]
my $limmaResHash = getDEresults($contrastHash, $limmaScript, $normExpr);

# Write all differential expression stats to a file.
writeLimmaResults($exptAcc, $arrayDesAcc, $limmaResHash);


# end
#####





###############
# subroutines #
###############

# readContrasts
#  - read the assay group and contrast definitions file and return a hash with
#  this data in.
sub readContrasts {

	my $defsFile = shift;
	my $contrastHash = {};

	# a temporary place to put assay groups
	# $agHash->{ "1" } = [ "wildType1", "wildType2", "wildType3" ]
	#        ->{ "2" } = [ "Xmutant1", "Xmutant2", "Xmutant3" ]
	#        ->{ "3" } = [ "Ymutant1", "Ymutant2", "Ymutant3" ]
	my $agHash = {};


	open(DEFSFILE, $defsFile) or die("Can't open $defsFile: $!\n");
	while(defined(my $line = <DEFSFILE>)) {
		
		unless($line =~ /^#/) {
			
			chomp $line;

			if($line =~ /^(\d+)=(.*)/) {

				my @ags = split "@@", $2;
				
				$agHash->{$1} = \@ags;
			}
			elsif($line =~ /=\d+ \d+/) {
				
				# given ordering of the contrasts file we should have got the assay
				# groups information by the time we get to the contrast
				# definitions. If not, die.
				unless(%{ $agHash }) { die "No assay groups found!\n"; }
				
				my @lineSplit = split "=", $line;
				my $contrastName = $lineSplit[0];
				my ($refAG, $testAG) = split " ", $lineSplit[1];

				$contrastHash->{ $contrastName }->{ "reference" } = $agHash->{ $refAG };
				$contrastHash->{ $contrastName }->{ "test" } = $agHash->{ $testAG };
				# This is for the plot filename:
				$contrastHash->{ $contrastName }->{ "AGnums" } = $refAG."_".$testAG;
			}
		}
	}
	return $contrastHash;
}





# getDEresults
#  - Run limma script for each contrast and build a hash of results of all
#  contrasts:
# $limmaResultsHash->{ "designElement1" }->{ "contrast 1" } = [ "p-value", "t-stat", "logFC" ]
# 		                                 ->{ "contrast 2" } = [ "p-value", "t-stat", "logFC" ]
# 		           ->{ "designElement2" }->{ "contrast 1" } = [ "p-value", "t-stat", "logFC" ]
#                                        ->{ "contrast 2" } = [ "p-value", "t-stat", "logFC" ]
sub getDEresults {

	$_ = shift for my ($contrastHash, $limmaScript, $normExpr);

	my $limmaResHash = {};
	
	# Filenames for temporary storage of limma results
	my $limmaResTempFile = ".limma_res.txt";
	my $plotDataTempFile = ".plotData.txt";
	
	# Do differential expression stats for each contrast
	foreach my $contrastName (keys %{ $contrastHash }) {

		print "Computing differential exprssion statistics for contrast \"$contrastName\".\n";
		
		# Reference and test assay accessions for limma script.
		my $refAssays = join ",", @{ $contrastHash->{ $contrastName }->{ "reference" }};
		my $testAssays = join ",", @{ $contrastHash->{ $contrastName }->{ "test" }};
		
		# Run limma script.
		`Rscript $limmaScript $normExpr $refAssays $testAssays $limmaResTempFile $plotDataTempFile 2>&1`;
		
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
				$limmaResHash->{ $designElement }->{ $contrastName } = \@lineSplit;
			}
		}
		close(LIMMARES);
		# clean up
		`rm $limmaResTempFile`;
		`rm $plotDataTempFile`;
	}

	# TODO: MvA plots.
	


	return $limmaResHash;
}





# writeLimmaResults
#  - Write results from limma to tab-delimited test file.
sub writeLimmaResults {

	$_ = shift for my ($exptAcc, $arrayDesAcc, $limmaResHash);
	
	# Make filename
	my $limmaResFile = $exptAcc."-".$arrayDesAcc."-analytics.tsv";
	open(RESFILE, ">$limmaResFile") or die("Can't open $limmaResFile: $!\n");

	# write column headers
	printf(RESFILE "Design Elements");
	foreach my $contrastName (keys %{ $contrastHash }) {
		printf(RESFILE "\t$contrastName.p-value\t$contrastName.t-statistic\t$contrastName.log2foldchange");
	}

	# write statistics
	foreach my $designElement (keys %{ $limmaResHash }) {

		printf(RESFILE "\n$designElement");
		
		foreach my $contrastName (keys %{ $limmaResHash->{ $designElement }}) {

			my $statsString = join "\t", @{ $limmaResHash->{ $designElement }->{ $contrastName }};

			printf(RESFILE "\t".$statsString);
		}
	}

	close(RESFILE);
}
