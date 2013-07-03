#!/usr/bin/perl

# POD documentation - main docs before the code
=pod

=head1 NAME
  Karyn Megy - 07-June-13
  kmegy@ebi.ac.uk
  gxa_fastq_qualityencoding.pl

=head1 SYNOPSIS
  Estimate the quality encoding (Q33 or Q64) of a fastq file
  ... and generate the distribution of ASCII characters.   

=head1 DESCRIPTION
  
  (More sophisticated version of the previous program of the same name)

  Estimate the quality encoding (Q33 or Q64) of a fastq file
  ... allow to take a subset of lines, or all of them (in case parsing a sample file, for ex.)

  See: http://en.wikipedia.org/wiki/FASTQ_format 
  Simon Anders (CSAMA 2012): as of beg. of 2012, the encoding should always be Q33


  SSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS.....................................................
  ..........................XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX......................
  ...............................IIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII......................
  .................................JJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJ......................
  LLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLL....................................................
  !"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\]^_`abcdefghijklmnopqrstuvwxyz{|}~
  |                         |    |        |                              |                     |
 33                        59   64       73                            104                   126

 S - Sanger        Phred+33,  raw reads typically (0, 40)
 X - Solexa        Solexa+64, raw reads typically (-5, 40)
 I - Illumina 1.3+ Phred+64,  raw reads typically (0, 40)
 J - Illumina 1.5+ Phred+64,  raw reads typically (3, 40)
 L - Illumina 1.8+ Phred+33,  raw reads typically (0, 41)

 For each sequence, store the score at each position and:
	- estimate the quality score encoding,
	- check wheter there are any characters NOT coding for that score,
	- if so, return them. 

=head1 OPTIONS
  - Verbose, value 0 or 1
  0: return only the value 33 or 64, to be used directly by other programs
  1: return a sentence giving the encoding, % and count of each Q33 or Q64, and any offending characters

 - sample
 Parse a sample of the file only (sample = sequence, not lines)  

=head1 EXAMPLES
  gxa_fastq_qualityencoding.pl file.fastq -verbose 1 -sample 50000
  gxa_fastq_qualityencoding.pl file.fastq

=cut

#use strict ;
use Switch ;
use English ;
use Getopt::Long ;

my $start = time ;

#Initialise $, @ and %
my $Q33 = "!\"#\$%&'()*+,-./0123456789:" ;
my $overlap = ";<=>?\@ABCDEFGHIJ" ;
my $Q64 = "KLMNOPQRSTUVWXYZ[\\]^_`abcdefgh" ;

my %Hfastq_file_char ;
my %Hfastq_file_encod ;

my %H_fastq ;
my (%Hash, %Hash2) ; #subroutine variables. Need them to be global.

my $qual ;

#Print help if needed
# (try to) cater for all "help" calls  (-H, --H, -Help, --Help, -HELP etc. case insentive)

if ( ($ARGV[0] eq "") || ($ARGV[0] =~ /^-*[hH]$/) || ($ARGV[0] =~ /^-*help$/i) ) {
    print "This program identifies quality encoding for a fastq file.".
	  "Compulsory arguments:".
	  "\tfastq file\n".
          "Optional arguments:".
	  "\tverbose mode: return 33 or 64 (verbose 0) or a full sentance (verbose 1, default)".
	  "\texit mode: don't die if inconsistent quality encoding (exit 0, default) or die (exit 1)\n".
	  "\tsample size (preferably multiple of 4): parse a limited number of reads in a fastq file\n".
    "\tE.g.: perl gxa_fastq_qualityencoding.pl -f ERR030858.filter1.fastq -exit 1\n".
    "        perl gxa_fastq_qualityencoding.pl -f ERR030858.filter1.fastq -verbose 0 -sample 10000\n" ;
    exit 1;
}

# Get arguments
my $fastq ; 
my $verbose = 1 ;
my $exit = 0 ;
my $sample = 0 ;
&GetOptions ( 'file:s'    => \$fastq,
	      'verbose:s' => \$verbose,
              'exit:s' => \$exit,
              'sample:s' => \$sample,
	    ) ;

if ($verbose !~ /[01]/) {
    print "Please provide an appropriate verbose mode: 0 (returns 33 or 64) or 1 (returns full sentence).\n" ;
}

