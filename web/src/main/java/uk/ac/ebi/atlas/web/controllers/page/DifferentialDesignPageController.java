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

import com.google.common.collect.Sets;
import com.google.gson.Gson;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import uk.ac.ebi.atlas.model.differential.Contrast;
import uk.ac.ebi.atlas.model.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.web.DifferentialDesignRequestPreferences;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Set;

@Controller
@Scope("request")
public class DifferentialDesignPageController extends ExperimentDesignPageRequestHandler<DifferentialExperiment> {

    private String contrastId;


    @RequestMapping(method = RequestMethod.GET, value = "/experiments/{experimentAccession}/experiment-design", params = {"type=DIFFERENTIAL"})
    public String showRnaSeqExperimentDesign(@ModelAttribute("preferences") @Valid DifferentialDesignRequestPreferences preferences
            , Model model, HttpServletRequest request) throws IOException {

        contrastId = preferences.getSelectedContrast();
        return handleRequest(model, request);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/experiments/{experimentAccession}/experiment-design", params = {"type=MICROARRAY"})
    public String showMicroarrayExperimentDesign(@ModelAttribute("preferences") @Valid DifferentialDesignRequestPreferences preferences
            , Model model, HttpServletRequest request) throws IOException {

        contrastId = preferences.getSelectedContrast();
        return handleRequest(model, request);
    }

    @Override
    protected Set<String> getAnalysedRowsAccessions(DifferentialExperiment experiment) {
        return experiment.getAssayAccessions();
    }

    @Override
    protected void extendModel(Model model, DifferentialExperiment experiment) {

        model.addAttribute("contrasts", experiment.getContrasts());

        if (StringUtils.isBlank(contrastId)) {
            contrastId = experiment.getContrasts().first().getId();
        }

        Gson gson = new Gson();

        Contrast contrast = experiment.getContrast(contrastId);
        model.addAttribute("referenceAssays", gson.toJson(Sets.newHashSet(contrast.getReferenceAssayGroup())));
        model.addAttribute("testAssays", gson.toJson(Sets.newHashSet(contrast.getTestAssayGroup())));

    }

}