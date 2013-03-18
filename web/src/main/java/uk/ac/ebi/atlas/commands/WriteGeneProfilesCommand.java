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

import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.commands.context.BaselineRequestContext;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.model.baseline.BaselineProfile;
import uk.ac.ebi.atlas.model.baseline.Factor;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.PrintWriter;

@Named("streamGeneProfiles")
@Scope("prototype")
public class WriteGeneProfilesCommand extends GeneProfilesInputStreamCommand<Long> {

    private GeneProfilesTSVWriter geneProfileWriter;

    @Inject
    public WriteGeneProfilesCommand(GeneProfilesTSVWriter geneProfileWriter) {
        this.geneProfileWriter = geneProfileWriter;
    }

    @Override
    protected Long apply(BaselineRequestContext requestContext, ObjectInputStream<BaselineProfile> inputStream) throws IOException {

        return geneProfileWriter.apply(inputStream, Factor.getValues(requestContext.getAllQueryFactors()), requestContext.getAllQueryFactors());
    }


    public void setResponseWriter(PrintWriter responseWriter) {
        geneProfileWriter.setResponseWriter(responseWriter);
    }

}