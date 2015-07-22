package uk.ac.ebi.atlas.solr.admin.index.conditions;

import com.google.common.collect.SetMultimap;
import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import uk.ac.ebi.atlas.model.Experiment;

import java.io.IOException;
import java.util.Collection;

public abstract class ConditionsIndex<T extends Experiment> {

    private static final Logger LOGGER = Logger.getLogger(ConditionsIndex.class);

    private SolrClient solrClient;

    private ConditionsBuilder<T> propertiesBuilder;


    public ConditionsIndex(SolrClient solrClient, ConditionsBuilder<T> propertiesBuilder) {
        this.solrClient = solrClient;
        this.propertiesBuilder = propertiesBuilder;
    }


    public void updateConditions(T experiment, SetMultimap<String, String> ontologyTerms) {

        LOGGER.info("<updateConditions> " + experiment.getAccession());

        removeConditions(experiment.getAccession());

        addConditions(experiment, ontologyTerms);

        //ToDO: (NK) Do we need to optimise after every experiment?
        optimize();

    }

    public void addConditions(T experiment, SetMultimap<String, String> ontologyTermIdsByAssayAccession) {
        try {

            Collection propertiesBeans = propertiesBuilder.buildProperties(experiment, ontologyTermIdsByAssayAccession);

            solrClient.addBeans(propertiesBeans);
            solrClient.commit();

        } catch (SolrServerException | IOException e) {
            LOGGER.error(e.getMessage(), e);
            throw new IllegalStateException(e);
        }
    }

    public void removeConditions(String accession) {
        try {
            solrClient.deleteByQuery("experiment_accession:" + accession);
            solrClient.commit();
        } catch (SolrServerException | IOException e) {
            LOGGER.error(e.getMessage(), e);
            throw new IllegalStateException(e);
        }
    }

    void optimize() {
        try {
            solrClient.optimize();

        } catch (SolrServerException | IOException e) {
            LOGGER.error(e.getMessage(), e);
            throw new IllegalStateException(e);
        }

    }
}
