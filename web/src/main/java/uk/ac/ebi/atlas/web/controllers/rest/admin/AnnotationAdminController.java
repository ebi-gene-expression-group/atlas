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

package uk.ac.ebi.atlas.web.controllers.rest.admin;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Set;

@Controller
@Scope("request")
@RequestMapping("/admin")
public class AnnotationAdminController {

//    private GeneNamesImportCommand geneNamesImportCommand;

//    private DesignElementMappingLoader designElementLoader;

//    @Inject
//    public AnnotationAdminController(GeneNamesImportCommand geneNamesImportCommand) {
//        this.geneNamesImportCommand = geneNamesImportCommand;
//
//    }

    @RequestMapping("/load/gene-names")
    @ResponseBody
    public String loadGeneNames(@RequestParam("species") Set<String> species) {

//        geneNamesImportCommand.loadGeneNames(species);
//
//        return "Updated";
        return "depricated";
    }

    @RequestMapping("/load/mirna")
    @ResponseBody
    public String loadMiRnaAnnotations() {

//        int count = geneNamesImportCommand.loadMiRnaNames();
//
//        return "Updated " + count + " miRNA names";

        return "depricated";
    }

    @RequestMapping("/load/design-elements")
    @ResponseBody
    public String loadDesignElements(@RequestParam("arrayDesign") String arrayDesign,
                                       @RequestParam(value = "type", required = false) String type) {
//        //ToDo: maybe create Command similar to GeneNamesImportCommand
//        ArrayDesignType arrayDesignType;
//        if (StringUtils.isEmpty(type)) {
//            arrayDesignType = ArrayDesignType.MICRO_ARRAY;
//        } else {
//            arrayDesignType = ArrayDesignType.getByName(type);
//        }
//
//        designElementLoader.loadMappings(arrayDesign, arrayDesignType);
//
//        return "Updated";
        return "depricated";
    }

}
















