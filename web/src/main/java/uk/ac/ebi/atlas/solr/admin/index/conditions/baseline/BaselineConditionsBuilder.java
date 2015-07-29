package uk.ac.ebi.atlas.solr.admin.index.conditions.baseline;

import com.google.common.collect.Lists;
import com.google.common.collect.SetMultimap;
import com.google.common.collect.Sets;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.AssayGroups;
import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.solr.admin.index.conditions.Condition;
import uk.ac.ebi.atlas.solr.admin.index.conditions.ConditionsBuilder;

import javax.inject.Named;
import java.util.Collection;
import java.util.Set;

@Named
@Scope("prototype")
public class BaselineConditionsBuilder extends ConditionsBuilder<BaselineExperiment> {
    @Override
    public Collection<Condition> buildProperties(BaselineExperiment experiment, SetMultimap<String, String> ontologyTermIdsByAssayAccession) {
        Collection<Condition> conditions = Lists.newLinkedList();

        AssayGroups assayGroups = experiment.getAssayGroups();
        for (AssayGroup assayGroup : assayGroups) {
            conditions.addAll(buildPropertiesForAssayGroup(experiment, assayGroup, ontologyTermIdsByAssayAccession));
        }

        return conditions;

    }

    protected Collection<Condition> buildPropertiesForAssayGroup(Experiment experiment, AssayGroup assayGroup, SetMultimap<String, String> ontologyTermIdsByAssayAccession) {

        Collection<Condition> conditions = Sets.newHashSet();

        for (String assayAccession : assayGroup) {

            Set<String> values = collectAssayProperties(experiment.getExperimentDesign(), assayAccession, ontologyTermIdsByAssayAccession);
            Condition condition = new Condition(experiment.getAccession(),
                    assayGroup.getId(), values);
            conditions.add(condition);
        }

        return conditions;
    }

}
