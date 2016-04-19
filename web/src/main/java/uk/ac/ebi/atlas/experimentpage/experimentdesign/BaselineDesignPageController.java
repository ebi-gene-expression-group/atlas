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

package uk.ac.ebi.atlas.experimentpage.experimentdesign;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.ac.ebi.atlas.experimentpage.baseline.BaselineRequestPreferencesValidator;
import uk.ac.ebi.atlas.experimentpage.fastqc.FastQCReportUtil;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.baseline.Factor;
import uk.ac.ebi.atlas.web.controllers.ResourceNotFoundException;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Set;

@Controller
@Scope("request")
public class BaselineDesignPageController extends ExperimentDesignPageRequestHandler<BaselineExperiment> {

    private static final String SPECIES = "species";

    private FastQCReportUtil fastQCReportUtil;

    @Inject
    public void setFastQCReportUtil(FastQCReportUtil fastQCReportUtil) {
        this.fastQCReportUtil = fastQCReportUtil;
    }

    @RequestMapping(value = "/experiments/{experimentAccession}/experiment-design", params = {"type=RNASEQ_MRNA_BASELINE"})
    public String showRnaSeqExperimentDesign(Model model, HttpServletRequest request) throws IOException {
        return handleRequest(model, request);
    }

    @RequestMapping(value = "/experiments/{experimentAccession}/experiment-design", params = {"type=PROTEOMICS_BASELINE"})
    public String showProteomicsExperimentDesign(Model model, HttpServletRequest request) throws IOException {
        return handleRequest(model, request);
    }

    @Override
    protected void extendModel(Model model, BaselineExperiment experiment, String experimentAccession) {
        //This is necessary for adding functionality to the QC button
        Set<Factor> organisms = experiment.getExperimentalFactors().getDefaultFilterFactors();
        String specie = experiment.getFirstOrganism();

        if(!organisms.isEmpty()) {
            for (Factor factor : organisms) {
                if (factor.getType().equals("ORGANISM")) {
                    specie = factor.getValue();
                }
            }
        }

        try {
            if (fastQCReportUtil.hasFastQC(experimentAccession, specie)) {
                fastQCReportUtil.buildFastQCIndexHtmlPath(experimentAccession, specie);
                model.addAttribute(SPECIES, specie);
            }
        } catch (IOException e) {
            throw new ResourceNotFoundException("Species could not be found");
        }
    }

    @Override
    protected Set<String> getAnalysedRowsAccessions(BaselineExperiment experiment) {
        return experiment.getExperimentRunAccessions();
    }
}