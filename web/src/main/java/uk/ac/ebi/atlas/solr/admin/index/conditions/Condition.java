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

package uk.ac.ebi.atlas.solr.admin.index.conditions;

import org.apache.solr.client.solrj.beans.Field;

import java.util.Collection;
import java.util.Objects;

public class Condition {

    @Field("experiment_accession")
    private String experimentAccession;

    @Field("assay_group_id")
    private String assayGroupId;

    @Field("conditions")
    private Collection<String> values;

    public Condition() {
    }

    public Condition(String experimentAccession, String assayGroupId, Collection<String> values) {
        this.experimentAccession = experimentAccession;
        this.assayGroupId = assayGroupId;
        this.values = values;
    }

    public String getExperimentAccession() {
        return experimentAccession;
    }

    public void setExperimentAccession(String experimentAccession) {
        this.experimentAccession = experimentAccession;
    }

    public String getAssayGroupId() {
        return assayGroupId;
    }

    public void setAssayGroupId(String assayGroupId) {
        this.assayGroupId = assayGroupId;
    }

    public Collection<String> getValues() {
        return values;
    }

    public void setValues(Collection<String> values) {
        this.values = values;
    }

    @Override
    public int hashCode() {return Objects.hash(experimentAccession, assayGroupId, values);}

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {return true;}
        if (obj == null || getClass() != obj.getClass()) {return false;}
        final Condition other = (Condition) obj;
        return Objects.equals(this.experimentAccession, other.experimentAccession) && Objects.equals(this.assayGroupId, other.assayGroupId) && Objects.equals(this.values, other.values);
    }
}
