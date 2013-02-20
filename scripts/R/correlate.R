#!/usr/bin/Rscript

correlate <<- function(genes, runs, countsFile1, countsFile2, outFile) {

	counts1Lab <- basename(countsFile1)
	counts2Lab <- basename(countsFile2)

	counts1 <- read.delim(countsFile1, header=TRUE, sep="\t")
	rownames(counts1) <- counts1[,1]
	counts1[,1] <- NULL
	counts1 <- counts1[order(rownames(counts1)),]
	
	counts2 <- read.delim(countsFile2, header=TRUE, sep="\t")
	rownames(counts2) <- counts2[,1]
	counts2[,1] <- NULL
	counts2 <- counts2[order(rownames(counts2)),]

	stopifnot(all(rownames(counts1) == rownames(counts2)))

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

	counts1Sum <- apply(counts1, 1, function(x) { sum(x) })
	counts2Sum <- apply(counts2, 1, function(x) { sum(x) })
	
	sumDF <- data.frame(counts1Sum=counts1Sum, counts2Sum=counts2Sum)


	cor.scatterplot(sumDF, "counts1Sum", "counts2Sum", counts1Lab, counts2Lab, outFile)
}


# Nuno's correlation plot function
cor.scatterplot <- function(df, colA, colB, counts1Lab, counts2Lab, outFile) {
  	

	del <- 0.1
    x<-round(cor(df[,colA], df[,colB]), 2)
	#--------------------------------------------------
	# cat(colA)
	#-------------------------------------------------- 
	

	pdf(file=outFile)
	plot(df[,colA]+del, df[,colB]+del, log="xy", type="p", pch=".", xlab=counts1Lab, ylab=counts2Lab, main=paste("Pearson r=", x, sep=""))
	abline(0, 1, col="red")
	dev.off()
}




args <- commandArgs(TRUE)
if(length(args) > 0) {
	do.call(correlate, as.list(args))
}
