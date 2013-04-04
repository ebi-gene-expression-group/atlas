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

package uk.ac.ebi.atlas.web.controllers.page;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Set;

@Controller
@Scope("request")
public class BaselineDesignPageController extends ExperimentDesignPageRequestHandler<BaselineExperiment> {

    @RequestMapping(value = "/experiments/{experimentAccession}/experiment-design", params = {"type=BASELINE"})
    public String showExperimentDesign(Model model, HttpServletRequest request) throws IOException {

        return handleRequest(model, request);

    }

    @Override
    protected void extendModel(Model model, BaselineExperiment experiment) {
        //No need to add extra attributes to the model, parent template method does enough
    }

    @Override
    protected Set<String> getAnalysedRowsAccessions(BaselineExperiment experiment) {
        return experiment.getExperimentRunAccessions();
    }
}