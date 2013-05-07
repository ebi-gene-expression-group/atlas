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

package uk.ac.ebi.atlas.commands;

import com.google.common.collect.Maps;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import uk.ac.ebi.atlas.commands.context.RequestContext;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.geneindex.GeneQueryTokenizer;
import uk.ac.ebi.atlas.geneindex.SolrClient;
import uk.ac.ebi.atlas.model.GeneProfile;
import uk.ac.ebi.atlas.streams.GeneProfileInputStreamFilter;

import javax.inject.Inject;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

public abstract class GeneProfilesQueryCommand<T, K extends GeneProfile> implements Command<T> {

    private static final Logger LOGGER = Logger.getLogger(GeneProfilesQueryCommand.class);

    private SolrClient solrClient;

    private RequestContext requestContext;

    protected GeneProfilesQueryCommand(RequestContext requestContext) {
        this.requestContext = requestContext;
    }

    @Inject
    public void setSolrClient(SolrClient solrClient) {
        this.solrClient = solrClient;
    }

    public T execute(String experimentAccession) throws GenesNotFoundException {
        Set<String> selectedGeneIds = getSelectedGeneIds();

        try (ObjectInputStream<K> inputStream = new GeneProfileInputStreamFilter(createInputStream(experimentAccession), selectedGeneIds, requestContext.getSelectedQueryFactors())) {

            return execute(inputStream, requestContext);

        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            throw new IllegalStateException("IOException when invoking ObjectInputStream.close()");
        }
    }

    Set<String> getSelectedGeneIds() throws GenesNotFoundException {
        Set<String> selectedGeneIds = null;

        if (StringUtils.isNotBlank(requestContext.getGeneQuery())) {

            selectedGeneIds = solrClient.findGeneIds(requestContext.getGeneQuery(), requestContext.isExactMatch(), requestContext.getFilteredBySpecies());

        }
        return selectedGeneIds;
    }

    Map<String, Set<String>> getSelectedGeneIdsPerQueryToken() throws GenesNotFoundException {
        Map<String, Set<String>> selectedGeneIdsPerQueryToken = null;

        if (StringUtils.isNotBlank(requestContext.getGeneQuery())) {

            selectedGeneIdsPerQueryToken = Maps.newHashMap();

            for (String queryToken : GeneQueryTokenizer.tokenize(requestContext.getGeneQuery())) {

                Set<String> selectedGeneIds = solrClient.findGeneIds(queryToken, requestContext.isExactMatch(), requestContext.getFilteredBySpecies());
                selectedGeneIdsPerQueryToken.put(queryToken, selectedGeneIds);

            }

        }

        return selectedGeneIdsPerQueryToken;
    }

    protected abstract ObjectInputStream<K> createInputStream(String experimentAccession);

    protected abstract T execute(ObjectInputStream<K> inputStream, RequestContext requestContext);
}
