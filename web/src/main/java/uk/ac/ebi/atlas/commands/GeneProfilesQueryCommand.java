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

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import uk.ac.ebi.atlas.commands.context.RequestContext;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.geneindex.GeneQueryTokenizer;
import uk.ac.ebi.atlas.geneindex.SolrClient;
import uk.ac.ebi.atlas.model.Profile;
import uk.ac.ebi.atlas.streams.GeneProfileInputStreamFilter;

import javax.inject.Inject;
import java.io.IOException;
import java.util.Set;

public abstract class GeneProfilesQueryCommand<T, K extends Profile> implements Command<T> {

    private static final Logger LOGGER = Logger.getLogger(GeneProfilesQueryCommand.class);

    private SolrClient solrClient;

    private GeneQueryTokenizer geneQueryTokenizer;

    protected RequestContext requestContext;

    protected GeneProfilesQueryCommand(RequestContext requestContext) {
        this.requestContext = requestContext;
    }

    @Inject
    public void setSolrClient(SolrClient solrClient, GeneQueryTokenizer geneQueryTokenizer) {
        this.solrClient = solrClient;
        this.geneQueryTokenizer = geneQueryTokenizer;
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

    //ToDo: remove this method, returning null or passing nulls around is a bad smell
    Set<String> getSelectedGeneIds() throws GenesNotFoundException {
        Set<String> selectedGeneIds = null;

        if (StringUtils.isNotBlank(requestContext.getGeneQuery())) {

            selectedGeneIds = solrClient.findGeneIds(requestContext.getGeneQuery(), requestContext.isExactMatch(), requestContext.getFilteredBySpecies());

        }
        return selectedGeneIds;
    }

    protected abstract ObjectInputStream<K> createInputStream(String experimentAccession);

    protected abstract T execute(ObjectInputStream<K> inputStream, RequestContext requestContext);
}
