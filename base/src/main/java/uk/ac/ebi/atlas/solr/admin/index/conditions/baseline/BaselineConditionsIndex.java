package uk.ac.ebi.atlas.solr.admin.index.conditions.baseline;

import org.apache.solr.client.solrj.SolrClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.solr.admin.index.conditions.ConditionsIndex;

import javax.inject.Inject;
import javax.inject.Named;

@Named
@Scope("prototype")
public class BaselineConditionsIndex extends ConditionsIndex {

    @Inject
    public BaselineConditionsIndex(@Qualifier("solrClientBaselineConditions") SolrClient solrClient,
                                   BaselineConditionsBuilder propertiesBuilder) {
        super(solrClient, propertiesBuilder);
    }
}
