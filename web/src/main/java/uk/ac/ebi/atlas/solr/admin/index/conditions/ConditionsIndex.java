/*
 * Copyright 2008-2013 Microarray Informatics Team, EMBL-European Bioinformatics Institute
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
 * For further details of the Gene Expression Atlas project, including source code,
 * downloads and documentation, please see:
 *
 * http://gxa.github.com/gxa
 */

package uk.ac.ebi.atlas.solr.admin.index.conditions;

import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.model.ExperimentTrader;
import uk.ac.ebi.atlas.model.differential.DifferentialExperiment;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.Collection;

@Named
@Scope("prototype")
public class ConditionsIndex {

    private static final Logger LOGGER = Logger.getLogger(ConditionsIndex.class);

    private SolrServer differentialConditionsSolrServer;

    private ConditionPropertiesBuilder conditionPropertiesBuilder;

    private ExperimentTrader experimentTrader;

    @Inject
    public ConditionsIndex(SolrServer differentialConditionsSolrServer, ConditionPropertiesBuilder conditionPropertiesBuilder, ExperimentTrader experimentTrader) {
        this.differentialConditionsSolrServer = differentialConditionsSolrServer;
        this.conditionPropertiesBuilder = conditionPropertiesBuilder;
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
        try{
            Experiment experiment = experimentTrader.getPublicExperiment(experimentAccession);

            if(experiment instanceof DifferentialExperiment){
                //ToDo: (NK) find a way to remove casting
                Collection<ConditionProperty> conditionProperties =
                        conditionPropertiesBuilder.buildProperties((DifferentialExperiment)experiment);

                differentialConditionsSolrServer.addBeans(conditionProperties);

                differentialConditionsSolrServer.commit();
            }

        } catch (SolrServerException | IOException e) {
            LOGGER.error(e.getMessage(), e);
            throw new IllegalStateException(e);
        }
    }

    public void removeConditions(String accession) {
        try {
            differentialConditionsSolrServer.deleteByQuery("experiment_accession:" + accession);
            differentialConditionsSolrServer.commit();
        } catch (SolrServerException | IOException e) {
            LOGGER.error(e.getMessage(), e);
            throw new IllegalStateException(e);
        }
    }

    void optimize() {
        try {
            differentialConditionsSolrServer.optimize();

        } catch (SolrServerException | IOException e) {
            LOGGER.error(e.getMessage(), e);
            throw new IllegalStateException(e);
        }

    }
}
