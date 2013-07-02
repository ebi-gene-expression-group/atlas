#!/usr/bin/perl
#
## POD documentation - main docs before the code
=pod

=head1 NAME
  Karyn Megy - 21-June-13
  kmegy@ebi.ac.uk
 
  gxa_setup_ereap_data.pl

=head1 SYNOPSIS
  Import data (reference, experiments etc.). 

=head1 DESCRIPTION
  Import data: 
        - download the reference, 
        - download experiment,
        - build bowtie indexes

  Later: generate the filter file, the contrast (XML) file.
                                                       
=head1 OPTIONS
  none

=head1 EXAMPLES
  gxa_setup_ereap_data.pl  -rel 72 -exp E-GEOD-31839 -pese se

=cut

use strict ;
use Getopt::Long ;


######################
#### Initialise $, @ and %
my ($help, $experiment, $ens_release, $pese, $organism) ; #arguments
my @command_args ;


#Default value needed in the help,
#So creating "default" variables 
my $download_ref_def = 1 ;
my $download_exp_def = 1 ;
my $bowtie_index_def = 1 ;

my $download_ref = $download_ref_def ;
my $download_exp = $download_exp_def ; 
my $bowtie_index = $bowtie_index_def  ;

######################
### Get arguments
GetOptions('help' => \$help,
           'Help' => \$help,
           'h'    => \$help,
           'H'    => \$help,
           'exp=s'   => \$experiment,
	   'rel=s'   => \$ens_release,
	   'pese=s'  => \$pese,
	   'org=s'   => \$organism,
	   'download_ref=s' => \$download_ref,
	   'download_exp=s' => \$download_exp,
	   'bowtie_index=s' => \$bowtie_index,
          ) or usage(\@command_args) ;

if ($help) { usage(\@command_args) ; }

if (!$ens_release || !$experiment) { print "!! WARNING !! Missing Ensembl release (-rel) or experiment ID (-exp)\n\n" ; $help = 1 ; } 
if ( ($pese !~ /^pe$/) && ($pese !~ /^se$/) ) { print "!! WARNING !! Incorrect or missing value for -pese. Should be 'pe' (for pair end) or 'se' (for single end)\n\n" ; $help = 1 ; } 
if ( (($bowtie_index == 1) || ($download_ref == 1)) && $organism eq "") { print "!! WARNING !! Organism required when downloading reference or building bowtie index (-org)\n\n" ; $help = 1 ; }

if ($help) { usage(\@command_args) ; exit ;}

#Get environment variables
my $ereap_clone = $ENV{'EREAP_CLONE'};
if (!$ereap_clone) { die "Set the enviroment variable \$EREAP_CLONE.\n" } 

#Get experiment sub-directory
my $dir ;
if ($experiment =~ /E-(\w+?)-\d+?/) {$dir = $1 ; }

#Download the reference
if ($download_ref == 1) { 
	print "[INFO] Downloading the reference sequence for $organism\n" ;
	chdir "$ereap_clone/data/reference/" ;

	##Download reference sequences for all the organisms
        #my $CMD = `egrep -v '#' $ereap_clone/data/reference/gxa.references.conf | xargs -I % echo \"$ereap_clone/data/reference/download_reference.sh $ens_release % clean\"` ;

	#Download 1 species reference 
	my $CMD = `egrep $organism $ereap_clone/data/reference/gxa.references.conf | grep -v '#' | xargs -I % echo \"$ereap_clone/data/reference/download_reference.sh $ens_release % clean\"` ;
	system($CMD) ;
	print "[INFO] Downloading reference done\n" ;
}

#Download the experiment
if ($download_exp == 1) {
        print "[INFO] Downloading the experiment files for $experiment\n" ;
	`cd $ereap_clone/data/raw_data` ;
	`./download_fastq.sh $ereap_clone/prod/  /ebi/ftp/pub/databases/microarray/data/experiment/$dir/$experiment/$experiment.sdrf.txt $pese` ; 
	print "[INFO] .... downloading experiment done\n" ;
}

#Build bowtie index
### capture the organism and the *cdna.all.fa file
if ($bowtie_index == 1) {
	print "[INFO] Build bowtie index\n" ;

	my $species_ref = `egrep $organism $ereap_clone/data/reference/gxa.references.conf | grep -v '#'` ;
	my ($species, $assembly, $ftp) = split (" ", $species_ref) ;
	$species = ucfirst($species) ;
	my $dna_file = "$species\.$assembly\.$ens_release\.cdna.all.fa.gz" ;
	my $gunzip_dna_file = "$species\.$assembly\.$ens_release\.cdna.all.fa" ;
	`gunzip $ereap_clone/data/reference/$organism/$dna_file` ;
	`$ereap_clone/scripts/ereap_map.sh bowtie2 bowtie2-build --offrate 3 $ereap_clone/data/reference/$organism/$gunzip_dna_file $ereap_clone/data/reference/$organism/$gunzip_dna_file` ;

	print "[INFO] .... building bowtie index done\n" ;
}


sub usage {
    my ($command_args) = @_;
    
    print "Your command line was:\t".
	"Ereap_Setup_Data.pl ".join("\t", @$command_args), "\n".
	"Compulsory parameters:\n".
	"\t-exp: experiment name (e.g. E-GEOD-20895)\n".
	"\t-rel: Ensembl release number (e.g. 72)\n".
	"\t-pese: pair end or single end reads? Define value 'pe' or 'se'\n\n".

	"Optional parameters, depending on the step to run/to skip:\n".
	"\t-download_ref: 0 or 1 (default $download_ref_def), download reference data (genome, DNA etc.) for the input species\n".
	"\t-download_exp: 0 or 1 (default $download_exp_def), download experiment\n".
	"\t-bowtie_index: 0 or 1 (default $bowtie_index_def), build the Bowtie index\n".
	"\t-organism: binomial species name, underscore separated (e.g. homo_sapiens). Required if bowtie_index or download_ref = 1\n" ;
}
