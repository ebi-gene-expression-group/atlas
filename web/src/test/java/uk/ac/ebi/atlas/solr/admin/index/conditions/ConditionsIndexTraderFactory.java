package uk.ac.ebi.atlas.solr.admin.index.conditions;

import org.apache.solr.client.solrj.SolrServer;
import uk.ac.ebi.atlas.solr.admin.index.conditions.baseline.BaselineConditionsBuilder;
import uk.ac.ebi.atlas.solr.admin.index.conditions.baseline.BaselineConditionsIndex;
import uk.ac.ebi.atlas.solr.admin.index.conditions.differential.DifferentialConditionsBuilder;
import uk.ac.ebi.atlas.solr.admin.index.conditions.differential.DifferentialConditionsIndex;

import javax.inject.Named;

@Named
public class ConditionsIndexTraderFactory {

    public ConditionsIndexTrader create(SolrServer solrServer) {
        BaselineConditionsIndex baselineConditionIndex = new BaselineConditionsIndex(solrServer, new BaselineConditionsBuilder());
        DifferentialConditionsIndex differentialConditionIndex = new DifferentialConditionsIndex(solrServer, new DifferentialConditionsBuilder());
        return new ConditionsIndexTrader(baselineConditionIndex, differentialConditionIndex);
    }

}
