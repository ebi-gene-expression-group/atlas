package uk.ac.ebi.atlas.web;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;
import uk.ac.ebi.atlas.search.SemanticQuery;
import uk.ac.ebi.atlas.species.Species;
import uk.ac.ebi.atlas.trader.ArrayDesignTrader;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

@Named("applicationProperties")
public class ApplicationProperties {

    private static final String TSV_FILE_EXTENSION = ".tsv";

    private static Map<String,String> speciesToExperimentProperties =
            ImmutableMap.<String,String>builder()
                    .put("anolis carolinensis","E-MTAB-3727")
                    .put("arabidopsis thaliana","E-GEOD-30720")
                    .put("bos taurus","E-MTAB-2798")
                    .put("brachypodium distachyon","E-MTAB-4401")
                    .put("caenorhabditis elegans","E-MTAB-2812")
                    .put("equus caballus","E-GEOD-46858")
                    .put("gallus gallus","E-MTAB-2797")
                    .put("glycine max","E-GEOD-61857")
                    .put("homo sapiens","E-MTAB-2836")
                    .put("hordeum vulgare","E-MTAB-2809")
                    .put("macaca mulatta","E-MTAB-2799")
                    .put("monodelphis domestica","E-MTAB-3719")
                    .put("mus musculus","E-MTAB-3718")
                    .put("oryza sativa","E-MTAB-2037")
                    .put("ovis aries","E-MTAB-3838")
                    .put("papio anubis","E-MTAB-2848")
                    .put("rattus norvegicus","E-GEOD-53960")
                    .put("solanum lycopersicum","E-MTAB-4765")
                    .put("sorghum bicolor","E-MTAB-4400")
                    .put("triticum aestivum","E-MTAB-4260")
                    .put("vitis vinifera","E-MTAB-4350")
                    .put("xenopus tropicalis","E-MTAB-3726")
                    .put("zea mays","E-MTAB-4342").build();

    private Properties configurationProperties;
    private ArrayDesignTrader arrayDesignTrader;

    @Inject
    ApplicationProperties(@Named("configuration") Properties configurationProperties,
                          ArrayDesignTrader arrayDesignTrader) {
        this.configurationProperties = configurationProperties;
        this.arrayDesignTrader = arrayDesignTrader;
    }


    //This is invoked from jsp el
    public String getArrayExpressURL(String experimentAccession) {
        String arrayExpressUrlTemplate = configurationProperties.getProperty("experiment.arrayexpress.url.template");
        return MessageFormat.format(arrayExpressUrlTemplate, experimentAccession);
    }

    //This is invoked from jsp el
    public String getArrayExpressArrayURL(String arrayAccession) {
        String arrayDesign = arrayDesignTrader.getArrayDesignAccession(arrayAccession);  //getKey from arrayDesignMap
        String arrayExpressUrlTemplate = configurationProperties.getProperty("experiment.arrayexpress.arrays.url.template");
        return MessageFormat.format(arrayExpressUrlTemplate, arrayDesign);
    }

    //This is invoked from jsp el
    public String getPubMedURL(String pubMedId) {
        String arrayExpressUrlTemplate = configurationProperties.getProperty("experiment.pubmed.url.template");
        return MessageFormat.format(arrayExpressUrlTemplate, pubMedId);
    }

    public String getFeedbackEmailAddress() {
        return configurationProperties.getProperty("feedback.email");
    }

    public Set<String> getArrayDesignAccessions() {
        return Sets.newHashSet(configurationProperties.getProperty("arraydesign.accessions").trim().split(","));
    }

    public static String getBaselineReferenceExperimentAccession(Species species) {
        return speciesToExperimentProperties.get(species.getReferenceName());
    }

    public static String buildServerURL(HttpServletRequest request) {
        String spec = request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
        if (request.isSecure()) {
            spec = "https://" + spec;
        } else {
            spec = "http://" + spec;
        }

        try {
            return new URL(spec).toExternalForm();
        } catch (MalformedURLException e){
            throw new RuntimeException(e);
        }
    }

    public String buildDownloadURL(SemanticQuery geneQuery, HttpServletRequest request) {
        Map<String, String[]> allParameters = new HashMap<>(request.getParameterMap());
        allParameters.put("geneQuery", new String[]{geneQuery.toUrlEncodedJson()});
        StringBuilder sourceURLBuilder = new StringBuilder(
                request.getRequestURI()
                        .replaceFirst("^.*"+request.getContextPath(), "")
                        .replace("/json/experiments", "/experiments")
                        .replaceFirst("\\??$", TSV_FILE_EXTENSION+"?"));
        for(Map.Entry<String, String[]> e: allParameters.entrySet()){
            if(e.getValue().length>0) {
                sourceURLBuilder.append(e.getKey()).append("=").append(e.getValue()[0]).append("&");
            }
        }
        sourceURLBuilder.deleteCharAt(sourceURLBuilder.lastIndexOf("&"));
        return sourceURLBuilder.toString();
    }

    /* Used in bioentities.jsp and bioentities-search-results.jsp */
    public String urlParamEncode(String value) throws UnsupportedEncodingException {
        return URLEncoder.encode(value, "UTF-8");
    }

    /*
    <c:set var="serverPort" value="${pageContext.request.serverPort == 80 ? '' : ':'.concat(pageContext.request.serverPort)}"/>
    <c:set var="protocol" value="${pageContext.request.scheme}://"/>
    <c:set var="atlasHost" value="${pageContext.request.serverName.concat(serverPort)}"/>
     */
    public String buildAtlasHostURL(HttpServletRequest request){
        String serverPort = request.getServerPort() == 80 ? "" : ":".concat(Integer.toString(request.getServerPort()));
        String atlasHost = request.getServerName().concat(serverPort);
        return (atlasHost.contains("wwwdev.ebi.ac.uk") || atlasHost.contains("www.ebi.ac.uk") ? "https": request.getScheme())+"://" + atlasHost;
    }

}
