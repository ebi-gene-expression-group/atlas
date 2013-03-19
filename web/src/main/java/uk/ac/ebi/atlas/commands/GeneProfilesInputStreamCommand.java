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
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.commands.context.RequestContext;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.geneindex.SolrClient;
import uk.ac.ebi.atlas.streams.GeneProfileInputStreamFilter;

import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

@Named
@Scope("request")
public class GeneProfilesInputStreamCommand<T, K extends ObjectInputStream> {
    protected static final Logger logger = Logger.getLogger(GeneProfilesInputStreamCommand.class);

    private RequestContext requestContext;

    private SolrClient solrClient;

    private CommandExecutor<T, K> commandExecutor;

    @Inject
    public void setSolrClient(SolrClient solrClient) {
        this.solrClient = solrClient;
    }

    public void setRequestContext(RequestContext requestContext) {
        this.requestContext = requestContext;
    }

    @NotNull
    public T apply(K geneProfileInputStream) throws GenesNotFoundException {

        checkNotNull(commandExecutor, "Command executor was not set!");

        Set<String> selectedGeneIds = null;

        if (StringUtils.isNotBlank(requestContext.getGeneQuery())) {

            selectedGeneIds = solrClient.findGeneIds(requestContext.getGeneQuery(), requestContext.getFilteredBySpecies());

        }

        try (ObjectInputStream<K> inputStream = new GeneProfileInputStreamFilter(geneProfileInputStream, selectedGeneIds, requestContext.getSelectedQueryFactors())) {

            return commandExecutor.execute(inputStream);

        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new IllegalStateException("IOException when invoking ObjectInputStream.close()");
        }
    }

    public void setCommandExecutor(CommandExecutor commandExecutor) {
        this.commandExecutor = commandExecutor;
    }
}
