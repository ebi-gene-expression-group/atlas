package uk.ac.ebi.atlas.solr.admin.index.conditions;


import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.model.differential.AssayGroup;
import uk.ac.ebi.atlas.model.differential.Contrast;
import uk.ac.ebi.atlas.model.differential.DifferentialExperiment;

import javax.inject.Named;
import java.util.Collection;
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
                                                                            String groupType,
                                                                            String contrastId,
                                                                            AssayGroup assayGroup) {

           Collection<ConditionProperty> conditions = Sets.newHashSet();

           for (String assayAccession : assayGroup) {
               Map<String,String> properties = experiment.getExperimentDesign().getFactors(assayAccession);

               properties.putAll(experiment.getExperimentDesign().getSamples(assayAccession));

               for (String name : properties.keySet()) {
                   ConditionProperty conditionProperty = new ConditionProperty(experiment.getAccession(),
                           groupType, contrastId, name, properties.get(name));
                   conditions.add(conditionProperty);
               }
           }

           return conditions;
       }
}
