# normalizeOneExperiment()
# 	- Reads in a batch of Affymetrix CEL files and produces a normalized data
# 	matrix via robust multichip averaging (RMA), or for Agilent 2-colour
# 	arrays, read in the raw data text files and produce a matrix of
# 	log-fold-changes and a matrix of average intensities.
# ARGUMENTS:
# 	- files <- a vector of raw data file names (CEL files for Affymetrix, *.txt files for Agilent 2-colour).
# 	- scans <- a vector of corresponding scan names (should have the same length as files)
# 	- outFile <- filename to store normalized data matrix
# 	- mode <- which type of normalization to do:
# 					- "2colour" : normalization for 2-colour Agilent arrays.
# 					- "oligo" : RMA normalization for Affymetrix single-colour arrays using oligo package.
# 					- "affy" : RMA normalization for Affymetrix single-solour arrays usinng affy package.
normalizeOneExperiment <- function(files, outFile, scans, mode) {

	# Use try() to catch errors
	error <- try({

		# If data is from 2-colour Agilent array
		if(mode == "2colour") {
			
			# load limma
			library(limma)

			# read files
			print("Reading in raw data")
			rg <- try({read.maimages(files, source="agilent")})
			# check it worked, if not, return the error.
			if(class(rg) == "try-error") { return(rg) }
			
			# Background correction.
			# Using method outlined in Section 6.1 of Limma Users Guide (25
			# September 2012 version), to adjust the foreground adaptively for
			# the background intensities, to produce strictly positive adjusted
			# intensities, i.e. avoiding negative or zero values in the
			# resulting adjusted intensities.
			print("Background correcting")
			rg <- backgroundCorrect(rg, method="normexp", offset=50)

			# Within-array normalization.
			# This gives the M-value and A-value for each gene on the array.
			# M = log2(Cy5) - log2(Cy3)
			# A = 0.5[log2(Cy5) + log2(Cy3)]
			print("Running within-array normalization")
			ma <- normalizeWithinArrays(rg, method="loess")

			# average for probe duplicates -- some probes are on the array
			# multiple times in different locations.
			print("Averaging duplicated probes")
			ma <- avereps(ma, ID=ma$genes$ProbeName)

			# data frames for M-values and A-values
			mValues <- data.frame(ma$M)
			aValues <- data.frame(ma$A)

			outFile_A = gsub(".txt", "_A.txt", outFile)

			print("Writing M-values and A-values")
			# write scan names to output files for M-values (outFile) and A-values (outFile_A).
			len = length(scans)
			write(c("Scan REF", scans), outFile, ncolumns = len+1, sep = "\t")
			write(c("Scan REF", scans), outFile_A, ncolumns = len+1, sep="\t")

			# write M-values and A-values, without column names because we already wrote them above.
			write.table(ma$M, file=outFile, sep="\t", quote=FALSE, row.names=TRUE, col.names=FALSE, append=TRUE)
			write.table(ma$A, file=outFile_A, sep="\t", quote=FALSE, row.names=TRUE, col.names=FALSE, append=TRUE)
		
			return("2-colour normalization complete")
		}
		
		
		# Use oligo package for pre-processing
		else if(mode == "oligo") {

			# Load oligo
			library(oligo)

			# Read in data from CEL files using read.celfiles().
			# read.celfiles() creates a Biobase object whose class varies
			# depending on the chip type: 
			# 	3'IVT array --> ExpressionFeatureSet
			# 	Gene ST array --> GeneFeatureSet
			# 	Exon ST array --> ExonFeatureSet
			# 	(SNP 5.0 or 6.0 array (use clrmm pkg) --> SnpCnvFeatureSet)
			print("Reading in CEL files")
			featureSet <- read.celfiles(files)
			print("Running rma")

			# Run RMA using rma().
			#
			# Robust Multipchip Averaging (RMA):
			# 	1) Model-based background adjustment:
			#       - remove noise signature from data, just leave signal.
			#       - any 'mismatch' probes are ignored.
			# 	2) Quantile normalization:
			# 		- Identifies a target distribution and then coerces the
			# 		intensity data from each array towards that distribution.
			# 		This means that data from all arrays in the dataset are now
			# 		comparable.
			# 	3) Summarization:
			# 		- We have intensities for individual probes; this step
			# 		takes these intensities and summarizes them per probe set
			# 		so that we get a single value for each one.
			# 		- For 3'IVT arrays, a probe set =~ a gene/transcript.
			# 		- For ST arrays, a probe set =~ an exon.
			#
			# For ST arrays (ExonFeatureSet or GeneFeatureSet), the
			# summarization target argument is needed:
			# 	- target="core" obtains gene-level summaries of expression for
			# 	"well-annotated" loci (annotated by RefSeq). 
			# 	- Exon (not Gene) arrays also have probes for "predicted genes"
			# 	and other less well-described loci. These can be accessed with
			# 	target="extended" (mRNA evidence) or target="full"
			# 	(bioinformatic prediction only).
			# 	- To summarize at the exon level, e.g. to look at expressions
			# 	for alternative splice forms, use target="probeset".
			# 	- For now we want to display information for genes, we are not
			# 	going as far as individual exons, so we'll use target="core".
			#
			# For non-ST arrays (ExpressionFeatureSet or SnpCnvFeatureSet), do
			# not specify target (dies if you do).
			#
			# rma() creates an ExpressionSet object which we'll call eSet.
			
			# If the first element of the vector returned by class() matches
			# "Gene" or "Exon", we've got an ST array...
			if( grepl("Gene", class(featureSet)[1]) || grepl("Exon", class(featureSet[1]) )) {
				eSet <- rma(featureSet, target="core")
			} 
			# ...otherwise, we haven't.
			else {
				eSet <- rma(featureSet) # no target
			}
			print("rma finished")
		} 
		

		# Use affy package for pre-processing
		else if(mode == "affy") {

			# Load affy
			library(affy)

			# Read in CEL files and normalize using justRMA().
			print("Running justRMA")
			eSet <- justRMA(filenames = files, celfile.path = NULL)
			print("justRMA finished")
		}
	

		# Now the pre-processing is done, either with oligo or affy, and the
		# data is stored in the eSet object. 
	
		# Next, make sure the scan names in the vector 'scans' are in the same
		# order as their corresponding samples in eSet.
				
		# relSort()
		# 	- Function to sort the scan names (in scans) so they are in the correct
		# 	order for the data in eSet.
		# ARGUMENTS:
		# 	- toSort <- a vector of things to reorder (scan names).
		# 	- keys <- a vector of CEL filenames.
		# 	- sortedKeys <- a vector of the same CEL filenames in the order they
		# 	are in the eSet object.
		relSort <- function(toSort, keys, sortedKeys) {

			# Copy contents of toSort to a new vector called sorted
			sorted <- toSort

			# For i in 1:length(sortedKeys):
			# 	- Find the position index of the CEL filename in the vector
			# 	'keys' that matches the CEL filename at position 'i' in the
			# 	vector 'sortedKeys';
			#	- Get the scan name at that position in 'toSort' and put it at
			#	position 'i' in the 'sorted' vector.
			for (i in 1:length(sortedKeys)) sorted[i] <- toSort[which(keys==sortedKeys[i])]
			
			# Return the reordered scan names
			return(sorted)
		}

		# shortFileNames is vector of CEL filenames with /path/to/files/ stripped off (i.e. just base names)
		shortFileNames <- gsub(".+/", "", files)
		
		# Create a vector of scan names in the correct order with relSort()
		scansSorted <- relSort(scans, shortFileNames, sampleNames(eSet))
		

		# Write the normalized data matrix in eSet to a tab-delimited textfile
		# (filename in 'outFile').
		
		# First make some headers for the columns.
		# How many columns do we need
		len <- length(sampleNames(eSet))
		# Write the scan names after sorting
		write(c("Scan REF", scansSorted), outFile, ncolumns = len+1, sep = "\t")
		
		# Now write the normalized and summarized expression values (accessed
		# via 'exprs(eSet)') to the outFile. The left-most column is the
		# probe set IDs. Leave out the column names (col.names=FALSE) as we
		# already put some in above.
		write.table(exprs(eSet), file = outFile, sep = "\t", quote = FALSE, row.names = TRUE, col.names = FALSE, append = TRUE)
	
	})

	# error catching -- return the error message if there was one (class(error)
	# returns "try-error"), otherwise return NULL.
	if ("try-error" == class(error)) {
		return(error)
	} else {
		return(NULL)
	}
}
