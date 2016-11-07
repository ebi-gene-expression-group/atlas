
package uk.ac.ebi.atlas.controllers;

import java.util.HashMap;
import java.util.Map;

public class DownloadURLBuilder {

    private static final String TSV_RAW_FILE_NAME = "/raw-counts.tsv";
    private static final String TSV_NORMALIZED_FILE_EXTENSION = "/normalized.tsv";
    private static final String TSV_LOG_FOLD_FILE_EXTENSION = "/logFold.tsv";
    private static final String TSV_ANALYTICS_FILE_EXTENSION = "/all-analytics.tsv";
    private static final String R_FILE_EXTENSION = "-atlasExperimentSummary.Rdata";

    private String experimentAccession;

    public DownloadURLBuilder(String experimentAccession) {
        this.experimentAccession = experimentAccession;
    }

    public Map<String,String> dataDownloadUrls(String requestURI){
        Map<String,String> result = new HashMap<>();
        result.put("rawDownloadUrl", buildDownloadRawUrl(requestURI));
        result.put("normalizedUrl", buildDownloadNormalizedDataUrl(requestURI));
        result.put("logFoldUrl", buildDownloadLogFoldDataUrl(requestURI));
        result.put("analyticsDownloadUrl", buildDownloadAllAnalyticsUrl(requestURI));
        result.put("rDownloadUrl", buildDownloadRFileUrl(requestURI));
        return result;
    }

    private String buildDownloadRawUrl(String requestURI) {
        return extractBaseURL(requestURI) + TSV_RAW_FILE_NAME;
    }

    private String buildDownloadAllAnalyticsUrl(String requestURI) {
        return extractBaseURL(requestURI) + TSV_ANALYTICS_FILE_EXTENSION;
    }

    private String buildDownloadNormalizedDataUrl(String requestURI) {
        return extractBaseURL(requestURI) + TSV_NORMALIZED_FILE_EXTENSION;
    }

    private String buildDownloadLogFoldDataUrl(String requestURI) {
        return extractBaseURL(requestURI) + TSV_LOG_FOLD_FILE_EXTENSION;
    }

    private String buildDownloadRFileUrl(String requestURI) {
        return extractBaseURL(requestURI) + experimentAccession + R_FILE_EXTENSION;
    }

    private String extractBaseURL(String requestURI) {
        if (requestURI.endsWith("/experiment-design")) {
            return requestURI.replace("/experiment-design", "");
        } else if (requestURI.endsWith("/analysis-methods")) {
            return requestURI.replace("/analysis-methods", "");
        } else {
            return requestURI;
        }
    }
}