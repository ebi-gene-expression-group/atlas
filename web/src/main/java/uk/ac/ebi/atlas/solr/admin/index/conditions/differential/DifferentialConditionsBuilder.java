
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
