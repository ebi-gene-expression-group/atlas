package uk.ac.ebi.atlas.solr.admin.index.conditions;

import uk.ac.ebi.atlas.model.Experiment;
import com.google.common.collect.SetMultimap;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collection;

public abstract class ConditionsIndex {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConditionsIndex.class);

    private SolrClient solrClient;

    private ConditionsBuilder propertiesBuilder;


    public ConditionsIndex(SolrClient solrClient, ConditionsBuilder propertiesBuilder) {
        this.solrClient = solrClient;
        this.propertiesBuilder = propertiesBuilder;
    }


    public void updateConditions(Experiment experiment, SetMultimap<String, String> ontologyTerms) {

        LOGGER.info("<updateConditions> {}", experiment.getAccession());

        removeConditions(experiment.getAccession());

        addConditions(experiment, ontologyTerms);
    }

    public void addConditions(Experiment experiment, SetMultimap<String, String> assayGroupIdToOntologyTermIds) {
        try {

            Collection propertiesBeans = propertiesBuilder.buildProperties(experiment, assayGroupIdToOntologyTermIds);

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

}