if ($exit !~ /[01]/) {
    print "Please provide an appropriate exit mode: 0 (warning only if inconsistencies found) or 1 (die when inconsistencies).\n" ;
}


## Fill in a hash with generic fastq features (encoding score / characters)
#
# Calling subroutines with string, encoding score, ASCII position start
# produce: $Hfastq{$character}{ENCODING} = Q33, Q64 or OVERLAP
#          $Hfastq{$character}{POSITION} = nb. between 33 -> 126
#
# and: $Hquality_char{$quality}{$character} = 1 (so can parse on quality)
#
#returned values are global so any call to that subroutine will 'add' to the existing variable (hash)
#####################
my ($Href_fastq, $Href_quality_char) = &HFastq_populate($Q33, "Q33", 33) ;
($Href_fastq, $Href_quality_char) = &HFastq_populate($overlap, "OVERLAP", 59) ;
($Href_fastq, $Href_quality_char) = &HFastq_populate($Q64, "Q64", 75) ;

#Get the hashes, rather than their references, 
#Making life easier later on!
%H_fastq = %$Href_fastq ;
%H_quality_char = %$Href_quality_char ;

##Print to check
#foreach my $char (sort keys %H_fastq) {
#	print "CHAR: $char\n" ;
#	print "\tEncoding: $H_fastq{$char}{ENCODING}\n" ;
#	print "\tPosition: $H_fastq{$char}{POSITION}\n" ;	
#}

#foreach my $qual (sort keys %H_quality_char) {
#       print "QUALITY: $qual\t" ;
#       foreach my $asc (keys %{$H_quality_char{$qual}}) { print "$asc " ; }
#       print "\n" ;
#}	
#exit ;

## Parse fastq file
###################
##Check fastq integrity:
##        - line number = multiple of 4? If not, continue anyway...
##        - 1st line of a record starts by @? If not, point culprit line and EXIT!
##        - 3rd line of a record starts by +? If not, point culprit line and EXIT!
###Encoding: - read every 4th line of the file (quality score),
##           - store in category Q33, overlap or Q64, 
##           - once Q33 or Q64 reach 1,000, do some checking and give the encoding
##
### Line number
## !! Time consuming !!
##Expected number of lines (multiple of 4)?
#my $rez = `wc -l $ARGV[0]` ;
#my ($line_nb, $file_name) = split(" ", $rez) ;
#if ($line_nb % 4 != 0) {
#    print "Not a fastq file, or corrupted fastq file. See: see http://maq.sourceforge.net/fastq.shtml ## $line_nb lines, not a multiple of 4 (".$line_nb/4 ." seq.).\n" ;
#    #exit ; 
#    #}
#
#
open (F, $fastq || die ("Can't open fastq file: $fastq. Did you use -f tag?\n")) ;
while (my $line=<F>) {
    chomp $line ;

    ##Is the file consistent and not corrupted?
    #Testing 1st line of every record - should start with @
    if ( (($.+3) % 4 == 0) && ($line !~ /^\@/) ){
	    print "$fastq: not a fastq file, or corrupted fastq file. See: see http://maq.sourceforge.net/fastq.shtml ## Record not starting with @\n".
	    "\t(l.$.) $line\n" ; 
	    exit 1;
    }

    #Testing 2nd line of every record - should be ATCGNatcgn and ambigous code
    #... not doing it to speed up process. Assuming that if 1st and 3rd lines are OK, then the rest should be OK as well

    #Testing 3rd line of every record - should start with +
    if ( (($.+1) % 4 == 0) && ($line !~ /^\+/) ){ 
	    print "$fastq: not a fastq file, or corrupted fastq file. See: see http://maq.sourceforge.net/fastq.shtml ## Every 3rd line of a record should start with +\n".
	      "\t(l.$.) $line\n" ; 
	    exit 1;
    }

    #If 4th line of a record (quality score)
    if ($. % 4 == 0) {
	    #Store the line in an array
	    my @A_line = split("", $line) ;
	  
	#At each position, store the score in a hash
	foreach my $a (0..$#A_line) {
		#print $A_line[$a] ;
		$Hfastq_file_char{$A_line[$a]}++ ;
	
		#Get encoding
		my $encod = $H_fastq{$A_line[$a]}{ENCODING} ;
		$Hfastq_file_encod{$encod}++ ;	
	}
     }
     #print "\n" ;

     #If sample option ON,
     #and more than $sample entries in one category: STOP 
     if ($sample > 0) {
	if ( ($Hfastq_file_encod{"Q33"} > $sample) || ($Hfastq_file_encod{"Q64"} > $sample) ) { exit ; }
     }
}
close F ;


