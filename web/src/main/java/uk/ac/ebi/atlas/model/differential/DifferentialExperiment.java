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

package uk.ac.ebi.atlas.model.differential;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.model.ExperimentType;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;

import static com.google.common.base.Preconditions.checkState;

public class DifferentialExperiment extends Experiment {

    private Map<String, Contrast> contrastsById = Maps.newHashMap();

    public DifferentialExperiment(String accession, Set<Contrast> contrasts, String description, boolean hasExtraInfoFile, Set<String> species, List<String> pubMedIds) {
        this(ExperimentType.DIFFERENTIAL, accession, contrasts, description, hasExtraInfoFile, species, pubMedIds);
    }

    protected DifferentialExperiment(ExperimentType experimentType, String accession, Set<Contrast> contrasts, String description, boolean hasExtraInfoFile, Set<String> species, List<String> pubMedIds) {
        super(experimentType, accession, description, hasExtraInfoFile, species, null, pubMedIds);
        for (Contrast contrast : contrasts) {
            contrastsById.put(contrast.getId(), contrast);
        }
    }

    public SortedSet<Contrast> getContrasts() {
        SortedSet<Contrast> contrasts = Sets.newTreeSet();
        contrasts.addAll(contrastsById.values());
        return contrasts;
    }

    public Contrast getContrast(String contrastId) {
        Contrast contrast = contrastsById.get(contrastId);
        checkState(contrast != null, "Cannot find a contrast with contrastId: " + contrastId);
        return contrast;
    }

    public SortedSet<String> getContrastIds() {
        return Sets.newTreeSet(contrastsById.keySet());
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
