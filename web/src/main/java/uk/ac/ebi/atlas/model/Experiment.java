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
import java.util.Collections;
import java.util.Set;

public class Experiment implements Serializable {

    protected Set<String> species;
    private String accession;
    private String description;
    private String displayName;
    private boolean hasExtraInfoFile;

    public Experiment(String accession, String displayName, String description, boolean hasExtraInfoFile, Set<String> species) {
        this.accession = accession;
        this.displayName = displayName;
        this.description = description;
        this.hasExtraInfoFile = hasExtraInfoFile;
        this.species = species;
    }

    public Experiment(String accession, String description, boolean hasExtraInfoFile, Set<String> species) {
        this(accession, null, description, hasExtraInfoFile, species);
    }

    public String getDisplayName() {
        return StringUtils.isNotBlank(displayName) ? displayName : getExperimentAccession();
    }

    public String getDescription() {
        return description;
    }

    public boolean hasExtraInfoFile() {
        return hasExtraInfoFile;
    }

    public String getExperimentAccession(){
        return accession;
    }

    public Set<String> getSpecies() {
        return Collections.unmodifiableSet(species);
    }

    public String getFirstSpecies() {
        return species.iterator().next();
    }
}
