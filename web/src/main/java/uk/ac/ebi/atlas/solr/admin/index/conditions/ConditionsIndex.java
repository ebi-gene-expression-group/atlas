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

    private HttpSolrServer conditionsSolrServer;

    private ConditionPropertiesBuilder conditionPropertiesBuilder;

    @Inject
    public ConditionsIndex(HttpSolrServer conditionsSolrServer, ConditionPropertiesBuilder conditionPropertiesBuilder) {
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
