package uk.ac.ebi.atlas.solr.admin.index.conditions;

import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
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

    public void indexExperiment(DifferentialExperiment experiment) {

        LOGGER.info("<indexExperiment> " + experiment.getAccession());

        try {
            deleteExperiment(experiment.getAccession());

            Collection<ConditionProperty> conditionProperties = conditionPropertiesBuilder.buildProperties(experiment);

            conditionsSolrServer.addBeans(conditionProperties);

            conditionsSolrServer.commit();

            //ToDO: (NK) Do we need to optimise after every experiment?
            optimize();

        } catch (SolrServerException | IOException e) {
            LOGGER.error(e.getMessage(), e);
            throw new IllegalStateException(e);
        }

    }

    public void deleteExperiment(String accession) {
//        try {
//            LOGGER.debug("<deleteExperiment> conditionsSolrServer.host: " + ((HttpSolrServer) conditionsSolrServer).getBaseURL());
//            conditionsSolrServer.deleteByQuery("experiment_accession:" + accession);
//            conditionsSolrServer.commit();
//        } catch (SolrServerException | IOException e) {
//            LOGGER.error(e.getMessage(), e);
//            throw new IllegalStateException(e);
//        }
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
