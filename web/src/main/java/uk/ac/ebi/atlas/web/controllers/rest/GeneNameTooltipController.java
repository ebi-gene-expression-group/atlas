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


import com.google.common.collect.Multimap;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import uk.ac.ebi.atlas.geneindex.SolrClient;

import javax.inject.Inject;
import java.util.Collection;

@Controller
@Scope("request")
public class GeneNameTooltipController {

    private static final int MAX_NUMBER_OF_SUGGESTIONS = 15;

    private SolrClient solrClient;

    @Inject
    public GeneNameTooltipController(SolrClient solrClient) {
        this.solrClient = solrClient;
    }

    @RequestMapping(value = "/rest/genenametooltip", method = RequestMethod.GET, produces = "text/html")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String getTopSuggestions(@RequestParam(value = "identifier") String identifier) {

        Multimap<String, String> multimap = solrClient.fetchTooltipProperties(identifier);

        StringBuilder sb = new StringBuilder();
        sb.append(formatGeneSymbol(multimap.get("symbol")));
        sb.append(" ");
        sb.append(formatSynonymsAndIdentifier(multimap.get("synonym"), identifier));

        return sb.toString();

    }

    private String formatGeneSymbol(Collection<String> symbols) {
        String symbol = symbols.iterator().next();
        return "<div style=\"font-size: 18\">" + symbol + "</div>";
    }

    private String formatSynonymsAndIdentifier(Collection<String> synonyms, String identifier) {
        StringBuilder sb = new StringBuilder("<div style=\"font-size:14\">(");
        for (String synonym : synonyms) {
            sb.append(synonym);
            sb.append(", ");
        }
        sb.append(identifier);
        sb.append(")</div>");
        return sb.toString();
    }

}
