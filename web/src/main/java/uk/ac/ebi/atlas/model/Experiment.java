/*
 * Copyright 2008-2013 Microarray Informatics Team, EMBL-European Bioinformatics Institute
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
 * For further details of the Gene Expression Atlas project, including source code,
 * downloads and documentation, please see:
 *
 * http://gxa.github.com/gxa
 */

package uk.ac.ebi.atlas.model;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.commons.lang.StringUtils;

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

    public boolean isMultiOrganismExperiment() {
        return getOrganisms().size() > 1;
    }


    public Map<String, ?> getAttributes(){
        Map<String, Object> result = new HashMap<>();
        result.put("type", this.getType());
        result.put("allSpecies", StringUtils.join(this.getOrganisms(), ", "));
        result.put("experimentDescription", this.getDescription());
        result.put("hasExtraInfo", this.hasExtraInfoFile());
        result.put("pubMedIds", this.getPubMedIds());
        result.put("experimentAccession", this.getAccession());
        return result;
    }
}
