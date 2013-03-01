/*
 * Copyright 2008-2012 Microarray Informatics Team, EMBL-European Bioinformatics Institute
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.ac.ebi.atlas.commands.GeneNotFoundException;
import uk.ac.ebi.atlas.commands.RequestContext;
import uk.ac.ebi.atlas.commands.RequestContextBuilder;
import uk.ac.ebi.atlas.commands.WriteGeneProfilesCommand;
import uk.ac.ebi.atlas.model.caches.ExperimentsCache;
import uk.ac.ebi.atlas.web.FilterFactorsConverter;
import uk.ac.ebi.atlas.web.RequestPreferences;
import uk.ac.ebi.atlas.web.controllers.GeneProfilesController;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

@Controller
@Scope("request")
public class GeneProfilesDownloadController extends GeneProfilesController {
    private static final Logger LOGGER = Logger.getLogger(GeneProfilesDownloadController.class);

    private WriteGeneProfilesCommand writeGeneProfilesCommand;

    @Inject
    public GeneProfilesDownloadController(WriteGeneProfilesCommand writeGeneProfilesCommand,
                                          RequestContextBuilder requestContextBuilder,
                                          ExperimentsCache experimentsCache,FilterFactorsConverter filterFactorsConverter) {

        super(requestContextBuilder, experimentsCache, filterFactorsConverter);
        this.writeGeneProfilesCommand = writeGeneProfilesCommand;
    }

    @RequestMapping("/experiments/{experimentAccession}.tsv")
    public void downloadGeneProfiles(@PathVariable String experimentAccession
            , @ModelAttribute("preferences") @Valid RequestPreferences preferences
            , HttpServletResponse response) throws IOException, GeneNotFoundException {

        initPreferences(preferences, experimentAccession);

        LOGGER.info("<downloadGeneProfiles> received download request for requestPreferences: " + preferences);

        response.setHeader("Content-Disposition", "attachment; filename=\"" + experimentAccession + "-gene-expression-profiles.tsv\"");

        response.setContentType("text/plain; charset=utf-8");

        initRequestContext(experimentAccession, preferences);

        writeGeneProfilesCommand.setResponseWriter(response.getWriter());

        long genesCount = writeGeneProfilesCommand.apply(experimentAccession);

        LOGGER.info("<downloadGeneProfiles> streamed " + genesCount + "gene expression profiles");


    }

}