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

package uk.ac.ebi.atlas.experimentloader;

import com.google.common.base.Objects;
import uk.ac.ebi.atlas.model.ExperimentType;

import java.util.Date;

public class ExperimentDTO {

    private final String accessKey;

    private String experimentAccession;

    private ExperimentType experimentType;

    private Date lastUpdate;

    private boolean isPrivate;

    public ExperimentDTO(String experimentAccession, ExperimentType experimentType, Date lastUpdate, boolean isPrivate, String accessKey) {
        this.experimentAccession = experimentAccession;
        this.experimentType = experimentType;
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
                .add("isPrivate", isPrivate)
                .add("accessKey", accessKey)
                .add("lastUpdate", lastUpdate).toString();
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public String getAccessKey() {
        return accessKey;
    }
}