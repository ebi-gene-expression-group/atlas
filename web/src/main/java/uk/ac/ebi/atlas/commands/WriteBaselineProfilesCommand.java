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
import uk.ac.ebi.atlas.commands.context.BaselineRequestContext;
import uk.ac.ebi.atlas.commands.context.RequestContext;
import uk.ac.ebi.atlas.commands.download.BaselineProfilesTSVWriter;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.model.baseline.BaselineProfile;
import uk.ac.ebi.atlas.model.baseline.BaselineProfileComparator;
import uk.ac.ebi.atlas.model.baseline.BaselineProfilesList;
import uk.ac.ebi.atlas.streams.baseline.GeneSetProfilesBuilder;
import uk.ac.ebi.atlas.streams.InputStreamFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.PrintWriter;

@Named
@Scope("request")
public class WriteBaselineProfilesCommand extends GeneProfilesQueryCommand<Long, BaselineProfile> implements Command<Long> {

    private static final Logger LOGGER = Logger.getLogger(WriteBaselineProfilesCommand.class);

    private BaselineProfilesTSVWriter baselineProfilesTSVWriter;
    private GeneSetProfilesBuilder geneSetProfilesBuilder;
    private InputStreamFactory inputStreamFactory;

    @Inject
    public void setInputStreamFactory(InputStreamFactory inputStreamFactory) {
        this.inputStreamFactory = inputStreamFactory;
    }

    @Inject
    public WriteBaselineProfilesCommand(BaselineProfilesTSVWriter baselineProfilesTSVWriter
            , BaselineRequestContext requestContext
            , GeneSetProfilesBuilder geneSetProfilesBuilder) {
        super(requestContext);
        this.baselineProfilesTSVWriter = baselineProfilesTSVWriter;
        this.geneSetProfilesBuilder = geneSetProfilesBuilder;
    }

    @Override
    public ObjectInputStream<BaselineProfile> createInputStream(String experimentAccession) {
        return inputStreamFactory.createBaselineProfileInputStream(experimentAccession);
    }

    @Override
    public Long execute(ObjectInputStream<BaselineProfile> inputStream, RequestContext requestContext) {
        try {
            if (requestContext.isGeneSetMatch()) {
                BaselineProfileComparator baselineProfileComparator =
                        new BaselineProfileComparator(requestContext.isSpecific()
                                , requestContext.getSelectedQueryFactors()
                                , requestContext.getAllQueryFactors()
                                , requestContext.getCutoff());

                BaselineProfilesList geneSetProfiles =
                        geneSetProfilesBuilder.forGeneQueryResponse(requestContext.getGeneQueryResponse())
                                .withInputStream(inputStream)
                                .withBaselineComparator(baselineProfileComparator)
                                .build();

                return baselineProfilesTSVWriter.write(geneSetProfiles, requestContext.getAllQueryFactors());
            }

            return baselineProfilesTSVWriter.write(inputStream, requestContext.getAllQueryFactors());
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            throw new IllegalStateException("IOException when invoking ObjectInputStream.close()");
        }
    }

    public void setResponseWriter(PrintWriter responseWriter) {
        baselineProfilesTSVWriter.setResponseWriter(responseWriter);
    }
}
