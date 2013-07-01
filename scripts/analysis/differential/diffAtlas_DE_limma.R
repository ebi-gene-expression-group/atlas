#!/ebi/microarray/home/biocep/local/lib64/R/bin/Rscript

# diffAtlas_DE_limma.R
# Microarray differential expression statistics computation for the
# Differential Atlas.


# diffAtlas_DE_limma()
# - Differential expression analysis (2-group comparison) using limma.
# Arguments:
# 	- normExprsFile <- matrix of normalized and summarized expression values.
#	- refAssays <- comma-separated list of assay accessions in reference assay group.
#	- testAssays <- comma-separated list of assay accessions in test assay group.
#	- resFile <- filename for results.
#	- plotDataFile <- filename for data for MvA plot.
#	- aValuesFile <- filename for matrix of A-values; only needed for 2-colour designs. Default is NULL.
diffAtlas_DE_limma <<- function(normExprsFile, refAssays, testAssays, resFile, plotDataFile, aValuesFile = NULL) {

	e <- try({
		
		# load limma.
		library(limma)
		
		# Read expressions (M-values/log-fold-changes for 2-colour).
		print(paste("Reading", normExprsFile))
		# R's reserved characters are changed to "." because check.names=TRUE
		# in the read.delim() function.
		normExprs <- read.delim(normExprsFile, stringsAsFactors=FALSE)
		rownames(normExprs) <- normExprs[,1]
		normExprs[,1] <- NULL

		# Make vectors of assay accessions.
		# Assay groups come in like e.g.:
		# 	assay 1<SEP>assay 2<SEP>assay 3
		# Split on "<SEP>" -- we use this as a separator because it is very
		# unlikely to crop up in actual assay names.
		# Use make.names to convert reserved characters to "." so that they
		# match the column headings from reading in the normalized expressions
		# above. 
		refAssays <- make.names(unlist(strsplit(refAssays, "<SEP>")))
		testAssays <- make.names(unlist(strsplit(testAssays, "<SEP>")))


		# Provisions for 2-colour designs:
		#	- If aValuesFile is not NULL, this means it's a 2-colour design.
		#	- Create an MAList object out of the M-values in normExprs and
		#	A-values in aValues. This is essentially equivalent to the
		#	ExpressionSet created for Affymetrix data.
		#	- Fit LM, get DE stats. Contrast is implied, do not need to specify contrasts matrix.
		#	- Write results and data for MvA plot -- think this part could be
		#	the same as for Affymetrix data?
		if(!is.null(aValuesFile)) {

			# Sort ref and test assays so they are in the same order.
			refAssays <- sort(refAssays)
			testAssays <- sort(testAssays)
			
			# Read in A-values
			aValues <- read.delim(aValuesFile, stringsAsFactors=FALSE)
			rownames(aValues) <- aValues[,1]
			aValues[,1] <- NULL

			# Strip off .Cy* endings to check the assay names are the same and
			# to subset columns from normExpr and aValues.
			refAssaysNoCy <- gsub(".Cy\\d$", "", refAssays)
			testAssaysNoCy <- gsub(".Cy\\d$", "", testAssays)

			# [double-]check that we have the same assay names for test and ref
			# without .Cy* endings.
			# Compare sorted values at each index position using
			# any(). If there are any mismatches this returns TRUE.
			if(any(refAssaysNoCy != testAssaysNoCy)) {
				stop("Differing assay names found in test and reference assay groups after removing \".Cy3\" and \".Cy5\". Please verify this experiment has a two-colour design.")
			}
			
			
			# Create "targets" dataframe for design matrix.
			# Vector of ref and test for Cy3-labelled samples.
			cy3 <- rep(NA, length(refAssays))
			cy3[grep(".Cy3$", refAssays)] <- "ref"
			cy3[grep(".Cy3$", testAssays)] <- "test"
			# Vector of ref and test for Cy5-labelled samples.
			cy5 <- rep(NA, length(refAssays))
			cy5[grep(".Cy5$", refAssays)] <- "ref"
			cy5[grep(".Cy5$", testAssays)] <- "test"
			# Check in case any of cy3 or cy5 are still NA -- something's wrong if so.
			if(any(is.na(cy3)) || any(is.na(cy5))) {
				stop("Some assays do not have an accession ending with \".Cy3\" or \".Cy5\". Please verify this experiment has a two-colour design.")
			}
			# Create "targets" dataframe like:
			#	-----------
			#		Cy3	Cy5
			#	A1	ref	test
			#	A2	test	ref
			#	A3	ref	test
			#	A4	test	ref
			#	-----------
			targets <- data.frame(Cy3=cy3, Cy5=cy5)
			# Rownames are assay names and should be the same as the column
			# headings in dataframes of M-values and A-values.
			rownames(targets) <- refAssaysNoCy

			# Design matrix
			designMatrix <- modelMatrix(targets, ref="ref")


			# Subset M-values and A-values
			mValuesForContrast <- normExprs[,refAssaysNoCy]
			aValuesForContrast <- aValues[,refAssaysNoCy]

			# Verify that rownames (i.e. design element IDs) are the same (and
			# in the same order) for subsetted M-values and A-values.
			if(any(rownames(mValuesForContrast) != rownames(aValuesForContrast))) {
				stop("Different design element IDs found in M-values and A-values. Please verify they are from the same experiment.")
			}

			# Create MAList object.
			# First need to make a normal list with "genes" (design element
			# IDs), "M", and "A" elements.
			maList <- list(genes=rownames(mValuesForContrast), M=mValuesForContrast, A=aValuesForContrast)
			# Make it into an "MAList-class" object
			maList <- new("MAList", maList)

			fit <- lmFit(maList, designMatrix)
	
		} # if(!is.null(aValuesFile))
		
		# Otherwise this is single colour Affymetrix array, make ExpressionSet and apply linear model fit.
		else {
		
			# Subset the required columns of the expressions matrix.
			exprsForContrast <- normExprs[,c(refAssays, testAssays)]
			
			# Make an ExpressionSet for this contrast.
			esetForContrast <- makeEset(exprsForContrast, refAssays, testAssays)

			# Make design matrix.
			designMatrix <- model.matrix(as.formula("~0 + groups"), data=esetForContrast)
			

			# Fit linear model.
			fit <- lmFit(esetForContrast, design=designMatrix)
			
			# simple contrast matrix.
			contrast <- as.matrix(c(-1,1))

			# Apply contrast.
			fit <- contrasts.fit(fit, contrasts=contrast)
		}

		# moderated t-stats and associated p-values, B-stats (log-odds).
		fitEbayes <- eBayes(fit)
		
		# adjust p-values for multiple testing using Benjamini and Hochberg FDR
		# correction, and put them into the object.
		fitEbayes$adjPvals <- rep(NA, length(fitEbayes$p.value))
		nonNaPvIndices <- which(!is.na(fitEbayes$p.value))
		fitEbayes$adjPvals[nonNaPvIndices] <- p.adjust(fitEbayes$p.value[nonNaPvIndices], method="fdr")
		
		# Relevant results to data frames.
		# For 2-colour: 		
		if(!is.null(aValuesFile)) {
			
			# Have to specify which column of fitEbayes$t and
			# fitEbayes$coefficients we want. This is always the first column.

			# results for heatmap matrix display:
			contrastResults <- data.frame(designElements=fitEbayes$genes, adjPval=fitEbayes$adjPvals, t=fitEbayes$t[,1], logFC=fitEbayes$coefficients[,1])
			# results to be used for MvA plot creation (as above but with average intensities and without t-stats:
			plotData <- data.frame(designElements=fitEbayes$genes, adjPval=fitEbayes$adjPvals, logFC=fitEbayes$coefficients[,1], avgExpr=fitEbayes$Amean)
		}
		# For Affymetrix 1-colour:
		else {
			# results for heatmap matrix display:
			contrastResults <- data.frame(designElements = featureNames(esetForContrast), adjPval = fitEbayes$adjPvals, t = fitEbayes$t, logFC = fitEbayes$coefficients)
			# results to be used for MvA plot creation (as above but with average intensities and without t-stats):
			plotData <- data.frame(designElements = featureNames(esetForContrast), adjPval = fitEbayes$adjPvals, logFC = fitEbayes$coefficients, avgExpr = fitEbayes$Amean)
		}

		# write results.
		write.table(contrastResults, file=resFile, row.names=FALSE, quote=FALSE, sep="\t")
		write.table(plotData, file=plotDataFile, row.names=FALSE, quote=FALSE, sep="\t")
		
	})
}




