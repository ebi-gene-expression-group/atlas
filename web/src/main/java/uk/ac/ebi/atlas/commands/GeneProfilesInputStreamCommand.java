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
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.geneindex.IndexClient;
import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.model.GeneProfile;
import uk.ac.ebi.atlas.model.caches.ExperimentsCache;
import uk.ac.ebi.atlas.streams.GeneProfileInputStreamFilter;
import uk.ac.ebi.atlas.streams.GeneProfilesInputStream;
import uk.ac.ebi.atlas.web.RequestPreferences;

import javax.inject.Inject;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public abstract class GeneProfilesInputStreamCommand<T> implements Function<String, T> {
    private static final Logger logger = Logger.getLogger(RankGeneProfilesCommand.class);

    private RequestPreferences requestPreferences;

    private GeneProfilesInputStream.Builder geneProfileInputStreamBuilder;

    private IndexClient indexClient;

    private ExperimentsCache experimentsCache;

    @Inject
    protected void setGeneProfileInputStreamBuilder(GeneProfilesInputStream.Builder geneProfileInputStreamBuilder) {
        this.geneProfileInputStreamBuilder = geneProfileInputStreamBuilder;
    }

    @Inject
    public void setIndexClient(IndexClient indexClient) {
        this.indexClient = indexClient;
    }

    @Inject
    public void setExperimentsCache(ExperimentsCache experimentsCache) {
        this.experimentsCache = experimentsCache;
    }

    public T apply(String experimentAccession) {

        Set<String> geneIDs = new HashSet<>();

        String geneProperties = requestPreferences.getGeneQuery();

        Experiment experiment = experimentsCache.getExperiment(experimentAccession);

        if (!StringUtils.isEmpty(geneProperties)) {

            geneIDs.addAll(indexClient.findGeneIds(geneProperties, experiment.getSpecie()));

            if (geneIDs.isEmpty()) {
                return returnEmpty();
            }
        }

        try (ObjectInputStream<GeneProfile> inputStream = buildGeneProfilesInputStream(experimentAccession, geneIDs)) {

            return apply(requestPreferences, experiment, inputStream);

        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new IllegalStateException("IOException when invoking ObjectInputStream.close()");
        }
    }

    protected ObjectInputStream<GeneProfile> buildGeneProfilesInputStream(String experimentAccession, Set<String> geneIDs) {

        ObjectInputStream<GeneProfile> geneProfileInputStream = geneProfileInputStreamBuilder.forExperiment(experimentAccession)
                .withCutoff(requestPreferences.getCutoff()).create();


        return new GeneProfileInputStreamFilter(geneProfileInputStream, geneIDs, requestPreferences.getOrganismParts()
                                                ,requestPreferences.isIncludeGenesExpressedInNonSelectedFactorValues());

    }

    protected ObjectInputStream<GeneProfile> buildGeneProfilesInputStream(String experimentAccession) {
        return buildGeneProfilesInputStream(experimentAccession, null);

    }

    public void setRequestPreferences(RequestPreferences requestPreferences) {
        this.requestPreferences = requestPreferences;
    }

    protected abstract T apply(RequestPreferences requestPreferences, Experiment experiment
            , ObjectInputStream<GeneProfile> inputStream) throws IOException;

    protected abstract T returnEmpty();
}
