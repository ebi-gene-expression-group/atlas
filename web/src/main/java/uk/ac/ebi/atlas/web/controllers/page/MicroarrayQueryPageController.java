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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.ac.ebi.atlas.commands.RankMicroarrayProfilesCommand;
import uk.ac.ebi.atlas.commands.context.MicroarrayRequestContextBuilder;
import uk.ac.ebi.atlas.model.differential.DifferentialProfilesList;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayExperiment;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayProfile;
import uk.ac.ebi.atlas.web.MicroarrayRequestPreferences;
import uk.ac.ebi.atlas.web.controllers.DownloadURLBuilder;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@Scope("request")
public class MicroarrayQueryPageController extends DifferentialQueryPageController<MicroarrayExperiment, MicroarrayRequestPreferences, DifferentialProfilesList, MicroarrayProfile> {

    @Inject
    public MicroarrayQueryPageController(MicroarrayRequestContextBuilder requestContextBuilder,
                                         RankMicroarrayProfilesCommand rankMicroarrayProfilesCommand,
                                         DownloadURLBuilder downloadURLBuilder) {
        super(requestContextBuilder, rankMicroarrayProfilesCommand, downloadURLBuilder);
    }

    @RequestMapping(value = "/experiments/{experimentAccession}", params = {"type=MICROARRAY"})
    public String showGeneProfiles(@ModelAttribute("preferences") @Valid MicroarrayRequestPreferences preferences
            , BindingResult result, Model model, HttpServletRequest request) {

        return super.showGeneProfiles(preferences, result, model, request);

    }

    @Override
    protected void initExtraRequestPreferences(MicroarrayRequestPreferences requestPreferences, MicroarrayExperiment experiment) {
        // preselect first array design accession
        requestPreferences.setArrayDesignAccession(experiment.getArrayDesignAccessions().first());
    }

}
