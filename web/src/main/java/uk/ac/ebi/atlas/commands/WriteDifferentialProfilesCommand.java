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
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.commands.context.RequestContext;
import uk.ac.ebi.atlas.commands.context.RnaSeqRequestContext;
import uk.ac.ebi.atlas.commands.download.RnaSeqProfilesTSVWriter;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.model.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.model.differential.rnaseq.RnaSeqProfile;
import uk.ac.ebi.atlas.streams.InputStreamFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.PrintWriter;

@Named
@Scope("prototype")
public class WriteDifferentialProfilesCommand extends GeneProfilesQueryCommand<Long, RnaSeqProfile> implements Command<Long> {

    private static final Logger LOGGER = Logger.getLogger(WriteDifferentialProfilesCommand.class);

    private RnaSeqProfilesTSVWriter geneProfileTsvWriter;

    private DifferentialExperiment experiment;
    private InputStreamFactory inputStreamFactory;

    @Inject
    public WriteDifferentialProfilesCommand(RnaSeqProfilesTSVWriter geneProfileTsvWriter,
                                            RnaSeqRequestContext requestContext,
                                            InputStreamFactory inputStreamFactory) {
        super(requestContext);
        this.geneProfileTsvWriter = geneProfileTsvWriter;
        this.inputStreamFactory = inputStreamFactory;
    }

    @Override
    public ObjectInputStream<RnaSeqProfile> createInputStream(String experimentAccession) {
        return inputStreamFactory.createDifferentialProfileInputStream(experimentAccession);
    }

    @Override
    public Long execute(ObjectInputStream<RnaSeqProfile> inputStream, RequestContext requestContext) {
        try {
            return geneProfileTsvWriter.write(inputStream, experiment.getContrasts());
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            throw new IllegalStateException("IOException when invoking ObjectInputStream.close()");
        }
    }


    public void setResponseWriter(PrintWriter responseWriter) {
        geneProfileTsvWriter.setResponseWriter(responseWriter);
    }

    public void setExperiment(DifferentialExperiment experiment) {
        this.experiment = experiment;
    }

}
