package uk.ac.ebi.atlas.solr.admin.index.conditions.baseline;

import com.google.common.collect.Lists;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.solr.admin.index.conditions.ConditionsBuilder;

import javax.inject.Named;
import java.util.Collection;

@Named
@Scope("prototype")
public class BaselineConditionsBuilder extends ConditionsBuilder<BaselineExperiment> {
    @Override
    public Collection<BaselineCondition> buildProperties(BaselineExperiment experiment) {
        Collection<BaselineCondition> conditions = Lists.newLinkedList();

        return conditions;

    }
}
