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

import com.google.common.base.Function;
import com.google.common.collect.Maps;
import com.google.common.collect.Ordering;
import com.google.common.collect.Sets;
import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.model.ExperimentType;

import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;

public class DifferentialExperiment extends Experiment{

    private Map<String, Contrast> contrastsById = Maps.newHashMap();

    public DifferentialExperiment(String accession, Set<Contrast> contrasts, String description, boolean hasExtraInfoFile, Set<String> species) {
        super(ExperimentType.DIFFERENTIAL, accession, description, hasExtraInfoFile, species);
        for (Contrast contrast: contrasts){
            this.contrastsById.put(contrast.getId(), contrast);
        }
    }

    public SortedSet<Contrast> getContrasts(){
        SortedSet<Contrast> contrasts = Sets.newTreeSet(orderByDisplayName());
        contrasts.addAll(this.contrastsById.values());
        return contrasts;
    }

    public Contrast getContrast(String contrastId) {
        return this.contrastsById.get(contrastId);
    }

    private Comparator<Contrast> orderByDisplayName(){
        return Ordering.natural().onResultOf(new Function<Contrast, Comparable>() {
            @Override
            public Comparable apply(Contrast contrast) {
                return contrast.getDisplayName();
            }
        });
    }

}
