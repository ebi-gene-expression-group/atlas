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

import com.google.common.collect.Sets;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.commands.context.MicroarrayRequestContext;
import uk.ac.ebi.atlas.commands.context.RequestContext;
import uk.ac.ebi.atlas.commands.download.MicroarrayProfilesTSVWriter;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.model.differential.Contrast;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayProfile;
import uk.ac.ebi.atlas.streams.GeneProfileInputStreamFilter;
import uk.ac.ebi.atlas.streams.InputStreamFactory;
import uk.ac.ebi.atlas.streams.differential.microarray.MicroarrayProfilesInputStream;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

@Named
@Scope("prototype")
public class WriteMicroarrayProfilesCommand extends GeneProfilesQueryCommand<Long, MicroarrayProfile> implements Command<Long> {

    private static final Logger LOGGER = Logger.getLogger(WriteMicroarrayProfilesCommand.class);

    private MicroarrayProfilesTSVWriter geneProfileTsvWriter;
    private MicroarrayRequestContext requestContext;

    private InputStreamFactory inputStreamFactory;

    @Inject
    public WriteMicroarrayProfilesCommand(MicroarrayProfilesTSVWriter geneProfileTsvWriter,
                                          MicroarrayRequestContext requestContext,
                                          InputStreamFactory inputStreamFactory) {
        super(requestContext);
        this.geneProfileTsvWriter = geneProfileTsvWriter;
        this.requestContext = requestContext;
        this.inputStreamFactory = inputStreamFactory;
    }

    @Override
    public ObjectInputStream<MicroarrayProfile> createInputStream(String experimentAccession) {
        return inputStreamFactory.createMicroarrayProfileInputStream(experimentAccession, requestContext.getArrayDesignAccession());
    }

    @Override
    public Long execute(ObjectInputStream<MicroarrayProfile> inputStream, RequestContext requestContext) {
        try {
            GeneProfileInputStreamFilter geneProfileInputStreamFilter = (GeneProfileInputStreamFilter) inputStream;
            MicroarrayProfilesInputStream microarrayProfilesInputStream = (MicroarrayProfilesInputStream) geneProfileInputStreamFilter.getWrappedInputStream();
            Set<Contrast> conditions = Sets.newHashSet(microarrayProfilesInputStream.getOrderedContrasts());
            return geneProfileTsvWriter.write(inputStream, conditions);
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            throw new IllegalStateException("IOException when invoking ObjectInputStream.close()");
        }
    }

    @Override
    ObjectInputStream<MicroarrayProfile> filterInputStream(ObjectInputStream<MicroarrayProfile> inputStream) throws GenesNotFoundException {
        return geneProfilesFilter.filterInputStreamAnySpecies(inputStream, requestContext);
    }

    public void setResponseWriter(PrintWriter responseWriter) {
        geneProfileTsvWriter.setResponseWriter(responseWriter);
    }

}
