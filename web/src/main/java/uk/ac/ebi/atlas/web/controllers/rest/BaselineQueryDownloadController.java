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

package uk.ac.ebi.atlas.web.controllers.rest;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.ac.ebi.atlas.commands.GeneNotFoundException;
import uk.ac.ebi.atlas.commands.RequestContextBuilder;
import uk.ac.ebi.atlas.commands.WriteGeneProfilesCommand;
import uk.ac.ebi.atlas.model.ExperimentType;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.web.FilterFactorsConverter;
import uk.ac.ebi.atlas.web.RequestPreferences;
import uk.ac.ebi.atlas.web.controllers.BaselineQueryController;
import uk.ac.ebi.atlas.web.controllers.ExperimentDispatcher;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

@Controller
@Scope("request")
public class BaselineQueryDownloadController extends BaselineQueryController {
    private static final Logger LOGGER = Logger.getLogger(BaselineQueryDownloadController.class);

    private WriteGeneProfilesCommand writeGeneProfilesCommand;

    @Inject
    public BaselineQueryDownloadController(WriteGeneProfilesCommand writeGeneProfilesCommand,
                                           RequestContextBuilder requestContextBuilder,
                                           FilterFactorsConverter filterFactorsConverter) {

        super(requestContextBuilder, filterFactorsConverter);
        this.writeGeneProfilesCommand = writeGeneProfilesCommand;
    }

    @RequestMapping(value = "/experiments/{experimentAccession}.tsv", params = "type=BASELINE")
    public void downloadGeneProfiles(HttpServletRequest request
            , @ModelAttribute("preferences") @Valid RequestPreferences preferences
            , HttpServletResponse response) throws IOException, GeneNotFoundException {

        BaselineExperiment baselineExperiment = (BaselineExperiment)request.getAttribute(ExperimentDispatcher.EXPERIMENT_ATTRIBUTE);

        initPreferences(preferences, baselineExperiment);

        LOGGER.info("<downloadGeneProfiles> received download request for requestPreferences: " + preferences);

        response.setHeader("Content-Disposition", "attachment; filename=\"" + baselineExperiment.getAccession() + "-gene-expression-profiles.tsv\"");

        response.setContentType("text/plain; charset=utf-8");

        initRequestContext(baselineExperiment.getAccession(), preferences);

        writeGeneProfilesCommand.setResponseWriter(response.getWriter());

        long genesCount = writeGeneProfilesCommand.apply(baselineExperiment);

        LOGGER.info("<downloadGeneProfiles> streamed " + genesCount + "gene expression profiles");


    }

}