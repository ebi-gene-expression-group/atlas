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

package uk.ac.ebi.atlas.experimentimport;

import com.google.common.base.Objects;
import com.google.gson.*;
import uk.ac.ebi.atlas.model.ExperimentType;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class ExperimentDTO {

    private final String accessKey;

    private String experimentAccession;

    private ExperimentType experimentType;

    private Date lastUpdate;

    private boolean isPrivate;

    private Set<String> species;

    private Set<String> pubmedIds;

    private String title;

    public ExperimentDTO(String experimentAccession, ExperimentType experimentType, Set<String> species, Set<String> pubmedIds,
                         String title, boolean isPrivate) {
        this(experimentAccession, experimentType, species, pubmedIds, title, null, isPrivate, UUID.randomUUID().toString());
    }

    public ExperimentDTO(String experimentAccession, ExperimentType experimentType, Set<String> pubmedIds,
                         String title, Date lastUpdate, boolean aPrivate, String accessKey) {
        this(experimentAccession, experimentType, new HashSet(), pubmedIds, title, lastUpdate, aPrivate, accessKey);
    }

    public ExperimentDTO(String experimentAccession, ExperimentType experimentType, Set<String> species, Set<String> pubmedIds,
                         String title, Date lastUpdate, boolean isPrivate, String accessKey) {
        this.experimentAccession = experimentAccession;
        this.experimentType = experimentType;
        this.species = species;
        this.pubmedIds = pubmedIds;
        this.title = title;
        this.lastUpdate = lastUpdate;
        this.isPrivate = isPrivate;
        this.accessKey = accessKey;
    }


    public String getExperimentAccession() {
        return experimentAccession;
    }

    public ExperimentType getExperimentType() {
        return experimentType;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(experimentAccession, experimentType);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ExperimentDTO) {
            ExperimentDTO other = (ExperimentDTO) obj;
            return this.experimentAccession.equals(other.experimentAccession)
                    && this.experimentType.equals(other.experimentType);
        }
        return false;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("ExperimentAccession", experimentAccession)
                .add("ExperimentType", experimentType)
                .add("species", species)
                .add("pubmedIds", pubmedIds)
                .add("title", title)
                .add("isPrivate", isPrivate)
                .add("accessKey", accessKey)
                .add("lastUpdate", lastUpdate).toString();
    }

    public JsonObject toJson(){
        JsonObject result = new JsonObject();
        result.add("accession", new JsonPrimitive(experimentAccession));
        result.add("type", new JsonPrimitive(experimentType.name()));
        JsonArray speciesArray = new JsonArray();
        for(String specie: species){
            speciesArray.add(new JsonPrimitive(specie));
        }
        result.add("species", speciesArray);
        JsonArray pubmedIdsArray = new JsonArray();
        for(String id: pubmedIds){
            pubmedIdsArray.add(new JsonPrimitive(id));
        }
        result.add("pubmedIds", pubmedIdsArray);
        result.add("title", new JsonPrimitive(title));
        result.add("isPrivate", new JsonPrimitive(isPrivate));
        result.add("accessKey", new JsonPrimitive(accessKey));
        result.add("lastUpdate", new JsonPrimitive(lastUpdate.toString()));
        return result;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public Set<String> getSpecies() {
        return species;
    }

    public Set<String> getPubmedIds() {
        return pubmedIds;
    }

    public String getTitle() {
        return title;
    }

    public void addSpecies(String aSpecies) {
        species.add(aSpecies);
    }

}