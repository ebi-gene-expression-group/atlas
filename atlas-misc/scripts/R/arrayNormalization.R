#!/usr/bin/env Rscript

# normalizeArrayData()
# 	- Reads in a batch of Affymetrix CEL files and produces a normalized data
# 	matrix via robust multichip averaging (RMA), or for Agilent 2-colour
# 	arrays, read in the raw data text files and produce a matrix of
# 	log-fold-changes and a matrix of average intensities.
# ARGUMENTS:
# 	- inFile <- name of TSV file containing mapping of assay names to raw data file names (CEL files for Affymetrix, *.txt files for Agilent).
# 	- mode <- which type of normalization to do:
# 					- "agil1" : normalization for 1-colour Agilent arrays.
# 					- "agil2" : loess normalization for 2-colour Agilent arrays.
# 					- "affy" : RMA normalization for Affymetrix single-colour arrays using oligo package.
# 	- outFile <- filename to store normalized data matrix
# 	- miRBaseFile <- filename with probeset IDs and latest miRBase mappings (if applicable).
normalizeArrayData <- function(inFile, mode, outFile, miRBaseFile) {

	# Use try() to catch errors
	error <- try({

		# Read file mapping assay names to raw data files
		assaysToDataFiles <- read.delim(inFile, stringsAsFactors = FALSE, header = TRUE)

		files <- assaysToDataFiles$Filename
		assayNames <- assaysToDataFiles$AssayName

		# If data is from a 1-colour or 2-colour Agilent array, we do the
		# pre-processing with limma. This consists of subsetting probes based
		# on miRBase mappings, background correction, within- or between-array
		# normalization, and averaging over duplicates.
		# For 1-colour data, normalized intensities are written to a single file.
		# For 2-colour data, log2(fols change)s are written to one file and
		# average intensities to another file.
		if(mode == "agil1" | mode == "agil2") {
			
			# load limma
			library(limma)

			# read files
			if(mode == "agil1") {
			
				print("Reading Agilent 1-colour data")
				# for 1-colour Agilent data we only have a green channel.
				dataSet <- try({read.maimages(files, source="agilent", green.only=TRUE)})
			}
			else if(mode == "agil2") {
				
				print("Reading Agilent 2-colour data")
				dataSet <- try({read.maimages(files, source="agilent")})
			}
			# check it worked, if not, return the error.
			if(class(dataSet) == "try-error") { return(dataSet) }
			
			# Check if there's a microRNA miRBase probeset list and if so,
			# subset based on that list.
			if(miRBaseFile != 0) { dataSet <- subsetProbes(dataSet, miRBaseFile) }
			
			# Background correction.
			# Using method outlined in Section 6.1 of Limma Users Guide (25
			# September 2012 version), to adjust the foreground adaptively for
			# the background intensities, to produce strictly positive adjusted
			# intensities, i.e. avoiding negative or zero values in the
			# resulting adjusted intensities.
			# NB: this might get masked by oligo package's backgroundCorrect
			# method if they are both loaded (here they shouldn't be so don't
			# call limma's explicitly).
			print("Background correcting")
			dataSet <- backgroundCorrect(dataSet, method="normexp", offset=50)
			
			if(mode == "agil1") {
				
				# Between-array normalization for 1-colour.
				normData <- normalizeBetweenArrays(dataSet, method="quantile")
			}

			else if(mode == "agil2") {
				# Within-array normalization for 2-colour.
				# This gives the M-value and A-value for each gene on the array.
				# M = log2(Cy5) - log2(Cy3)
				# A = 0.5[log2(Cy5) + log2(Cy3)]
				print("Running within-array normalization")
				normData <- normalizeWithinArrays(dataSet, method="loess")
			}

			# average for probe duplicates -- some probes are on the array
			# multiple times in different locations.
			print("Averaging duplicated probes")
			normData <- avereps(normData, ID=normData$genes$ProbeName)
			
			# Write results to file
			if(mode == "agil1") {
				# For single channel Agilent data, this is just the
				# quantile-normalized expressions (analogous to RMA-normalized
				# expressions from Affymetrix data).
				
				# Make a dataframe with probe IDs and normalized expressions.
				normExprs <- data.frame(cbind(normData$genes$ProbeName, normData$E))
				# Add assayNames as column headings
				colnames(normExprs) <- c("DesignElementAccession", assayNames)

				print("Writing normalized expressions matrix")
				write.table(normExprs, file=outFile, sep="\t", quote=FALSE, row.names=FALSE)
				
				# Now files are written, function returns and script exits.
				return("Agilent 1-colour normalization finished")
			}
			else if(mode == "agil2") {
				# For two channel Agilent data, this is logFCs (M-values) and
				# average intensities (A-values).

				# data frames for M-values and A-values. Put rownames (design
				# element IDs) as the first column and name it
				# "DesignElementAccession".
				mValues <- data.frame(normData$M)
				mValues <- data.frame(cbind(rownames(mValues), mValues))
				colnames(mValues) <- c("DesignElementAccession", assayNames)
				aValues <- data.frame(normData$A)
				aValues <- data.frame(cbind(rownames(aValues), aValues))
				colnames(aValues) <- c("DesignElementAccession", assayNames)

				# Filename for A-values
				outFile_A = paste(outFile, ".A-values", sep="")

				print("Writing M-values and A-values")
				
				# write M-values and A-values, without column names because we already wrote them above.
				write.table(mValues, file=outFile, sep="\t", quote=FALSE, row.names=FALSE)
				write.table(aValues, file=outFile_A, sep="\t", quote=FALSE, row.names=FALSE)
		
				# Now files are written, function returns and script exits.
				return("Agilent 2-colour normalization complete")
			}
		}
		# For Affymetrix data, use oligo package for pre-processing: this is
		# background adjustment, normalization and probe set summarization (see
		# below for details).
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
		# Now the pre-processing is done, either with oligo or affy, and the
		# data is stored in the eSet object. 
	
		# Next, make sure the scan names in the vector 'assayNames' are in the same
		# order as their corresponding samples in eSet.
		# shortFileNames is vector of CEL filenames with /path/to/files/ stripped off (i.e. just base names).
		shortFileNames <- gsub(".+/", "", files)
		
		# Create a vector of scan names in the correct order with relSort().
		assayNamesSorted <- relSort(assayNames, shortFileNames, sampleNames(eSet))
		
		# Normalized expressions to data frame.
		normExprs <- exprs(eSet)
		# Add row names as a column and give it col name "DesignElementAccession".
		normExprs <- data.frame(cbind(rownames(normExprs), normExprs))
	    colnames(normExprs) <- c("DesignElementAccession", assayNamesSorted)

		print("Writing normalized expressions matrix")
		# Write normalized data to file.
		write.table(normExprs, file = outFile, sep = "\t", quote = FALSE, row.names=FALSE)
	})

	# error catching -- return the error message if there was one (class(error)
	# returns "try-error"), otherwise return NULL.
	if ("try-error" == class(error)) {
		return(error)
	} else {
		return(NULL)
	}
}




