
package uk.ac.ebi.atlas.model;

import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.io.Serializable;
import java.util.*;

public abstract class Experiment implements Serializable {

    private static final Gson gson = new Gson();
    private ExperimentType type;
    private ExperimentDesign experimentDesign;
    private String species;
    private String kingdom;
    private String ensemblDB;
    private SortedSet<String> pubMedIds;
    private Map<String, String> speciesMapping;
    private String accession;
    private String description;
    private String displayName;
    private boolean hasExtraInfoFile;
    private boolean hasRData;
    private Date lastUpdate;
    private List<String> dataProviderURL;
    private List<String> dataProviderDescription;
    private List<String> alternativeViews;
    private List<String> alternativeViewDescriptions;

    public Experiment(ExperimentType type, String accession, Date lastUpdate, String displayName, String description,
                      boolean hasExtraInfoFile, boolean hasRData, String species, String kingdom, String ensemblDB, Map<String, String> speciesMapping, Set<String> pubMedIds, ExperimentDesign experimentDesign, List<String> dataProviderURL, List<String> dataProviderDescription, List<String> alternativeViews, List<String> alternativeViewDescriptions) {
        this.type = type;
        this.lastUpdate = lastUpdate;
        this.experimentDesign = experimentDesign;
        this.accession = accession;
        this.displayName = displayName;
        this.description = description;
        this.hasExtraInfoFile = hasExtraInfoFile;
        this.hasRData = hasRData;
        this.species = species;
        this.kingdom = kingdom;
        this.ensemblDB = ensemblDB;
        this.speciesMapping = speciesMapping;
        this.pubMedIds = Sets.newTreeSet(pubMedIds);
        this.dataProviderURL = dataProviderURL;
        this.dataProviderDescription = dataProviderDescription;
        this.alternativeViews = alternativeViews;
        this.alternativeViewDescriptions = alternativeViewDescriptions;
    }

    public ExperimentType getType() {
        return type;
    }

    public Date getLastUpdate() {
        return lastUpdate;
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

    public boolean hasExtraInfoFile() {
        return hasExtraInfoFile;
    }

    public boolean hasRData() {
        return hasRData;
    }

    public String getAccession() {
        return accession;
    }

    public String getKingdom() {
        return kingdom;
    }

    public String getEnsemblDB() {
        return ensemblDB;
    }

    public List<String> getPubMedIds() {
        return Lists.newArrayList(pubMedIds);
    }

    public String getSpecies() {
        return species;
    }

    /*
        Maps an organism value used in the SDRF to an Ensembl species. Loaded from the <speciesMapping> section in factors.xml
        Usually this is the same, however for some experiments there are no corresponding sample species in Ensembl,
        so the mapping points to a similar species,
        eg: for E-GEOD-30352 the SDRF/sample species pongo pygmaeus is mapped to the closely related Ensembl species
        pongo abelii.
        The Ensembl species is what is used to look up genes in Solr
     */

    //TODO: this should be moved to BaselineExperiment, because speciesMapping is always null for differentialExperiment
    // (see DifferentialExperiment constructor)
    public Map<String, String> getSpeciesToEnsemblMapping() {
        return Collections.unmodifiableMap(speciesMapping);
    }

    public String getRequestSpeciesName(String organism) {
        String speciesName = speciesMapping.get(organism);
        if (speciesName != null) {
            return Character.toUpperCase(speciesName.charAt(0)) + speciesName.substring(1);
        }
        return "";
    }

    public List<Pair<String, String>> alternativeViews(){
        List<Pair<String, String>> result = new ArrayList<>();
        Preconditions.checkState(alternativeViews.size() == alternativeViewDescriptions.size());
        for(int i = 0; i<alternativeViews.size(); i++){
            result.add(Pair.of(alternativeViews.get(i), alternativeViewDescriptions.get(i)));
        }
        return result;
    }

    public List<String> getDataProviderURL() {
        return dataProviderURL;
    }

    public List<String> getDataProviderDescription() {
        return dataProviderDescription;
    }

    protected abstract Set<String> getAnalysedRowsAccessions();

    public Map<String, ?> getAttributes(){
        Map<String, Object> result = new HashMap<>();
        result.put("type", this.getType());
        result.put("experimentHasRData", this.hasRData());
        result.put("species", this.getSpecies());
        result.put("experimentDescription", this.getDescription());
        result.put("hasExtraInfo", this.hasExtraInfoFile());
        result.put("pubMedIds", this.getPubMedIds());
        result.put("experimentAccession", this.getAccession());

        //Internet says keywords are not that useful for SEO any more. Remove if it causes you problems.
        List<String> keywords = ImmutableList.<String>builder()
                .add("experiment")
                .add(this.getAccession())
                .addAll(getDataProviderDescription())
                .addAll(Arrays.asList(this.getType().getDescription().split("_")))
                .addAll(this.getExperimentDesign().getAssayHeaders())
                .build();
        result.put("pageKeywords", Joiner.on(',').join(keywords));

        //We want this to show up in Google searches.
        result.put("pageDescription", this.getDescription());

        // Extra information to show on experiment page (if they were provided in <expAcc>-factors.xml file)
        result.put("dataProviderURL", getDataProviderURL());
        result.put("dataProviderDescription", getDataProviderDescription());
        result.put("alternativeViews", alternativeViews);
        result.put("alternativeViewDescriptions", alternativeViewDescriptions);

        //Experiment design related
        result.put("runAccessions", gson.toJson(getAnalysedRowsAccessions()));
        result.putAll(experimentDesign.getAttributes());

        return result;
    }
}
