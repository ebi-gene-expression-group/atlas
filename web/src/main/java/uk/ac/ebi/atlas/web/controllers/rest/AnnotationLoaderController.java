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

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import uk.ac.ebi.atlas.commands.GeneNamesImportCommand;
import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.model.caches.ExperimentsCache;
import uk.ac.ebi.atlas.web.ApplicationProperties;

import javax.inject.Inject;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@Scope("request")
public class AnnotationLoaderController {

    private ExperimentsCache experimentsCache;
    private ApplicationProperties applicationProperties;


    @Inject
    public AnnotationLoaderController(ApplicationProperties applicationProperties, ExperimentsCache experimentsCache) {
        this.experimentsCache = experimentsCache;
        this.applicationProperties = applicationProperties;
    }

    private GeneNamesImportCommand geneNamesImportCommand;

    @Inject
    public AnnotationLoaderController(GeneNamesImportCommand geneNamesImportCommand) {
        this.geneNamesImportCommand = geneNamesImportCommand;
    }

    @RequestMapping("/updateAnnotations")
    @ResponseBody
    public String updateAnnotations(@RequestParam("species") Set<String> species) {

        geneNamesImportCommand.loadGeneNames(species);

        return "Updated";
    }

    @RequestMapping("/updateAllAnnotations")
    @ResponseBody
    public String updateAnnotationsForAllLoadedExperiments() {

        return updateAnnotations(applicationProperties.getBiomartDatasetIdentifiers());

    }


}
















