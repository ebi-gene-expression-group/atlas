
package uk.ac.ebi.atlas.web.controllers;

import org.springframework.context.annotation.Scope;
import org.springframework.ui.Model;

import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Named
@Scope("request")
public class DownloadURLBuilder {

    private static final String TSV_RAW_FILE_EXTENSION = "/raw-counts.tsv";

    private static final String TSV_NORMALIZED_FILE_EXTENSION = "/normalized.tsv";

    private static final String TSV_LOG_FOLD_FILE_EXTENSION = "/logFold.tsv";

    private static final String TSV_ANALYTICS_FILE_EXTENSION = "/all-analytics.tsv";

    private static final String R_FILE_EXTENSION = "-atlasExperimentSummary.Rdata";

    public Map<String,String> dataDownloadUrls(HttpServletRequest request){
        Map<String,String> result = new HashMap<>();
        result.put("rawDownloadUrl", buildDownloadRawUrl(request));
        result.put("normalizedUrl", buildDownloadNormalizedDataUrl(request));
        result.put("logFoldUrl", buildDownloadLogFoldDataUrl(request));
        result.put("analyticsDownloadUrl", buildDownloadAllAnalyticsUrl(request));
        result.put("rDownloadUrl", buildDownloadRFileUrl(request));
        result.put("clusteringPdfUrl", buildDownloadClusteringPdfFileUrl(request));
        return result;
    }

    public static void addRDownloadUrlToModel(Model model, HttpServletRequest request) {
        model.addAttribute("rDownloadUrl", buildDownloadRFileUrl(request));
    }

    private String buildDownloadRawUrl(HttpServletRequest request) {
        return extractBaseURL(request) + TSV_RAW_FILE_EXTENSION;
    }

    private String buildDownloadAllAnalyticsUrl(HttpServletRequest request) {
        return extractBaseURL(request) + TSV_ANALYTICS_FILE_EXTENSION;
    }

    private String buildDownloadNormalizedDataUrl(HttpServletRequest request) {
        return extractBaseURL(request) + TSV_NORMALIZED_FILE_EXTENSION;
    }

    private String buildDownloadLogFoldDataUrl(HttpServletRequest request) {
        return extractBaseURL(request) + TSV_LOG_FOLD_FILE_EXTENSION;
    }

    private static String buildDownloadRFileUrl(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        String slashExperimentAccession = requestURI.substring(requestURI.lastIndexOf("/"));
        return requestURI + slashExperimentAccession + R_FILE_EXTENSION;
    }

    private String buildDownloadClusteringPdfFileUrl(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        String slashExperimentAccession = requestURI.substring(requestURI.lastIndexOf("/"));
        return requestURI + slashExperimentAccession + R_FILE_EXTENSION;
    }

    private String extractBaseURL(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        if (requestURI.endsWith("/experiment-design")) {
            return requestURI.replace("/experiment-design", "");
        } else if (requestURI.endsWith("/analysis-methods")) {
            return requestURI.replace("/analysis-methods", "");
        } else {
            return requestURI;
        }
    }
}