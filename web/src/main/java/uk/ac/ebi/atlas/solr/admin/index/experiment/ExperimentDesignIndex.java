package uk.ac.ebi.atlas.solr.admin.index.experiment;

import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import uk.ac.ebi.atlas.model.Experiment;

import java.io.IOException;
import java.util.Collection;

public abstract class ExperimentDesignIndex<T extends Experiment, M> {

    private static final Logger LOGGER = Logger.getLogger(ExperimentDesignIndex.class);

    private SolrServer solrServer;

    private PropertiesBuilder<T, M> propertiesBuilder;


    public ExperimentDesignIndex(SolrServer solrServer, PropertiesBuilder<T, M> propertiesBuilder) {
        this.solrServer = solrServer;
        this.propertiesBuilder = propertiesBuilder;
    }

    public void updateConditions(T experiment) {

        LOGGER.info("<updateConditions> " + experiment.getAccession());

        removeConditions(experiment.getAccession());

        addConditions(experiment);

        //ToDO: (NK) Do we need to optimise after every experiment?
        optimize();

    }

    public void addConditions(T experiment) {
        try {

            Collection<M> propertiesBeans = propertiesBuilder.buildProperties(experiment);

            solrServer.addBeans(propertiesBeans);
            solrServer.commit();

        } catch (SolrServerException | IOException e) {
            LOGGER.error(e.getMessage(), e);
            throw new IllegalStateException(e);
        }
    }

    public void removeConditions(String accession) {
        try {
            solrServer.deleteByQuery("experiment_accession:" + accession);
            solrServer.commit();
        } catch (SolrServerException | IOException e) {
            LOGGER.error(e.getMessage(), e);
            throw new IllegalStateException(e);
        }
    }

    void optimize() {
        try {
            solrServer.optimize();

        } catch (SolrServerException | IOException e) {
            LOGGER.error(e.getMessage(), e);
            throw new IllegalStateException(e);
        }

    }
}
