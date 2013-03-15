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
import uk.ac.ebi.atlas.commands.context.BaselineRequestContext;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.geneindex.SolrClient;
import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.model.baseline.BaselineProfile;
import uk.ac.ebi.atlas.streams.GeneProfileInputStreamFilter;
import uk.ac.ebi.atlas.streams.InputStreamFactory;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.Set;

public abstract class GeneProfilesInputStreamCommand<T> {
    protected static final Logger logger = Logger.getLogger(GeneProfilesInputStreamCommand.class);

    private InputStreamFactory inputStreamFactory;

    private BaselineRequestContext requestContext;

    private SolrClient solrClient;

    //ToDo: verify if the following @Inject can be injected in the constructor of the abstract class

    @Inject
    protected void setInputStreamFactory(InputStreamFactory inputStreamFactory) {
        this.inputStreamFactory = inputStreamFactory;
    }

    @Inject
    public void setSolrClient(SolrClient solrClient) {
        this.solrClient = solrClient;
    }

    @Inject
    public void setRequestContext(BaselineRequestContext requestContext) {
        this.requestContext = requestContext;
    }

    @NotNull
    public T apply(Experiment experiment) throws GenesNotFoundException {

        Set<String> selectedGeneIds = null;

        if(StringUtils.isNotBlank(requestContext.getGeneQuery())){

            selectedGeneIds = solrClient.findGeneIds(requestContext.getGeneQuery(), requestContext.getFilteredBySpecies());

        }

        ObjectInputStream<BaselineProfile> geneProfileInputStream = inputStreamFactory.createGeneProfileInputStream(experiment.getAccession());

        try (ObjectInputStream<BaselineProfile> inputStream = new GeneProfileInputStreamFilter(geneProfileInputStream, selectedGeneIds, requestContext.getSelectedQueryFactors())) {

            return apply(requestContext, inputStream);

        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new IllegalStateException("IOException when invoking ObjectInputStream.close()");
        }
    }

    protected abstract T apply(BaselineRequestContext requestContext, ObjectInputStream<BaselineProfile> inputStream) throws IOException;

}
