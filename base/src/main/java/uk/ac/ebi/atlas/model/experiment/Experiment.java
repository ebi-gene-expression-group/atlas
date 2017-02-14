package uk.ac.ebi.atlas.model.experiment;

import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import uk.ac.ebi.atlas.model.DescribesDataColumns;
import uk.ac.ebi.atlas.species.Species;
import uk.ac.ebi.atlas.utils.ExperimentInfo;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;

public abstract class Experiment<DataColumnDescriptor extends DescribesDataColumns> implements Serializable {

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
    protected final ImmutableMap<String, DataColumnDescriptor> dataColumnDescriptorsPerId;
    private final ExperimentDisplayDefaults experimentDisplayDefaults;

    public Experiment(ExperimentType type, String accession, Date lastUpdate, String displayName, String description,
                      String disclaimer, boolean hasRData, Species species, Collection<String> pubMedIds,
                      ExperimentDesign experimentDesign, List<String> dataProviderURL,
                      List<String> dataProviderDescription, List<String> alternativeViews,
                      List<String> alternativeViewDescriptions, List<DataColumnDescriptor> dataColumnDescriptors,
                      ExperimentDisplayDefaults experimentDisplayDefaults) {

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
        ImmutableMap.Builder<String, DataColumnDescriptor> builder = ImmutableMap.builder();
        for(DataColumnDescriptor dataColumnDescriptor: dataColumnDescriptors){
            builder.put(dataColumnDescriptor.getId(), dataColumnDescriptor);
        }
        this.dataColumnDescriptorsPerId = builder.build();
        this.experimentDisplayDefaults = experimentDisplayDefaults;

    }

    public List<DataColumnDescriptor> getDataColumnDescriptors(){
        return ImmutableList.<DataColumnDescriptor>builder().addAll(dataColumnDescriptorsPerId.values()).build();
    }

    public DataColumnDescriptor getDataColumnDescriptor(String id){
        return dataColumnDescriptorsPerId.get(id);
    }

    public ExperimentType getType() {
        return type;
    }
    
    public ExperimentDesign getExperimentDesign() {
        return experimentDesign;
    }

    public ExperimentDisplayDefaults getDisplayDefaults() {
        return experimentDisplayDefaults;
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

    //this doesn't quite match our model and is only used in experiment design
    //and only to tell if an assay has been analysed or not
    //possibly: move it to experiment design?
    public Set<String> getAnalysedRowsAccessions(){
        ImmutableSet.Builder<String> b = ImmutableSet.builder();
        for(DataColumnDescriptor dataColumnDescriptor: getDataColumnDescriptors()){
            b.addAll(dataColumnDescriptor.assaysAnalyzedForThisDataColumn());
        }
        return b.build();
    }

    public Map<String, Object> getAttributes(){
        Map<String, Object> result = new HashMap<>();
        result.put("type", type);
        result.put("experimentHasRData", hasRData);
        result.put("speciesReferenceName", species.getReferenceName());
        result.put("species", species);
        result.put("resources", species.getResources());
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
        experimentInfo.setNumberOfAssays(getAnalysedRowsAccessions().size());
        return experimentInfo;
    }

}
