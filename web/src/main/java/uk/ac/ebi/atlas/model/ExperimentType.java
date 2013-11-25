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


import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum ExperimentType {

    RNASEQ_MRNA_BASELINE("rnaseq_mrna_baseline")
    ,RNASEQ_MRNA_DIFFERENTIAL("rnaseq_mrna_differential")
    ,MICROARRAY_ANY("microarray parent type")
    ,MICROARRAY_1COLOUR_MRNA_DIFFERENTIAL(MICROARRAY_ANY, "microarray_1colour_mrna_differential")
    ,MICROARRAY_2COLOUR_MRNA_DIFFERENTIAL(MICROARRAY_ANY, "microarray_2colour_mrna_differential")
    ,MICROARRAY_1COLOUR_MICRORNA_DIFFERENTIAL(MICROARRAY_ANY, "microarray_1colour_microrna_differential");

    private ExperimentType parent;
    private String description;

    private ExperimentType(String description) {
        this.description = description;
    }

    private ExperimentType(ExperimentType parent, String description) {
        this(description);
        this.parent = parent;
    }

    public boolean isMicroarray() {
        return getParent().equals(MICROARRAY_ANY);
    }

    public boolean isBaseline() {
        return equals(RNASEQ_MRNA_BASELINE);
    }

    public boolean isMicroRna() {
        return equals(MICROARRAY_1COLOUR_MICRORNA_DIFFERENTIAL);
    }

    public boolean isDifferential() {
        return equals(RNASEQ_MRNA_DIFFERENTIAL);
    }

    public boolean isTwoColour() {
        return equals(MICROARRAY_2COLOUR_MRNA_DIFFERENTIAL);
    }

    public ExperimentType getParent() {
        return parent == null ? this : parent;
    }

    public String getDescription() {
        return description;
    }

    private static final Map<String,ExperimentType> typeByDescription = new HashMap<>();

    static {
        for(ExperimentType experimentType : EnumSet.allOf(ExperimentType.class))
            typeByDescription.put(experimentType.getDescription(), experimentType);
    }

    public static ExperimentType get(String experimentTypeDescription) {
        return typeByDescription.get(experimentTypeDescription.toLowerCase());
    }

}
