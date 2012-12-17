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

import javax.inject.Inject;
import java.util.List;

@Controller
@Scope("request")
public class AnnotationLoaderController {


    private GeneNamesImportCommand geneNamesImportCommand;

    @Inject
    public AnnotationLoaderController(GeneNamesImportCommand geneNamesImportCommand) {
        this.geneNamesImportCommand = geneNamesImportCommand;
    }

    @RequestMapping("/updateAnnotations")
    @ResponseBody
    public String showGeneExpressions(@RequestParam("organism") List<String> organisms) {

        geneNamesImportCommand.loadGeneNames(organisms);

        return "Updated";
    }


}
















