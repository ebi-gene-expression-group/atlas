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


import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.model.differential.AssayGroup;
import uk.ac.ebi.atlas.model.differential.Contrast;
import uk.ac.ebi.atlas.model.differential.DifferentialExperiment;

import javax.inject.Named;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Named
@Scope("prototype")
public class ConditionPropertiesBuilder {

    public Collection<ConditionProperty> buildProperties(DifferentialExperiment experiment) {

        Collection<ConditionProperty> conditions = Lists.newLinkedList();

        Set<Contrast> contrasts = experiment.getContrasts();
           for (Contrast contrast : contrasts) {
               conditions.addAll(buildPropertiesForAssayGroup(experiment, "reference", contrast.getId(), contrast.getReferenceAssayGroup()));
               conditions.addAll(buildPropertiesForAssayGroup(experiment, "test", contrast.getId(), contrast.getTestAssayGroup()));
           }

        return conditions;
       }

       protected Collection<ConditionProperty> buildPropertiesForAssayGroup(DifferentialExperiment experiment,
                                                                            String assayGroupType,
                                                                            String contrastId,
                                                                            AssayGroup assayGroup) {

           Collection<ConditionProperty> conditions = Sets.newHashSet();

           for (String assayAccession : assayGroup) {
               Map<String,String> properties = experiment.getExperimentDesign().getFactors(assayAccession);

               properties.putAll(experiment.getExperimentDesign().getSamples(assayAccession));

               Set<String> values = new HashSet<>();
               for (String name : properties.keySet()) {
                   values.add(properties.get(name));

               }
               ConditionProperty conditionProperty = new ConditionProperty(experiment.getAccession(),
                       assayGroupType, contrastId, values);
               conditions.add(conditionProperty);
           }

           return conditions;
       }
}
