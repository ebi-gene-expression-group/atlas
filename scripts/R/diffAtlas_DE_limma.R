#!/usr/bin/Rscript
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
diffAtlas_DE_limma <<- function(normExprsFile, refAssays, testAssays, resFile, plotDataFile) {

	e <- try({
		
		# Read expressions.
		print(paste("Reading", normExprsFile))
		normExprs <- read.delim(normExprsFile, header=TRUE, sep="\t")
		rownames(normExprs) <- normExprs[,1]
		normExprs[,1] <- NULL

		# make vectors of assay accessions.
		refAssays <- unlist(strsplit(refAssays, ","))
		testAssays <- unlist(strsplit(testAssays, ","))

		# Subset the required columns of the expressions matrix.
		exprsForContrast <- normExprs[,c(refAssays, testAssays)]
		
		# Make an ExpressionSet for this contrast.
		esetForContrast <- makeEset(exprsForContrast, refAssays, testAssays)

		# Make design matrix.
		designMatrix <- model.matrix(as.formula("~0 + groups"), data=esetForContrast)
		
		# load limma.
		library(limma)

		# Fit linear model.
		fit <- lmFit(esetForContrast, design=designMatrix)
		
		# simple contrast matrix.
		contrast <- as.matrix(c(-1,1))

		# Apply contrast.
		contrastFit <- contrasts.fit(fit, contrasts=contrast)

		# moderated t-stats and associated p-values, B-stats (log-odds).
		contrastFitEbayes <- eBayes(contrastFit)
		
		# adjust p-values for multiple testing using Benjamini and Hochberg FDR
		# correction, and put them into the object.
		contrastFitEbayes$adjPvals <- rep(NA, length(contrastFitEbayes$p.value))
		nonNaPvIndices <- which(!is.na(contrastFitEbayes$p.value))
		contrastFitEbayes$adjPvals[nonNaPvIndices] <- p.adjust(contrastFitEbayes$p.value[nonNaPvIndices], method="fdr")
		
		# Relevant results to data frames.
		# results for heatmap matrix display:
		contrastResults <- data.frame(designElements = featureNames(esetForContrast), adjPval = contrastFitEbayes$adjPvals, t = contrastFitEbayes$t, logFC = contrastFitEbayes$coefficients)
		# results to be used for MvA plot creation (as above but with average intensities and without t-stats):
		plotData <- data.frame(designElements = featureNames(esetForContrast), adjPval = contrastFitEbayes$adjPvals, logFC = contrastFitEbayes$coefficients, avgExpr = contrastFitEbayes$Amean)

		# write.
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
