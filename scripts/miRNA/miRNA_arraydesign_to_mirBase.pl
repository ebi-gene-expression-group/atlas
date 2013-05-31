#!/usr/bin/perl
#
## POD documentation - main docs before the code
=pod

=head1 NAME
  Karyn Megy - 23-May-13
  kmegy@ebi.ac.uk
  miRNA_arraydesign_to_mirBase.pl

=head1 SYNOPSIS
  Links probe set IDs/reporter names from a miRNA array design adf files to the current miRBase IDs

=head1 DESCRIPTION
  Links probe set IDs/reporter names from a miRNA array design adf files to the current miRBase IDs
  + percentage of IDs kept/discarded

  1. Get the list of previous/new IDs in miRBase (Aliases.txt file)
  2. Get the probe set IDs & reporter names for the array design (adf file) 
  3. Link latest miRBAse ID with reporter name using the probe set ID


=head1 OPTIONS
 none

=head1 EXAMPLES

  miRNA_arraydesign_to_mirBase.pl -adf input_adf_file.adf -out output_file_name.txt

  E.g.: 
  scripts/miRNA_arraydesign_to_mirBase.pl -adf A-MEXP-717/A-MEXP-717.adf.txt -out A-MEXP-717/A-MEXP-717_reporter_mirbase.txt

=cut

use strict ;
use Getopt::Long ;

##Declare variables, tables and hashes
my ($file_adf, $file_out, $file_out_stats) ; # input adf file and output files
my $file_aliases ; # miRBase file containing the deprecated & current accession/identifiers. See below for more information. 
my %H_mirbase_aliases ; # Link miRBase accession/identifiers from all miRBase releases to the current (the latest) miRBase identifier


## Collect arguments
GetOptions('adf=s' => \$file_adf,
	   'out=s' => \$file_out ) ;

if (!$file_out) { die "Missing output file (-out)!\n" ; } 

if ($file_out =~ /\.txt$/) { $file_out_stats = $file_out ; $file_out_stats =~ s/txt/log/ ; } 
else { $file_out_stats = $file_out.".log" ; }


##########
## miRBase aliases file 
##  --> ftp://mirbase.org/pub/mirbase/CURRENT/aliases.txt.gz
## Contains the list of miRBase accessions (column 1) and their deprecated/current identifiers (column 2, semi-colon separated, last one=current)
##
## Link the current ID to the deprecated ones, 
## Store in a hash.
## For convenience, if there is no deprecated ID, the current ID will be associated to itself.
###########
#Download the aliases file from miRBase 
#ftp://mirbase.org/pub/mirbase/CURRENT/aliases.txt.gz
#Connection from ebi-001.ac.uk OK, but not from lime.ebi.ac.uk. 

my $aliases = "aliases.txt" ;
if (-e $aliases) { $file_aliases = 'aliases.txt' ; }  # avoid downloading if already exists

if (!$file_aliases) {
	print STDERR "Retrieving aliases file from the lastest miRBase release (aliases.txt) - will only work if from ebi-00x!\n" ;
	`wget ftp://anonymous:anonymous\@mirbase.org/pub/mirbase/CURRENT/aliases.txt.gz` ;
	`gunzip aliases.txt.gz` ;
	$file_aliases = 'aliases.txt' ;
}

open (Faliases, $file_aliases) || die "Can't open miRBase aliases file $file_aliases\n" ;
while (my $line=<Faliases>) {
	chomp $line ;
	my ($ACC, $IDs) = split("\t", $line) ;
	my @A_IDs = split (";", $IDs) ; 
	my $IDCurrent = $A_IDs[-1] ;  #current ID is ALWAYS the last one

	##Store - associate ACC/IDs to the current ID (ACC: MIMATxxx, ID: hsa-xxx)
	## ACC not necessary for now but might be if another array design uses them instead of IDs.
	$H_mirbase_aliases{$ACC} = $IDCurrent ;
	for my $a (0..$#A_IDs) { $H_mirbase_aliases{$A_IDs[$a]} = $IDCurrent ; } 
}	
close Faliases ;

##Print for testing
#foreach my $ID (sort keys %H_mirbase_aliases) { print "$ID $H_mirbase_aliases{$ID}\n" ;}


##########
## design array adf file (input file)
## 
## Identify the column with the miRBase identifier (usually 6th or 7th one)
## Get the reporter name and the miRBase ID from that file, 
## find the corresponding latest miRBase ID from %H_mirbase_aliases
## And print. 
###########
#Identify column with miRBase identifiers
my $col = &fetchIDcolumn($file_adf) ;

#Parse adf file
my ($cpt_found, $cpt_lost) ; #counters

open (Fadf, $file_adf) || die "Can't open adf file $file_adf\n" ;
open (Fout, ">$file_out") ;
open (Fout_stats, ">$file_out_stats") ;

#Print the header
print Fout "adf reporter name\tmiRBase current ID\n" ;

while (my $line=<Fadf>) {

	chomp $line ;
	my @A_line = split ("\t", $line) ;

	#adf file: "reporter name" is ALWAYS 5th column ([4] for array position)	
	#miRBase identifier column identified in subroutine &fetchIDcolumn (-1 for array position)
	my $reporter = $A_line[4] ; 
	my $ID_mirbase = $A_line[$col-1] ;

	#Get the current miRBase ID
	my $ID_mirbase_current = $H_mirbase_aliases{$ID_mirbase} ;		

        #If exists, print!
    	#Do some stats
    	if ($ID_mirbase_current ne "") {
        	print Fout "$reporter\t$ID_mirbase_current\n" ;
		$cpt_found++ ;
	} else { $cpt_lost++ ; }	

	#Print for testing
	#print "\nLINE: $line<<\n" ; 
	#print "\t$reporter -> $ID_mirbase -> $ID_mirbase_current\n" ;
}

close Fadf ; 

#Print the stats
my $cpt_tot = $cpt_found + $cpt_lost ;
$a = sprintf ("# %.2f %% identifiers from $file_adf found in the latest miRBase release\n", ($cpt_found/$cpt_tot)*100) ;
$b = sprintf ("# %.2f %% identifiers from $file_adf lost!\n", ($cpt_lost/$cpt_tot)*100  ) ;

print Fout_stats "## STATS\n$a$b" ;
print "##### STATS\n$a$b" ;

close Fout ;
close Fout_stats ;

## Subroutine
#############
#
#Check in which column is the miRBase identifier 
#Parse 50 'Experimental' lines, identify column - same column 95% times? Stop. 
sub fetchIDcolumn {
	my $file = $_[0] ;
	my $cpt = 0 ; #counter
	my %H_cl ; #%H column counter 	
	my $column ; #variable to return

	my $max_check = 50 ; #number of lines to check

	open (F, $file) || die "Can't open subroutine file $file\n" ;	
	while ( (my $l=<F>) && ($cpt < $max_check) ) {	
			
		if ($l =~ /Experimental/) { #only consider "Experimental" lines
    	            	$cpt++ ;
			my @A_line = split ("\t", $l) ;
		
			for my $a (0..$#A_line) { 
				if ($A_line[$a] =~ /^\w{3}-\w{3}-\+?/) { $H_cl{$a}++ ;}
			}
		}
	}
	close F ;

	#Check which column contains the ID
	foreach my $A (keys %H_cl) {
		if ($H_cl{$A} >= $max_check*0.95) { $column = $A ; }
	}

	#Array position (start at 0), so real column is +1
	$column++ ;

	#If no identifier column found, make some noise and die
	if ($column eq "") { die "ERROR! Could not identify column with miRBase identifiers in $file\n" ; }
	return $column ;
}










