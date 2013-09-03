package uk.ac.ebi.atlas.solr.admin.index.conditions;

import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.model.differential.DifferentialExperiment;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.Collection;

@Named
@Scope("prototype")
public class ConditionsIndex {

    private static final Logger LOGGER = Logger.getLogger(ConditionsIndex.class);

    private SolrServer conditionsSolrServer;

    private ConditionPropertiesBuilder conditionPropertiesBuilder;

    @Inject
    public ConditionsIndex(SolrServer conditionsSolrServer, ConditionPropertiesBuilder conditionPropertiesBuilder) {
        this.conditionsSolrServer = conditionsSolrServer;
        this.conditionPropertiesBuilder = conditionPropertiesBuilder;
    }

    public void indexExperiment(DifferentialExperiment experiment) throws IOException, SolrServerException {

        LOGGER.info("<indexExperiment> " + experiment.getAccession());

        deleteExperiment(experiment.getAccession());

        Collection<ConditionProperty> conditionProperties = conditionPropertiesBuilder.buildProperties(experiment);

        conditionsSolrServer.addBeans(conditionProperties);

        conditionsSolrServer.commit();

        optimize();

    }

    public void deleteExperiment(String accession) {
        try {
            conditionsSolrServer.deleteByQuery("experiment_accession:" + accession);
            conditionsSolrServer.commit();
        } catch (SolrServerException | IOException e) {
            LOGGER.error(e.getMessage(), e);
            throw new IllegalStateException(e);
        }
    }

    void optimize() {
        try {
            conditionsSolrServer.optimize();

        } catch (SolrServerException | IOException e) {
            LOGGER.error(e.getMessage(), e);
            throw new IllegalStateException(e);
        }

    }
}
