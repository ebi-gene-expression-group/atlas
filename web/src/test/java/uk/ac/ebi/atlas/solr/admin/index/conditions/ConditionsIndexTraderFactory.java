package uk.ac.ebi.atlas.solr.admin.index.conditions;

import org.apache.solr.client.solrj.SolrClient;
import uk.ac.ebi.atlas.solr.admin.index.conditions.baseline.BaselineConditionsBuilder;
import uk.ac.ebi.atlas.solr.admin.index.conditions.baseline.BaselineConditionsIndex;
import uk.ac.ebi.atlas.solr.admin.index.conditions.differential.DifferentialConditionsBuilder;
import uk.ac.ebi.atlas.solr.admin.index.conditions.differential.DifferentialConditionsIndex;

import javax.inject.Named;

@Named
public class ConditionsIndexTraderFactory {

    public ConditionsIndexTrader create(SolrClient solrClient) {
        BaselineConditionsIndex baselineConditionIndex = new BaselineConditionsIndex(solrClient, new BaselineConditionsBuilder());
        DifferentialConditionsIndex differentialConditionIndex = new DifferentialConditionsIndex(solrClient, new DifferentialConditionsBuilder());
        return new ConditionsIndexTrader(baselineConditionIndex, differentialConditionIndex);
    }

}
