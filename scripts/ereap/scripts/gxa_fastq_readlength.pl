#!/usr/bin/perl

# POD documentation - main docs before the code
=pod

=head1 NAME
  Karyn Megy - 15-August-12
  kmegy@ebi.ac.uk
  gxa_fastq_readlength.pl

=head1 SYNOPSIS
  Estimate the read length of a fastq file
  
=head1 DESCRIPTION
  Extract the first thousand lines from a fastq files
  And estimate the read length

=head1 OPTIONS
  None

=head1 EXAMPLES
  gxa_fastq_readlength.pl file.fastq

=cut

use strict ;
use Switch ;

#autoflush
$| =  1;

#Initialise $, @ and %
my %H_length ;
my %H_perclength ;

#Check for argument and print help
# (try to) cater for all "help" calls  (-H, --H, -Help, --Help, -HELP etc. case insentive)
if ( ($ARGV[0] eq "") || ($ARGV[0] =~ /^-*[hH]$/) || ($ARGV[0] =~ /^-*help$/i) ) {
    print "This program estimate the read length of a fastq file (1,000 reads)\n".
    "\tE.g.: perl gxa_fastq_readlength.pl ERR030858.filter1.fastq -verbose 1\n".
    "\t\tverbosity: 1 (output=sentance) or 0 (output=number). Default is verbosity 1\n" ;
    exit 1;
}

#Set default verbosity
#1: output is a sentance, 0: output is a digit corresponding to the read length
my $verbose  = 1 ;

my $cpt = 0 ;   

#Test verbosity
if ($ARGV[1] ne "") { $verbose = $ARGV[1] ; }

##Check fastq integrity, at least until it determines the encoding:
#        - 1st line of a record starts by @? If not, point culprit line and EXIT!
#        - 3rd line of a record starts by +? If not, point culprit line and EXIT!
##Read length: get length of every 2nd line (sequence)
open (F, $ARGV[0] || die ("Can't open fastq file $ARGV[0]\n")) ;

while ((my $line=<F>) && ($cpt < 1000)) {
    chomp $line ;
    ##Is the file consistent and not corrupted?
    #Testing 1st line of every record - should start with @
    if ( (($.+3) % 4 == 0) && ($line !~ /^\@/) ){
	print STDERR "ERROR: Not a fastq file, or corrupted fastq file. See: see http://maq.sourceforge.net/fastq.shtml ## Record not starting with @\n".
	    "\t(l.$.) $line\n" ; 
	exit 1 ; 
    }

    #Getting length of the 2nd line
    if ( (($.+2) % 4 == 0) && ($line =~ /[ATCGNatcgn]/) ){
	$H_length{length($line)}++ ;
	$cpt++ ;

	#Print for test
        #print $line."\n" ;
	#print "Long:".length($line)." ($H_length{length($line)} / $cpt)\n" ;
    }

    #Testing 3rd line of every record - should start with +
    if ( (($.+1) % 4 == 0) && ($line !~ /^\+/) ){ 
	print STDERR "ERROR: Not a fastq file, or corrupted fastq file. See: see http://maq.sourceforge.net/fastq.shtml ## Every 3rd line of a record should start with +\n".
	      "\t(l.$.) $line\n" ; 
	exit 1; 
    }
}
close F ;

##Estimate read length - exact value
# Considered using average length but then only in special cases should you have a large variety of lengths
#(e.g.: after QC)


my $flag ; my $sum ;
foreach my $ReAd (sort keys %H_length) {
    my $perc = sprintf("%.2f", ($H_length{$ReAd}/$cpt)*100) ;

    #Store info. in case no length > 95% reads
    $H_perclength{$perc} = $ReAd ;

    #Stop if most reads are same length
    if ($perc > 90) { print " $perc% reads with $ReAd bp length\n" ; $flag = 1 ; } #exit ; }
}

#In case no majority of read length, calculate average length
if ($flag ne 1) {
    foreach my $PeRc (sort {$a<=>$b} keys %H_perclength) { print "$H_perclength{$PeRc} bp:  $PeRc % reads\n" ; }
    foreach my $ReAd (sort keys %H_length) { $sum += $ReAd * $H_length{$ReAd} ; }

    #Calculate average length
    my $avg = sprintf("%.2f", $sum /$cpt) ;
    print "Average length: $avg\n" ;
}
