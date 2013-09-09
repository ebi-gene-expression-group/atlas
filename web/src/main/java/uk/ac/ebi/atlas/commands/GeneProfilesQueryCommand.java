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
import uk.ac.ebi.atlas.model.Profile;
import uk.ac.ebi.atlas.solr.query.GeneQueryResponse;
import uk.ac.ebi.atlas.solr.query.SolrQueryService;
import uk.ac.ebi.atlas.streams.GeneProfileInputStreamFilter;

import javax.inject.Inject;
import java.io.IOException;

public abstract class GeneProfilesQueryCommand<T, K extends Profile> implements Command<T> {

    private static final Logger LOGGER = Logger.getLogger(GeneProfilesQueryCommand.class);

    private SolrQueryService solrQueryService;

    private RequestContext requestContext;

    protected GeneProfilesQueryCommand(RequestContext requestContext) {
        this.requestContext = requestContext;
    }

    @Inject
    public void setSolrQueryService(SolrQueryService solrQueryService) {
        this.solrQueryService = solrQueryService;
    }

    public T execute(String experimentAccession) throws GenesNotFoundException {

        try (ObjectInputStream<K> filteredInputStream = buildFilterInputStream(experimentAccession)) {

            return execute(filteredInputStream, requestContext);

        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            throw new IllegalStateException("IOException when invoking ObjectInputStream.close()");
        }
    }

    //ToDo: this should become an injectable builder
    ObjectInputStream<K> buildFilterInputStream(String experimentAccession) throws GenesNotFoundException {
        ObjectInputStream<K> inputStream = createInputStream(experimentAccession);

        if (StringUtils.isBlank(requestContext.getGeneQuery())) {
            return new GeneProfileInputStreamFilter(inputStream, requestContext.getSelectedQueryFactors());
        }

        GeneQueryResponse geneQueryResponse = solrQueryService.findGeneIdsOrSets(requestContext.getGeneQuery(),
                requestContext.isExactMatch(),
                requestContext.getFilteredBySpecies(),
                requestContext.isGeneSetMatch());

        if (geneQueryResponse.isEmpty()) {
            throw new GenesNotFoundException("No genes found for searchText = " + requestContext.getGeneQuery() + ", species = " + requestContext.getFilteredBySpecies());
        }

        //ToDo: this initialization is unrelated to this method, but I haven't find yet a better place for it
        requestContext.setGeneQueryResponse(geneQueryResponse);

        return new GeneProfileInputStreamFilter(inputStream, geneQueryResponse.getAllGeneIds(), requestContext.getSelectedQueryFactors());
    }

    public abstract ObjectInputStream<K> createInputStream(String experimentAccession);

    public abstract T execute(ObjectInputStream<K> inputStream, RequestContext requestContext);
}
