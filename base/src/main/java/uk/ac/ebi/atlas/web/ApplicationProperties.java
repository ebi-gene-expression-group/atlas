package uk.ac.ebi.atlas.web;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;
import uk.ac.ebi.atlas.search.SemanticQuery;
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
    private Properties configurationProperties;
    private ArrayDesignTrader arrayDesignTrader;

    @Inject
    ApplicationProperties(@Named("configuration") Properties configurationProperties,
                          ArrayDesignTrader arrayDesignTrader) {
        this.configurationProperties = configurationProperties;
        this.arrayDesignTrader = arrayDesignTrader;
    }

    Map<String,String> speciesToExperimentProperties =
            ImmutableMap.<String,String>builder()
                    .put("anolis_carolinensis","E-MTAB-3727")
                    .put("arabidopsis_thaliana","E-GEOD-30720")
                    .put("bos_taurus","E-MTAB-2798")
                    .put("brachypodium_distachyon","E-MTAB-4401")
                    .put("caenorhabditis_elegans","E-MTAB-2812")
                    .put("equus_caballus","E-GEOD-46858")
                    .put("gallus_gallus","E-MTAB-2797")
                    .put("glycine_max","E-GEOD-61857")
                    .put("homo_sapiens","E-MTAB-2836")
                    .put("hordeum_vulgare","E-MTAB-2809")
                    .put("macaca_mulatta","E-MTAB-2799")
                    .put("monodelphis_domestica","E-MTAB-3719")
                    .put("mus_musculus","E-MTAB-3718")
                    .put("oryza_sativa","E-MTAB-2037")
                    .put("ovis_aries","E-MTAB-3838")
                    .put("papio_anubis","E-MTAB-2848")
                    .put("rattus_norvegicus","E-GEOD-53960")
                    .put("solanum_lycopersicum","E-MTAB-4765")
                    .put("sorghum_bicolor","E-MTAB-4400")
                    .put("triticum_aestivum","E-MTAB-4260")
                    .put("vitis_vinifera","E-MTAB-4350")
                    .put("xenopus_tropicalis","E-MTAB-3726")
                    .put("zea_mays","E-MTAB-4342").build();


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

    public String getResourcesVersion() {
        return configurationProperties.getProperty("resources.version");
    }

    public Set<String> getArrayDesignAccessions() {
        return getStringValues("arraydesign.accessions");
    }

    private Set<String> getStringValues(String propertyKey) {
        return Sets.newHashSet(configurationProperties.getProperty(propertyKey).trim().split(","));
    }

    public String getBaselineReferenceExperimentAccession(String species) {
        return speciesToExperimentProperties.get(species.toLowerCase().replace(" ", "_"));
    }

    public String buildServerURL(HttpServletRequest request) throws MalformedURLException {
        String spec = request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
        if (request.isSecure()) {
            spec = "https://" + spec;
        } else {
            spec = "http://" + spec;
        }

        URL url = new URL(spec);
        return url.toExternalForm();
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

    @Deprecated //Not used because now we save data from the page. Also it's probably broken.
    public String buildDownloadURLForWidget(HttpServletRequest request, String experimentAccession) {
        // get original query string, not the one modified by ExperimentDispatcher
        String queryString = (String) request.getAttribute("javax.servlet.forward.query_string");
        return Joiner.on("?").skipNulls()
                .join(new String[]{"/experiments/" + experimentAccession + TSV_FILE_EXTENSION, queryString});
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
        return (atlasHost.contains("ebi.ac.uk")? "https": request.getScheme())+"://" + atlasHost;
    }

}
