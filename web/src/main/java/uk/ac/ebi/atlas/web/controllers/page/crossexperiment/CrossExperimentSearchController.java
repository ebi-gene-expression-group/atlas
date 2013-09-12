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

package uk.ac.ebi.atlas.web.controllers.page.crossexperiment;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uk.ac.ebi.atlas.solr.BioentityProperty;
import uk.ac.ebi.atlas.solr.BioentityType;
import uk.ac.ebi.atlas.solr.query.SolrQueryService;
import uk.ac.ebi.atlas.web.controllers.ResourceNotFoundException;

import javax.inject.Inject;

@Controller
@Scope("request")
public class CrossExperimentSearchController {

    private SolrQueryService solrQueryService;

    @Inject
    public CrossExperimentSearchController(SolrQueryService solrQueryService){

        this.solrQueryService = solrQueryService;
    }

    @RequestMapping(value = "/experiments/all")
    public String search(@RequestParam(value = "queryString") String queryString){

        String bioentityType;
        String bioentityIdentifier;

        try{
            BioentityProperty bioentity = solrQueryService.findBioentityProperty(queryString);

            bioentityType = extractBioentityType(bioentity).toLowerCase();
            bioentityIdentifier = bioentity.getBioentityIdentifier();
        } catch(ResourceNotFoundException e){
            bioentityType = "geneset";
            bioentityIdentifier = queryString;
        }

        return "redirect:/" + bioentityType + "s/" + bioentityIdentifier;

    }

    // bioentity.getBioentityType() may return ens<type> or mirna
    // so here we use BioentityType to normalize and sanitize solr bioentity_type value
    private String extractBioentityType(BioentityProperty bioentity) {
        return BioentityType.get(bioentity.getBioentityType()).name();
    }

}
