/*
 * Copyright 2008-2012 Microarray Informatics Team, EMBL-European Bioinformatics Institute
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

import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.util.*;

public class Experiment implements Serializable {

    private ExperimentType type;

    protected SortedSet<String> species;
    protected Map<String, String> speciesMapping;
    private String accession;
    private String description;
    private String displayName;
    private boolean hasExtraInfoFile;

    public Experiment(ExperimentType type, String accession, String displayName, String description, boolean hasExtraInfoFile, Set<String> species, Map<String, String> speciesMapping) {
        this.type = type;
        this.accession = accession;
        this.displayName = displayName;
        this.description = description;
        this.hasExtraInfoFile = hasExtraInfoFile;
        this.species = new TreeSet<>(species);
        this.speciesMapping = speciesMapping;
    }

    public Experiment(ExperimentType type, String accession, String description, boolean hasExtraInfoFile, Set<String> species, Map<String, String> speciesMapping) {
        this(type, accession, null, description, hasExtraInfoFile, species, speciesMapping);
    }

    public ExperimentType getType() {
        return type;
    }

    public String getDisplayName() {
        if (StringUtils.isNotBlank(displayName)){
            return getDisplayName();
        }
        return getAccession();
    }

    public String getDescription() {
        return description;
    }

    public boolean hasExtraInfoFile() {
        return hasExtraInfoFile;
    }

    public String getAccession() {
        return accession;
    }

    public Set<String> getSpecies() {
        return Collections.unmodifiableSet(species);
    }

    public String getFirstSpecies() {
        return species.iterator().next();
    }

    public Map<String, String> getSpeciesMapping() {
        return Collections.unmodifiableMap(speciesMapping);
    }

}
