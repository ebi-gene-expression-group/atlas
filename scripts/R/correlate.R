#!/usr/bin/Rscript
# 
# Calculate Pearson's product-moment correlation coefficient using two sets of
# RNA-seq read counts (or *PKMs) produced by different quantification methods.
# Produce a scatterplot and display correlation coefficient on it.
#
# Provide two tab-delimited text files with genes in rows and sequencing runs in columns.
# Provide comma-separated gene IDs and/or run IDs to use for the plot. To use
# all genes and/or all runs, use "all" instead (see below examples).
# 
# For each tab-delimited file, counts(/*PKMs) from all runs specified are
# summed for each gene. The resulting two vectors of counts are correlated and
# plotted.
#
# Examples:
# 1. All genes and all runs:	
# 	Rscript correlate.R all all <counts file 1> <counts file 2> <plot filename>
#
# 2. Only some genes but all runs:
# 	Rscript correlate.R ENSG0001,ENSG1234,ENSG0021,ENSG9991 all <counts file 1> <counts file 2> <plot filename>
#
# 3. All genes but only some runs:
# 	Rscript correlate.R all SRR001,SRR003,SRR009 <counts file 1> <counts file 2> <plot filename>

correlate <<- function(genes, runs, countsFile1, countsFile2, outFile) {
	
	# Get labels for plot axes from filenames.
	counts1Lab <- basename(countsFile1)
	counts2Lab <- basename(countsFile2)

	# Read in counts and sort the rows by gene IDs.
	counts1 <- read.delim(countsFile1, header=TRUE, sep="\t")
	rownames(counts1) <- counts1[,1]
	counts1[,1] <- NULL
	counts1 <- counts1[order(rownames(counts1)),]
	
	counts2 <- read.delim(countsFile2, header=TRUE, sep="\t")
	rownames(counts2) <- counts2[,1]
	counts2[,1] <- NULL
	counts2 <- counts2[order(rownames(counts2)),]

	# Make sure that the row names in each set of counts are identical. It
	# doesn't make sense to try and correlate counts from one gene from one
	# method with counts from a totally different gene from the other method.
	stopifnot(all(rownames(counts1) == rownames(counts2)))
	
	# Subset selected runs and/or genes if need be.
	if(runs != "all") {
		
		runs <- unlist(strsplit(runs, ","))

		counts1 <- counts1[,runs, drop=FALSE]
		counts2 <- counts2[,runs, drop=FALSE]
	}
	if(genes != "all") {
		
		genes <- unlist(strsplit(genes, ","))

		counts1 <- counts1[genes, , drop=FALSE]
		counts2 <- counts2[genes, , drop=FALSE]
	}

	# Add counts for each gene and put the two vectors into a data frame.
	counts1Sum <- apply(counts1, 1, function(x) { sum(x) })
	counts2Sum <- apply(counts2, 1, function(x) { sum(x) })
	sumDF <- data.frame(counts1Sum=counts1Sum, counts2Sum=counts2Sum)

	# Correlate and plot.
	cor.scatterplot(sumDF, "counts1Sum", "counts2Sum", counts1Lab, counts2Lab, outFile)
}


# Nuno's correlation plot function
cor.scatterplot <- function(df, colA, colB, counts1Lab, counts2Lab, outFile) {
  	
	# add 0.1 to all counts.
	del <- 0.1

	# use cor() to get Pearson's correlation coefficient, and round it to 2 decimal places.
    x<-round(cor(df[,colA], df[,colB]), 2)
	#--------------------------------------------------
	# cat(colA)
	#-------------------------------------------------- 
	
	# Start PDF device to write plot to.
	pdf(file=outFile)

	# Plot!
	plot(df[,colA]+del, df[,colB]+del, log="xy", type="p", pch=".", xlab=counts1Lab, ylab=counts2Lab, main=paste("Pearson r=", x, sep=""))
	# add line y=x
	abline(0, 1, col="red")
	# close PDF.
	dev.off()
}



# Call correlate() function if we've got arguments.
args <- commandArgs(TRUE)
if(length(args) > 0) {
	do.call(correlate, as.list(args))
}
