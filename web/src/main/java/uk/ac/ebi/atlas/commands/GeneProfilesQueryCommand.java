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

import org.apache.log4j.Logger;
import uk.ac.ebi.atlas.commands.context.RequestContext;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.model.Profile;

import javax.inject.Inject;
import java.io.IOException;

public abstract class GeneProfilesQueryCommand<T, K extends Profile> implements Command<T> {

    private static final Logger LOGGER = Logger.getLogger(GeneProfilesQueryCommand.class);

    protected GeneProfilesFilter geneProfilesFilter;

    private RequestContext requestContext;

    protected GeneProfilesQueryCommand(RequestContext requestContext) {
        this.requestContext = requestContext;
    }

    @Inject
    public void setGeneProfilesFilter(GeneProfilesFilter geneProfilesFilter) {
        this.geneProfilesFilter = geneProfilesFilter;
    }

    public T execute(String experimentAccession) throws GenesNotFoundException {

        try (ObjectInputStream<K> filteredInputStream = filterInputStream(createInputStream(experimentAccession))) {

            return execute(filteredInputStream, requestContext);

        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            throw new IllegalStateException("IOException when invoking ObjectInputStream.close()", e);
        }
    }

    ObjectInputStream<K> filterInputStream(ObjectInputStream<K> inputStream) throws GenesNotFoundException {
        return geneProfilesFilter.filterInputStream(inputStream, requestContext);
    }

    public abstract ObjectInputStream<K> createInputStream(String experimentAccession);

    public abstract T execute(ObjectInputStream<K> inputStream, RequestContext requestContext);
}
