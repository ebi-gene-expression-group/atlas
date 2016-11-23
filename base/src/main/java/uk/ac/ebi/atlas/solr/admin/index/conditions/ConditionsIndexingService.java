package uk.ac.ebi.atlas.solr.admin.index.conditions;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import uk.ac.ebi.atlas.model.experiment.ExperimentDesign;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.trader.ConfigurationTrader;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.Collection;

@Named
public class ConditionsIndexingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConditionsIndexingService.class);

    private final SolrClient baselineConditionsCore;
    private final SolrClient differentialConditionsCore;
    private final ConditionsLookupService conditionsLookupService;
    private final ConfigurationTrader configurationTrader;

    @Inject
    public ConditionsIndexingService(@Qualifier("solrClientBaselineConditions") SolrClient baselineConditionsCore,
                                     @Qualifier("solrClientDifferentialConditions") SolrClient differentialConditionsCore,
                                     ConditionsLookupService conditionsLookupService, ConfigurationTrader configurationTrader ){
        this.baselineConditionsCore =baselineConditionsCore;
        this.differentialConditionsCore = differentialConditionsCore;
        this.conditionsLookupService = conditionsLookupService;
        this.configurationTrader = configurationTrader;
    }

    public void indexConditions(String experimentAccession, ExperimentType experimentType, ExperimentDesign
            experimentDesign){
        if(experimentType.isDifferential()){
            indexDifferential(experimentAccession, experimentDesign);
        } else {
            indexBaseline(experimentAccession,experimentDesign);
        }
    }

    public void removeConditions(String experimentAccession, ExperimentType experimentType){
        if(experimentType.isDifferential()){
            removeBeans(differentialConditionsCore, experimentAccession);
        } else {
            removeBeans(baselineConditionsCore, experimentAccession);
        }
    }


    void indexBaseline(String experimentAccession,
                              ExperimentDesign experimentDesign){

        addBeans(baselineConditionsCore,
                conditionsLookupService.buildPropertiesForBaselineExperiment
                        (experimentAccession, experimentDesign,configurationTrader.getExperimentConfiguration(experimentAccession).getAssayGroups()));
    }

    void indexDifferential(String experimentAccession,
                                  ExperimentDesign experimentDesign){
        addBeans(differentialConditionsCore,
                conditionsLookupService.buildPropertiesForDifferentialExperiment(
                        experimentAccession, experimentDesign, configurationTrader.getExperimentConfiguration
                                (experimentAccession).getContrasts()));
    }

    private static void addBeans(SolrClient solrClient, Collection propertiesBeans) {
        try {
            solrClient.addBeans(propertiesBeans);
            solrClient.commit();

        } catch (SolrServerException | IOException e) {
            LOGGER.error(e.getMessage(), e);
            throw new IllegalStateException(e);
        }
    }

    private static void removeBeans(SolrClient solrClient, String accession) {
        try {
            solrClient.deleteByQuery("experiment_accession:" + accession);
            solrClient.commit();
        } catch (SolrServerException | IOException e) {
            LOGGER.error(e.getMessage(), e);
            throw new IllegalStateException(e);
        }
    }
}
