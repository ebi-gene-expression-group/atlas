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

package uk.ac.ebi.atlas.solr.admin.index.conditions.differential;


import com.google.common.collect.Lists;
import com.google.common.collect.SetMultimap;
import com.google.common.collect.Sets;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.differential.Contrast;
import uk.ac.ebi.atlas.model.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.solr.admin.index.conditions.ConditionsBuilder;

import javax.inject.Named;
import java.util.Collection;
import java.util.Set;

@Named
@Scope("prototype")
public class DifferentialConditionsBuilder extends ConditionsBuilder<DifferentialExperiment> {

    public Collection<DifferentialCondition> buildProperties(DifferentialExperiment experiment, SetMultimap<String, String> ontologyTermIdsByAssayAccession) {

        Collection<DifferentialCondition> conditions = Lists.newLinkedList();

        Set<Contrast> contrasts = experiment.getContrasts();
        for (Contrast contrast : contrasts) {
            conditions.addAll(buildPropertiesForAssayGroup(experiment, contrast.getId(), contrast.getReferenceAssayGroup(), ontologyTermIdsByAssayAccession));
            conditions.addAll(buildPropertiesForAssayGroup(experiment, contrast.getId(), contrast.getTestAssayGroup(), ontologyTermIdsByAssayAccession));
        }

        return conditions;
    }

    protected Collection<DifferentialCondition> buildPropertiesForAssayGroup(DifferentialExperiment experiment,
                                                                             String contrastId,
                                                                             AssayGroup assayGroup, SetMultimap<String, String> ontologyTermIdsByAssayAccession) {

        Collection<DifferentialCondition> conditions = Sets.newHashSet();

        for (String assayAccession : assayGroup) {
            Set<String> values = collectAssayProperties(experiment.getExperimentDesign(), assayAccession, ontologyTermIdsByAssayAccession);

            DifferentialCondition differentialCondition = new DifferentialCondition(experiment.getAccession(),
                    assayGroup.getId(), contrastId, values);
            conditions.add(differentialCondition);
        }

        return conditions;
    }
}
