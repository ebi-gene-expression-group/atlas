package uk.ac.ebi.atlas.web;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.model.AnatomogramType;
import uk.ac.ebi.atlas.model.baseline.AssayGroupFactor;
import uk.ac.ebi.atlas.trader.ArrayDesignTrader;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.*;

@Named("applicationProperties")
@Scope("singleton")
public class ApplicationProperties {

    private static final String TSV_FILE_EXTENSION = ".tsv";

    private Properties speciesToExperimentProperties;

    private Properties configurationProperties;
    private ArrayDesignTrader arrayDesignTrader;

    @Inject
    ApplicationProperties(@Named("configuration") Properties configurationProperties,
                          @Named("speciesToExperimentPropertyFile") Properties speciesToExperimentProperties,
                          ArrayDesignTrader arrayDesignTrader) {
        this.speciesToExperimentProperties = speciesToExperimentProperties;
        this.configurationProperties = configurationProperties;
        this.arrayDesignTrader = arrayDesignTrader;
    }

    public String getAnatomogramFileName(String species, AnatomogramType anatomogramType) {
        String key = "organism.anatomogram." + species.toLowerCase();
        String ending = "";
        if(anatomogramType.equals(AnatomogramType.MALE)) {
            ending = ".male";
        } else if (anatomogramType.equals(AnatomogramType.FEMALE)) {
            ending = ".female";
        } else if (anatomogramType.equals(AnatomogramType.BRAIN)) {
            ending = ".brain";
        }

        return configurationProperties.getProperty(key + ending);
    }

    public Map<String, ?> getAnatomogramProperties(String species, Set<AssayGroupFactor> filteredAssayGroupFactors) {
        Map<String, Object> result = new HashMap<>();
        String maleAnatomogramFileName = getAnatomogramFileName(species, AnatomogramType.MALE);
        result.put("maleAnatomogramFile", getAnatomogramFileName(species, AnatomogramType.MALE));

        String femaleAnatomogramFileName = getAnatomogramFileName(species, AnatomogramType.FEMALE);
        result.put("femaleAnatomogramFile", femaleAnatomogramFileName);

        String brainAnatomogramFileName = getAnatomogramFileName(species, AnatomogramType.BRAIN);
        result.put("brainAnatomogramFile", brainAnatomogramFileName);

        result.put("hasAnatomogram", maleAnatomogramFileName != null || femaleAnatomogramFileName != null || brainAnatomogramFileName != null);

        if(species.equals("oryza sativa") || species.equals("oryza sativa japonica group")){
            result.put("toggleButtonMaleImageTemplate", "/resources/images/whole_plant");
            result.put("toggleButtonFemaleImageTemplate", "/resources/images/flower_parts");
        }
        else {
            result.put("toggleButtonMaleImageTemplate", "/resources/images/male");
            result.put("toggleButtonFemaleImageTemplate", "/resources/images/female");
            result.put("toggleButtonBrainImageTemplate", "/resources/images/brain");
        }

        result.put("allSvgPathIds", new Gson().toJson(extractOntologyTerm
                (filteredAssayGroupFactors)));

        return result;
    }

    private ImmutableSet<String> extractOntologyTerm(Set<AssayGroupFactor> filteredAssayGroupFactors) {
        ImmutableSet.Builder<String> builder = ImmutableSet.builder();

        for (AssayGroupFactor assayGroupFactor : filteredAssayGroupFactors) {
            String valueOntologyTermId = assayGroupFactor.getValueOntologyTermId();
            if (valueOntologyTermId != null) {
                builder.add(valueOntologyTermId);
            }
        }
        return builder.build();
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

    public String getArrayExpressRestURL(String experimentAccession) {
        String arrayExpressUrlTemplate = configurationProperties.getProperty("experiment.arrayexpress.rest.url.template");
        return MessageFormat.format(arrayExpressUrlTemplate, experimentAccession);
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
        return speciesToExperimentProperties.getProperty(species.toLowerCase().replace(" ", "_"));
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

    public String buildDownloadURL(HttpServletRequest request) {
        // get original query string, not the one modified by ExperimentDispatcher
        String queryString = (String) request.getAttribute("javax.servlet.forward.query_string");
        String requestUri = (String) request.getAttribute("javax.servlet.forward.request_uri");
        String uri = StringUtils.remove(requestUri, request.getContextPath());

        return Joiner.on("?").skipNulls()
                .join(new String[]{uri + TSV_FILE_EXTENSION, queryString});
    }

    public String buildDownloadURLForWidget(HttpServletRequest request, String experimentAccession) {
        // get original query string, not the one modified by ExperimentDispatcher
        String queryString = (String) request.getAttribute("javax.servlet.forward.query_string");
        return Joiner.on("?").skipNulls()
                .join(new String[]{"/experiments/" + experimentAccession + TSV_FILE_EXTENSION, queryString});
    }

    // eg: "red,green,blue"
    public <T> String encodeMultiValues(Iterable<T> iterable) {
        return Joiner.on(",").join(iterable);
    }

    /* Used in bioEntities.jsp */
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
        return request.getScheme()+"://" + atlasHost;
    }

}