## Do the stats
###############
#Define quality
#Should be Q33 or Q64, not both!                 
my ($Q33_count, $OVLP_count, $Q64_count) = &stats(\%Hfastq_file_encod) ;

if ($Q33_count == 0 && $Q64_count != 0) { $qual = "Q64" ; }
elsif ($Q33_count != 0 && $Q64_count == 0) { $qual = "Q33" ; }

if ($qual =~ /[Q3364]/) {
    if ($verbose == 1) {
	    print "$fastq: $qual encoding (Q33: $Q33_count% ($Hfastq_file_encod{Q33}), overlap: $OVLP_count%, Q64: $Q64_count% ($Hfastq_file_encod{Q64}))\n" ;
    } elsif ($verbose == 0) {
	    $qual =~ s/Q// ;
	    print "$qual\n" ;
    }    

} else {
    print "ERROR: Cannot determine encoding for fastq file $fastq. Mixed scores: Q33 ($Q33_count %) and Q64 ($Q64_count %) [overlap $OVLP_count%]\n";

    #Print offending characters by ASCII position 		
    if ($Q33_count < $Q64_count) {
	print "ERROR\tOffending characters: " ;
	foreach $ASCII (keys %{$H_quality_char{Q33}}) {	
		if (exists $Hfastq_file_char{$ASCII}) { print "$ASCII ($Hfastq_file_char{$ASCII} occurences)\t" ; }
	} print "\n" ;
	
    } elsif ($Q64_count < $Q33_count) {
        print "\tOffending characters: " ;
	foreach $ASCII (keys %{$H_quality_char{Q64}}) {   
                if (exists $Hfastq_file_char{$ASCII}) { print "$ASCII ($Hfastq_file_char{$ASCII} occurences)\t" ; }
        } print "\n" ;
    }	    

    #Print all characters by ASCII position	
    #print "Quality encoding characters (occurence; quality encoding):" ;
    #foreach my $charact ( sort { $H_fastq{$a}{POSITION} cmp $H_fastq{$b}{POSITION} } keys %H_fastq) {
    # 	print "$H_fastq{$charact}{ENCODING}\t$charact\t$Hfastq_file_char{$charact}\n" ;
    #}

    if ($exit == 1) { exit 1; }
}

#Time the whole process
#my $now = time - $^T ;
#printf("\n%%%%%%\nTotal running time: %02d:%02d:%02d\n\n", int($now / 3600), int(($now % 3600) / 60), int($now % 60));

#################
## subroutines ##
#################
sub stats() {
    my %H = %{$_[0]} ;
    my $Total = 0 ;
    foreach my $t (keys %H) { $Total += $H{$t} }  #Total number of scores stored

    my $Q33_nb = 0 ;
    my $ovlp_nb = 0;
    my $Q64_nb = 0;

    if ($Total > 0) {
	$Q33_nb = sprintf("%.2f", ($H{"Q33"}*100)/$Total) ;
	$ovlp_nb = sprintf ("%.2f", ($H{"OVERLAP"}*100)/$Total) ;
	$Q64_nb = sprintf("%.2f", ($H{"Q64"}*100)/$Total) ;
    }

    ##print "Total: $Total, $Q33_nb, $ovlp_nb, $Q64_nb\n" ;
    return($Q33_nb,$ovlp_nb,$Q64_nb) ;
}

sub HFastq_populate {
        my $string = $_[0] ;
        my $encoding = $_[1] ;
        my $position = $_[2] ;
        %Hash ; #return hash. Global variable so can 'add up'.
	%Hash2 ; #2nd return hash. Global variable so can 'add up'.
		
        my @A_string = split(//, $string);
        foreach my $a (0..$#A_string) {
        	$Hash{"$A_string[$a]"}{ENCODING} = $encoding ;
       		$Hash{"$A_string[$a]"}{POSITION} = $position + $a ;
		$Hash2{$encoding}{"$A_string[$a]"} = 1;
                #my $X = $a + $position ; print "[$X] $A_string[$a]\n" ; #print to check
        }
        return (\%Hash, \%Hash2) ; #return a hash reference
}
