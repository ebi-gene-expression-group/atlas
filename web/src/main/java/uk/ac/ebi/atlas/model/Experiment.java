
package uk.ac.ebi.atlas.model;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.commons.lang.StringUtils;
import uk.ac.ebi.atlas.web.GeneQuery;

import java.io.Serializable;
import java.util.*;

public class Experiment implements Serializable {

    private ExperimentType type;
    private ExperimentDesign experimentDesign;
    private SortedSet<String> organisms;
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

    public Experiment(ExperimentType type, String accession, Date lastUpdate, String displayName, String description,
                      boolean hasExtraInfoFile, boolean hasRData, Set<String> organisms, String kingdom, String ensemblDB, Map<String, String> speciesMapping, Set<String> pubMedIds, ExperimentDesign experimentDesign) {
        this.type = type;
        this.lastUpdate = lastUpdate;
        this.experimentDesign = experimentDesign;
        this.accession = accession;
        this.displayName = displayName;
        this.description = description;
        this.hasExtraInfoFile = hasExtraInfoFile;
        this.hasRData = hasRData;
        this.organisms = new TreeSet<>(organisms);
        this.kingdom = kingdom;
        this.ensemblDB = ensemblDB;
        this.speciesMapping = speciesMapping;
        this.pubMedIds = Sets.newTreeSet(pubMedIds);
    }

    public Experiment(ExperimentType type, String accession, Date lastUpdate, String description, boolean hasExtraInfoFile, boolean hasRData,
                      Set<String> organisms, String kingdom, String ensemblDB, Map<String, String> speciesMapping, Set<String> pubMedIds, ExperimentDesign experimentDesign) {
        this(type, accession, lastUpdate, null, description, hasExtraInfoFile, hasRData, organisms, kingdom, ensemblDB, speciesMapping, pubMedIds, experimentDesign);
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

    public Set<String> getOrganisms() {
        return Collections.unmodifiableSet(organisms);
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

    //TODO: deprecate this in favor of a method that will return the organism actually needed, rather than just the first
    public String getFirstOrganism() {
        return organisms.iterator().next();
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
    public Map<String, String> getOrganismToEnsemblSpeciesMapping() {
        return Collections.unmodifiableMap(speciesMapping);
    }

    public String getRequestSpeciesName(String organism) {
        String speciesName = speciesMapping.get(organism);
        if (speciesName != null) {
            return Character.toUpperCase(speciesName.charAt(0)) + speciesName.substring(1);
        }
        return "";
    }

    public Map<String, ?> getAttributes(){
        Map<String, Object> result = new HashMap<>();
        result.put("type", this.getType());
        //TODO possibly allSpecies and experimentDescription are no longer used. Check and either remove this comment
        // or the two properties.
        result.put("allSpecies", StringUtils.join(this.getOrganisms(), ", "));
        result.put("experimentDescription", this.getDescription());
        result.put("hasExtraInfo", this.hasExtraInfoFile());
        result.put("pubMedIds", this.getPubMedIds());
        result.put("experimentAccession", this.getAccession());
        return result;
    }
}
