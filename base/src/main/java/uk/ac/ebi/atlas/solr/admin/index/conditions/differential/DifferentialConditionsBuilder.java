package uk.ac.ebi.atlas.solr.admin.index.conditions.differential;


import com.google.common.collect.Lists;
import com.google.common.collect.SetMultimap;
import com.google.common.collect.Sets;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.experimentimport.efo.EFOLookupService;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.model.differential.Contrast;
import uk.ac.ebi.atlas.model.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.solr.admin.index.conditions.ConditionsBuilder;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Collection;
import java.util.Set;

@Named
@Scope("prototype")
public class DifferentialConditionsBuilder extends ConditionsBuilder {

    @Inject
    public DifferentialConditionsBuilder(EFOLookupService efoLookupService) {
        super(efoLookupService);
    }

    @Override
    protected Collection<DifferentialCondition> buildProperties(Experiment experiment, SetMultimap<String, String>
            ontologyTermIdsByAssayAccession) {

        Collection<DifferentialCondition> conditions = Lists.newLinkedList();

        DifferentialExperiment differentialExperiment = (DifferentialExperiment)experiment;

        Set<Contrast> contrasts = differentialExperiment.getContrasts();
        for (Contrast contrast : contrasts) {
            conditions.addAll(buildPropertiesForAssayGroup(differentialExperiment, contrast.getId(), contrast.getReferenceAssayGroup(), ontologyTermIdsByAssayAccession));
            conditions.addAll(buildPropertiesForAssayGroup(differentialExperiment, contrast.getId(), contrast.getTestAssayGroup(), ontologyTermIdsByAssayAccession));
        }

        return conditions;
    }

    private Collection<DifferentialCondition> buildPropertiesForAssayGroup(DifferentialExperiment experiment,
                                                                             String contrastId,
                                                                             AssayGroup assayGroup, SetMultimap<String, String> ontologyTermIdsByAssayAccession) {

        Collection<DifferentialCondition> conditions = Sets.newHashSet();

        for (String assayAccession : assayGroup) {

            conditions.add(new DifferentialCondition(experiment.getAccession(),
                    assayGroup.getId(), contrastId,
                    collectAssayProperties(experiment.getExperimentDesign(),
                            assayAccession, ontologyTermIdsByAssayAccession)));
        }

        return conditions;
    }
}
