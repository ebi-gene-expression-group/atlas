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
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.ac.ebi.atlas.model.differential.Contrast;
import uk.ac.ebi.atlas.model.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.model.readers.ExperimentDesignTsvReader;
import uk.ac.ebi.atlas.web.controllers.ExperimentDispatcher;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Set;

@Controller
@Scope("request")
public class DifferentialDesignPageController extends ExperimentDesignPageController {

    @Inject
    public DifferentialDesignPageController(ExperimentDesignTsvReader experimentDesignTsvReader) {
        super(experimentDesignTsvReader);
    }

    @RequestMapping(value = "/experiments/{experimentAccession}/experiment-design", params = {"type=DIFFERENTIAL"})
    public String showGeneProfiles(Model model, HttpServletRequest request) throws IOException {
        DifferentialExperiment experiment = (DifferentialExperiment) request.getAttribute(ExperimentDispatcher.EXPERIMENT_ATTRIBUTE);
        extractExperimentDesign(model, experiment, extractLibrariesFromDifferentialExperiment(experiment));

        return "experiment-experiment-design";
    }

    private Set<String> extractLibrariesFromDifferentialExperiment(DifferentialExperiment experiment) {
        Set<String> libraries = Sets.newHashSet();
        for (Contrast contrast : experiment.getContrasts()) {
            for (String library : contrast.getReferenceAssayGroup()) {
                libraries.add(library);
            }
            for (String library : contrast.getTestAssayGroup()) {
                libraries.add(library);
            }
        }

        return libraries;
    }

}