# makeEset()
# 	- Create a Biobase ExpressionSet out of the assay, contrast and expression data.
#
makeEset <<- function(exprsForContrast, refAssays, testAssays) {

	# load Biobase for building ExpressionSet
	library(Biobase)

	# Expression data
	exprsForContrast <- as.matrix(exprsForContrast)
	expressionData <- assayDataNew(storage.mode = "lockedEnvironment", exprs=exprsForContrast)
	featureNames(expressionData) <- rownames(exprsForContrast)
	sampleNames(expressionData) <- colnames(exprsForContrast)

	# Factor data
	groups <- factor(c(rep("ref", length(refAssays)), rep("test", length(testAssays))))
	groupsDF <- data.frame(groups=groups)
	rownames(groupsDF) <- c(refAssays, testAssays)
	groupsDF <- new("AnnotatedDataFrame", data=groupsDF)

	# Feature data (just design element IDs, dies without this)
	featureData <- new("AnnotatedDataFrame", data=data.frame(designElements=rownames(exprsForContrast)))
	featureNames(featureData) <- rownames(exprsForContrast)
	
	# Return an ExpressionSet with all this data
	return(new("ExpressionSet", assayData=expressionData, phenoData=groupsDF, featureData=featureData))
}




# Run with arguments if there are any, otherwise don't do anything.
args <- commandArgs(TRUE)
if(length(args) > 0) {
	do.call(diffAtlas_DE_limma, as.list(args))
}
