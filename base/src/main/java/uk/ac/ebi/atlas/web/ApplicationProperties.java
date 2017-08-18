package uk.ac.ebi.atlas.web;

import com.google.common.collect.ImmutableMap;
import uk.ac.ebi.atlas.search.SemanticQuery;
import uk.ac.ebi.atlas.species.Species;
import uk.ac.ebi.atlas.trader.ArrayDesignTrader;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

@Named
public class ApplicationProperties {

    private static final String TSV_FILE_EXTENSION = ".tsv";
    private static final String PUBMED_URL_TEMPLATE = "https://europepmc.org/abstract/MED/{0}";
    private static final String AE_ARRAY_URL_TEMPLATE = "https://www.ebi.ac.uk/arrayexpress/arrays/{0}";

    private static final Map<String,String> REFERENCE_EXPERIMENTS =
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

    private ArrayDesignTrader arrayDesignTrader;

    @Inject
    ApplicationProperties(ArrayDesignTrader arrayDesignTrader) {
        this.arrayDesignTrader = arrayDesignTrader;
    }

    //This is invoked from jsp el
    public String getArrayExpressArrayURL(String arrayAccession) {
        String arrayDesign = arrayDesignTrader.getArrayDesignAccession(arrayAccession);  //getKey from arrayDesignMap
        return MessageFormat.format(AE_ARRAY_URL_TEMPLATE, arrayDesign);
    }

    //This is invoked from jsp el
    public String getPubMedURL(String pubMedId) {
        return MessageFormat.format(PUBMED_URL_TEMPLATE, pubMedId);
    }

    public static String getBaselineReferenceExperimentAccession(Species species) {
        return REFERENCE_EXPERIMENTS.get(species.getReferenceName());
    }

    public String buildDownloadURL(SemanticQuery geneQuery, HttpServletRequest request) {
        Map<String, String[]> allParameters = new HashMap<>(request.getParameterMap());
        allParameters.put("geneQuery", new String[]{geneQuery.toUrlEncodedJson()});
        StringBuilder sourceURLBuilder = new StringBuilder(
                request.getRequestURI()
                        .replaceFirst("^.*"+request.getContextPath(), "")
                        .replace("/json/experiments", "/experiments")
                        .replaceFirst("\\??$", TSV_FILE_EXTENSION+"?"));
        allParameters.entrySet().stream().filter(e -> e.getValue().length > 0).forEach(e -> {
            sourceURLBuilder.append(e.getKey()).append("=").append(e.getValue()[0]).append("&");
        });
        sourceURLBuilder.deleteCharAt(sourceURLBuilder.lastIndexOf("&"));
        return sourceURLBuilder.toString();
    }

}
