package uk.ac.ebi.atlas.solr.admin.index.experiment;

import org.apache.solr.client.solrj.SolrServer;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;

import javax.inject.Inject;
import javax.inject.Named;

@Named
@Scope("prototype")
public class FactorIndex extends ExperimentDesignIndex<BaselineExperiment, FactorProperty> {

    @Inject
    public FactorIndex(SolrServer factorSolrServer, FactorPropertiesBuilder propertiesBuilder) {
        super(factorSolrServer, propertiesBuilder);
    }


}
