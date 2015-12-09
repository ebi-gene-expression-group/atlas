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

package uk.ac.ebi.atlas.model.differential;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.model.ExperimentDesign;
import uk.ac.ebi.atlas.model.ExperimentType;

import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Set;

import static com.google.common.base.Preconditions.checkArgument;

public class DifferentialExperiment extends Experiment {

    private LinkedHashMap<String, Contrast> contrastsById = Maps.newLinkedHashMap();

    public DifferentialExperiment(String accession, Date lastUpdate, Set<Contrast> contrasts, String description, boolean hasExtraInfoFile, Set<String> species, String kingdom, String ensemblDB, Set<String> pubMedIds, ExperimentDesign experimentDesign) {
        this(ExperimentType.RNASEQ_MRNA_DIFFERENTIAL, accession, lastUpdate, contrasts, description, hasExtraInfoFile, species, kingdom, ensemblDB, pubMedIds, experimentDesign);
    }

    protected DifferentialExperiment(ExperimentType experimentType, String accession, Date lastUpdate, Set<Contrast> contrasts, String description, boolean hasExtraInfoFile, Set<String> species, String kingdom, String ensemblDB, Set<String> pubMedIds, ExperimentDesign experimentDesign) {
        super(experimentType, accession, lastUpdate, description, hasExtraInfoFile, species, kingdom, ensemblDB, null, pubMedIds, experimentDesign);
        for (Contrast contrast : contrasts) {
            contrastsById.put(contrast.getId(), contrast);
        }
    }

    public Set<Contrast> getContrasts() {
        return Sets.newLinkedHashSet(contrastsById.values());
    }

    public Contrast getContrast(String contrastId) {
        Contrast contrast = contrastsById.get(contrastId);
        checkArgument(contrast != null, this.getAccession() + ": cannot find a contrast with contrastId: " + contrastId);
        return contrast;
    }

    public Set<String> getContrastIds() {
        return Collections.unmodifiableSet(contrastsById.keySet());
    }

    public Set<String> getAssayAccessions() {
        Set<String> assayAccessions = Sets.newHashSet();
        for (Contrast contrast : getContrasts()) {
            for (String assayAccession : contrast.getReferenceAssayGroup()) {
                assayAccessions.add(assayAccession);
            }
            for (String assayAccession : contrast.getTestAssayGroup()) {
                assayAccessions.add(assayAccession);
            }
        }

        return assayAccessions;
    }

}
