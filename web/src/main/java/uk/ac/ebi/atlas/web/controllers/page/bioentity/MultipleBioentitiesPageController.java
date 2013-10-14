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

package uk.ac.ebi.atlas.web.controllers.page.bioentity;

import com.google.common.base.Joiner;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uk.ac.ebi.atlas.solr.query.SolrQueryService;

import javax.inject.Inject;
import java.util.Set;

@Controller
@Scope("request")
public class MultipleBioentitiesPageController  {

    private SolrQueryService solrQueryService;

    @Inject
    public MultipleBioentitiesPageController(SolrQueryService solrQueryService){
        this.solrQueryService = solrQueryService;
    }

    @RequestMapping(value = "/query", params = {"geneQuery"})
    public String showMultipleBioentitiesPage(@RequestParam(value="geneQuery", required = true) String geneQuery, Model model) {

        //add ensambl identifiers to model in case the searched ID is a mirbase ID
        Set<String> ensemblIDs = solrQueryService.fetchGeneIdentifiersFromSolr(geneQuery, "ensgene", true, "mirbase_id");
        if (ensemblIDs.size() > 0) {
            model.addAttribute("ensemblIdentifiersForMiRNA", "+" + Joiner.on("+").join(ensemblIDs));
        }


        return "home";
    }

}