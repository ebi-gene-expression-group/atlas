package uk.ac.ebi.atlas.model.experiment;

import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import uk.ac.ebi.atlas.species.Species;
import uk.ac.ebi.atlas.utils.ExperimentInfo;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class Experiment implements Serializable {

    private static final Gson gson = new Gson();
    private ExperimentType type;
    protected ExperimentDesign experimentDesign;
    private Species species;
    private List<String> pubMedIds;
    private String accession;
    protected String description;
    private String displayName;
    private String disclaimer;
    private boolean hasRData;
    private Date lastUpdate;
    private List<String> dataProviderURL;
    private List<String> dataProviderDescription;
    private List<String> alternativeViews;
    private List<String> alternativeViewDescriptions;

    public Experiment(ExperimentType type, String accession, Date lastUpdate, String displayName, String description,
                      String disclaimer, boolean hasRData, Species species, Collection<String> pubMedIds,
                      ExperimentDesign experimentDesign, List<String> dataProviderURL,
                      List<String> dataProviderDescription, List<String> alternativeViews,
                      List<String> alternativeViewDescriptions) {

        this.type = type;
        this.lastUpdate = lastUpdate;
        this.experimentDesign = experimentDesign;
        this.accession = accession;
        this.displayName = displayName;
        this.description = description;
        this.disclaimer = disclaimer;
        this.hasRData = hasRData;
        this.species = species;
        this.pubMedIds = ImmutableList.copyOf(Sets.newTreeSet(pubMedIds));
        this.dataProviderURL = dataProviderURL;
        this.dataProviderDescription = dataProviderDescription;
        this.alternativeViews = alternativeViews;
        this.alternativeViewDescriptions = alternativeViewDescriptions;

    }

    public ExperimentType getType() {
        return type;
    }
    
    public ExperimentDesign getExperimentDesign() {
        return experimentDesign;
    }

    public String getDisplayName() {
        if (StringUtils.isNotBlank(displayName)) {
            return displayName;
        }
        return getAccession();
    }

    public String getDescription() {
        return description;
    }

    public String getAccession() {
        return accession;
    }

    public Species getSpecies(){
        return species;
    }

    public List<Pair<String, String>> alternativeViews(){
        List<Pair<String, String>> result = new ArrayList<>();
        Preconditions.checkState(alternativeViews.size() == alternativeViewDescriptions.size());

        for(int i = 0 ; i < alternativeViews.size() ; i++) {
            result.add(Pair.of(alternativeViews.get(i), alternativeViewDescriptions.get(i)));
        }
        return result;
    }

    protected abstract Set<String> getAnalysedRowsAccessions();

    public Map<String, Object> getAttributes(){
        Map<String, Object> result = new HashMap<>();
        result.put("type", type);
        result.put("experimentHasRData", hasRData);
        result.put("species", species);
        result.put("experimentDescription", description);
        result.put("pubMedIds", pubMedIds);
        result.put("experimentAccession", accession);
        result.put("disclaimer", disclaimer);
        result.put("isFortLauderdale", "fortLauderdale".equalsIgnoreCase(disclaimer));//Deprecated,remove

        //Internet says keywords are not that useful for SEO any more. Remove if it causes you problems.
        List<String> keywords = ImmutableList.<String>builder()
                .add("experiment")
                .add(accession)
                .addAll(dataProviderDescription)
                .addAll(Arrays.asList(type.getDescription().split("_")))
                .addAll(experimentDesign.getAssayHeaders())
                .build();
        result.put("pageKeywords", Joiner.on(',').join(keywords));

        //We want this to show up in Google searches.
        result.put("pageDescription", description);

        // Extra information to show on experiment page (if they were provided in <expAcc>-factors.xml file)
        result.put("dataProviderURL", dataProviderURL);
        result.put("dataProviderDescription", dataProviderDescription);
        result.put("alternativeViews", alternativeViews);
        result.put("alternativeViewDescriptions", alternativeViewDescriptions);

        //Experiment design related
        result.put("runAccessions", gson.toJson(getAnalysedRowsAccessions()));
        result.putAll(experimentDesign.getAttributes());

        return result;
    }

    public ExperimentInfo buildExperimentInfo(){

        ExperimentInfo experimentInfo = new ExperimentInfo();
        experimentInfo.setExperimentAccession(accession);
        experimentInfo.setLastUpdate(new SimpleDateFormat("dd-MM-yyyy").format(lastUpdate));
        experimentInfo.setExperimentDescription(description);
        experimentInfo.setSpecies(species.getName());
        experimentInfo.setKingdom(species.getKingdom());
        experimentInfo.setExperimentType(type.getParent());
        experimentInfo.setExperimentalFactors(experimentDesign.getFactorHeaders());

        return experimentInfo;
    }
}
