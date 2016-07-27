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

@Named
@Scope("prototype")
public class BaselineConditionsBuilder extends ConditionsBuilder {

    @Override
    public Collection<Condition> buildProperties(Experiment experiment, SetMultimap<String, String> assayGroupIdToOntologyTermIds) {

        Collection<Condition> conditions = Lists.newLinkedList();

        BaselineExperiment baselineExperiment = (BaselineExperiment)experiment;

        AssayGroups assayGroups = baselineExperiment.getAssayGroups();
        for (AssayGroup assayGroup : assayGroups) {
            conditions.addAll(buildPropertiesForAssayGroup(baselineExperiment, assayGroup, assayGroupIdToOntologyTermIds));
        }

        return conditions;

    }

    private Collection<Condition> buildPropertiesForAssayGroup(BaselineExperiment experiment, AssayGroup assayGroup, SetMultimap<String, String> assayGroupIdToOntologyTermIds) {

        Collection<Condition> conditions = Sets.newHashSet();

        for (String assayAccession : assayGroup) {
            conditions.add(new Condition(experiment.getAccession(), assayGroup.getId(),  collectAssayProperties(experiment.getExperimentDesign(), assayAccession, assayGroupIdToOntologyTermIds)));
        }

        return conditions;
    }

}
