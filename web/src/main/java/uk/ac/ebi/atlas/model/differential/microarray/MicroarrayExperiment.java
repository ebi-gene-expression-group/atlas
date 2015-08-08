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

package uk.ac.ebi.atlas.model.differential.microarray;


import uk.ac.ebi.atlas.model.ExperimentDesign;
import uk.ac.ebi.atlas.model.ExperimentType;
import uk.ac.ebi.atlas.model.differential.Contrast;
import uk.ac.ebi.atlas.model.differential.DifferentialExperiment;

import java.util.Date;
import java.util.Set;
import java.util.SortedSet;

public class MicroarrayExperiment extends DifferentialExperiment {

    private SortedSet<String> arrayDesignAccessions;

    private boolean isTwoColour = false;

    public MicroarrayExperiment(ExperimentType type, String accession, Date lastUpdate, Set<Contrast> contrasts,
                                String description, boolean hasExtraInfoFile,
                                Set<String> species, String kingdom, String ensemblDB, SortedSet<String> arrayDesignAccessions, boolean twoColour,
                                Set<String> pubMedIds, ExperimentDesign experimentDesign) {

        super(type, accession, lastUpdate, contrasts, description, hasExtraInfoFile, species, kingdom, ensemblDB, pubMedIds, experimentDesign);
        this.arrayDesignAccessions = arrayDesignAccessions;

        isTwoColour = twoColour;
    }

    public SortedSet<String> getArrayDesignAccessions() {
        return arrayDesignAccessions;
    }

    public boolean isTwoColour() {
        return isTwoColour;
    }
}