# subsetProbes()
#  - For microRNA, we only want to normalize using probesets that are mapped to
#  the latest release of miRBase (www.mirbase.org). Subset the data here.
# ARGUMENTS:
# 	- dataSet <- for now either EListRaw or RGList object.
# 	- miRBaseFile <- filename for miRBase mappings.
subsetProbes <<- function(dataSet, miRBaseFile) {

	print(paste("There are", nrow(dataSet), "rows of data"))
	
	print(paste("Subsetting data for probes found in", miRBaseFile))

	# Read file with miRBase mappings into a data frame.
	miRBaseProbeMapping <- read.delim(miRBaseFile, stringsAsFactors = FALSE)
	
	# Probesets can be repeated; take unique set from design_element column in
	# miRBase mapping data frame.
	design_element <- unique(miRBaseProbeMapping$design_element)

	# Subset the data, taking only probesets from miRBase mapping file.
	# Agilent data is read in to one of two classes:
	# 	- "EListRaw" : 1-colour
	# 	- "RGList" : 2-colour
	if(class(dataSet) %in% c("EListRaw", "RGList")) { 
		
		# Subset dataSet using design_element column from miRBase mapping file.
		dataSet <- dataSet[which(dataSet$genes$ProbeName %in% design_element), ]
		
		# Check if we have any rows left after subsetting.
		if(nrow(dataSet) == 0) {
			# If not, that means no probe names matched any of the values in
			# the design_element column, so die.
			stop("No probes in raw data match values in design_element column of miRBase mapping file. Cannot proceed.")
		}
	}
	# If it's not an "ElistRaw" or "RGList" object we can't handle it (yet).
	else { print("Don't know how to subset for this type of array") }

	print(paste("After subsetting there are", nrow(dataSet), "rows remaining"))

	# TODO: add something here to subset for Affymetrix arrays.
	# Need to think about how to map probes to probesets because before
	# normalization probeset info is not available?
	
	return(dataSet)
}


# relSort()
# 	- Function to sort the scan names (in assayNames) so they are in the correct
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

# Run with arguments if there are any, otherwise don't do anything.
args <- commandArgs(TRUE)
if(length(args) > 0) {
	do.call(normalizeArrayData, as.list(args))
}
