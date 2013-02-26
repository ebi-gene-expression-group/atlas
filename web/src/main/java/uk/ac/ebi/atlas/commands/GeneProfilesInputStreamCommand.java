/*
 * Copyright 2008-2012 Microarray Informatics Team, EMBL-European Bioinformatics Institute
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

package uk.ac.ebi.atlas.commands;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.geneindex.InvalidQueryException;
import uk.ac.ebi.atlas.geneindex.SolrClient;
import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.model.ExperimentalFactors;
import uk.ac.ebi.atlas.model.Factor;
import uk.ac.ebi.atlas.model.GeneProfile;
import uk.ac.ebi.atlas.model.caches.ExperimentsCache;
import uk.ac.ebi.atlas.streams.GeneProfileInputStreamBuilder;
import uk.ac.ebi.atlas.streams.GeneProfileInputStreamFilter;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.Set;
import java.util.SortedSet;

public abstract class GeneProfilesInputStreamCommand<T> {
    protected static final Logger logger = Logger.getLogger(RankGeneProfilesCommand.class);

    private GeneProfileInputStreamBuilder geneProfileInputStreamBuilder;

    private ExperimentsCache experimentsCache;

    private SessionContext sessionContext;

    private SolrClient solrClient;

    @Inject
    protected void setGeneProfileInputStreamBuilder(GeneProfileInputStreamBuilder geneProfileInputStreamBuilder, SessionContext sessionContext) {
        this.geneProfileInputStreamBuilder = geneProfileInputStreamBuilder;
        this.sessionContext = sessionContext;
    }

    @Inject
    public void setExperimentsCache(ExperimentsCache experimentsCache) {
        this.experimentsCache = experimentsCache;
    }

    @Inject
    public void setSolrClient(SolrClient solrClient) {
        this.solrClient = solrClient;
    }

    public void setFilteredParameters(SessionContext sessionContext) {
        this.sessionContext = sessionContext;
    }

    @NotNull
    public T apply(String experimentAccession) throws GeneNotFoundException {

        Experiment experiment = experimentsCache.getExperiment(experimentAccession);

        Set<String> selectedGeneIds = null;

        if (StringUtils.isNotBlank(sessionContext.getGeneQuery())) {

            try {
                selectedGeneIds = searchForGeneIds(experiment);
                if (selectedGeneIds.isEmpty()) {
                    return returnEmpty();
                }
            } catch (InvalidQueryException e) {
                throw new GeneNotFoundException(e.getMessage());

            }
        }

        ObjectInputStream<GeneProfile> geneProfileInputStream = geneProfileInputStreamBuilder.forExperiment(experimentAccession)
                .createGeneProfileInputStream();

        try (ObjectInputStream<GeneProfile> inputStream = new GeneProfileInputStreamFilter(geneProfileInputStream, selectedGeneIds, sessionContext.getSelectedQueryFactors())) {

            ExperimentalFactors experimentalFactors = experiment.getExperimentalFactors();

            SortedSet<Factor> filteredFactors = experimentalFactors.getFilteredFactors(sessionContext.getSelectedFilterFactors());
            return apply(filteredFactors, sessionContext.getSelectedQueryFactors(), inputStream);

        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new IllegalStateException("IOException when invoking ObjectInputStream.close()");
        }
    }

    protected Set<String> searchForGeneIds(Experiment experiment) {

        return solrClient.findGeneIds(sessionContext.getGeneQuery(), sessionContext.getFilteredBySpecies());

    }

    protected abstract T apply(SortedSet<Factor> filteredFactors, Set<Factor> selectedQueryFactors, ObjectInputStream<GeneProfile> inputStream) throws IOException;

    protected abstract T returnEmpty() throws GeneNotFoundException;
}
