package uk.ac.ebi.atlas.solr.admin.index.experiment;

import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.model.ExperimentTrader;

import java.io.IOException;
import java.util.Collection;

public abstract class ExperimentIndex<T extends Experiment, M> {

    private static final Logger LOGGER = Logger.getLogger(ExperimentIndex.class);

    private SolrServer solrServer;

    private PropertiesBuilder<T, M> propertiesBuilder;

    private ExperimentTrader experimentTrader;


    public ExperimentIndex(SolrServer solrServer, PropertiesBuilder<T, M> propertiesBuilder, ExperimentTrader experimentTrader) {
        this.solrServer = solrServer;
        this.propertiesBuilder = propertiesBuilder;
        this.experimentTrader = experimentTrader;
    }

    public void updateConditions(String experimentAccession) {

        LOGGER.info("<updateConditions> " + experimentAccession);

        removeConditions(experimentAccession);

        addConditions(experimentAccession);

        //ToDO: (NK) Do we need to optimise after every experiment?
        optimize();

    }

    public void addConditions(String experimentAccession) {
        try {
            T experiment = getExperiment(experimentAccession);

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

    protected abstract T getExperiment(String accesstion);
}
