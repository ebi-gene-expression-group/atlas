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

import com.google.common.base.Function;
import com.google.common.collect.Sets;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.geneindex.SolrClient;
import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.model.GeneProfile;
import uk.ac.ebi.atlas.model.caches.ExperimentsCache;
import uk.ac.ebi.atlas.streams.GeneProfileInputStreamBuilder;
import uk.ac.ebi.atlas.streams.GeneProfileInputStreamFilter;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.Set;

public abstract class GeneProfilesInputStreamCommand<T> implements Function<String, T> {
    protected static final Logger logger = Logger.getLogger(RankGeneProfilesCommand.class);

    private GeneProfileInputStreamBuilder geneProfileInputStreamBuilder;

    private ExperimentsCache experimentsCache;

    private FilterParameters filterParameters;

    private SolrClient solrClient;

    @Inject
    protected void setGeneProfileInputStreamBuilder(GeneProfileInputStreamBuilder geneProfileInputStreamBuilder) {
        this.geneProfileInputStreamBuilder = geneProfileInputStreamBuilder;
    }

    @Inject
    public void setExperimentsCache(ExperimentsCache experimentsCache) {
        this.experimentsCache = experimentsCache;
    }

    @Inject
    public void setSolrClient(SolrClient solrClient){
        this.solrClient = solrClient;
    }

    public void setFilteredParameters(FilterParameters filterParameters) {
        this.filterParameters = filterParameters;
    }

    public FilterParameters getFilterParameters() {
        return filterParameters;
    }

    @NotNull
    public T apply(String experimentAccession) {

        Experiment experiment = experimentsCache.getExperiment(experimentAccession);

        Set<String> uppercaseGeneIDsToBeSearched = null;

        if (StringUtils.isNotBlank(filterParameters.getGeneQuery())){
            uppercaseGeneIDsToBeSearched = Sets.newHashSet(solrClient.findGeneIds(filterParameters.getGeneQuery(), experiment.getSpecie(), true));
            if (uppercaseGeneIDsToBeSearched.isEmpty()) {
                return returnEmpty();
            }
        }

        ObjectInputStream<GeneProfile> geneProfileInputStream = geneProfileInputStreamBuilder.forExperiment(experimentAccession)
                .createGeneProfileInputStream();

        try (ObjectInputStream<GeneProfile> inputStream = new GeneProfileInputStreamFilter(geneProfileInputStream, uppercaseGeneIDsToBeSearched, filterParameters.getQueryFactorValues())) {

            return apply(experiment, inputStream);

        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new IllegalStateException("IOException when invoking ObjectInputStream.close()");
        }
    }

    protected abstract T apply(Experiment experiment
            , ObjectInputStream<GeneProfile> inputStream) throws IOException;

    protected abstract T returnEmpty();
}
