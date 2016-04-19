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

package uk.ac.ebi.atlas.web.controllers.rest.experimentdesign;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.ac.ebi.atlas.commons.readers.FileTsvReaderBuilder;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@Controller
@Scope("request")
public class BaselineDesignDownloadController extends ExperimentDesignDownloadController<BaselineExperiment> {

    @Inject
    public BaselineDesignDownloadController(FileTsvReaderBuilder fileTsvReaderBuilder) {
        super(fileTsvReaderBuilder);
    }

    @RequestMapping(value = "/experiments/{experimentAccession}/experiment-design.tsv", params = {"type=RNASEQ_MRNA_BASELINE"})
    public void downloadExperimentDesign(HttpServletRequest request, HttpServletResponse response) throws IOException {

        extractExperimentDesign(request, response);

    }

    @RequestMapping(value = "/experiments/{experimentAccession}/experiment-design.tsv", params =
            {"type=PROTEOMICS_BASELINE"})
    public void downloadExperimentDesignProteomics(HttpServletRequest request, HttpServletResponse response) throws
            IOException {

        extractExperimentDesign(request, response);

    }

    @Override
    protected Set<String> getAnalysedRowsAccessions(BaselineExperiment experiment) {
        return experiment.getExperimentRunAccessions();
    }
}
















