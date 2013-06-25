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

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import uk.ac.ebi.atlas.commands.GeneNamesImportCommand;
import uk.ac.ebi.atlas.geneannotation.arraydesign.ArrayDesignType;
import uk.ac.ebi.atlas.geneannotation.arraydesign.DesignElementMappingLoader;
import uk.ac.ebi.atlas.web.ApplicationProperties;

import javax.inject.Inject;
import java.util.Set;

@Controller
@Scope("request")
public class AnnotationLoaderController {

    private ApplicationProperties applicationProperties;

    private GeneNamesImportCommand geneNamesImportCommand;

    private DesignElementMappingLoader designElementLoader;

    @Inject
    public AnnotationLoaderController(ApplicationProperties applicationProperties, GeneNamesImportCommand geneNamesImportCommand, DesignElementMappingLoader designElementLoader) {
        this.applicationProperties = applicationProperties;
        this.geneNamesImportCommand = geneNamesImportCommand;

        this.designElementLoader = designElementLoader;
    }

    @RequestMapping("/updateAnnotations")
    @ResponseBody
    public String updateAnnotations(@RequestParam("species") Set<String> species) {

        geneNamesImportCommand.loadGeneNames(species);

        return "Updated";
    }

    @RequestMapping("/updateAllAnnotations")
    @ResponseBody
//ToDo: this will not work once we add MicroRna
    public String updateAnnotationsForAllLoadedExperiments() {

        return updateAnnotations(applicationProperties.getBiomartDatasetIdentifiers());

    }

    @RequestMapping("/updateDesignElements")
    @ResponseBody
    public String updateDesignElements(@RequestParam("arrayDesign") String arrayDesign,
                                       @RequestParam(value="type", required = false) String type) {
        //ToDo: maybe create Command similar to GeneNamesImportCommand
        ArrayDesignType arrayDesignType;
        if (StringUtils.isEmpty(type)) {
            arrayDesignType = ArrayDesignType.MICRO_ARRAY;
        } else {
            arrayDesignType = ArrayDesignType.getByName(type);
        }

        designElementLoader.loadMappings(arrayDesign, arrayDesignType);

        return "Updated";
    }

//    @RequestMapping("/updateAllArrayDesigns")
//    @ResponseBody
//    public String updateAllArrayDesigns() {
//        designElementLoader.loadMappings(applicationProperties.getArrayDesignAccessions());
//        return "Updated";
//    }
}
















