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


import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import uk.ac.ebi.atlas.solr.query.SolrSuggestionsService;

import javax.inject.Inject;
import java.util.LinkedHashSet;
import java.util.List;

@Controller
@Scope("request")
public class AutocompleteController {

    private static final int MAX_NUMBER_OF_SUGGESTIONS = 15;

    private SolrSuggestionsService solrSuggestionsService;

    @Inject
    public AutocompleteController(SolrSuggestionsService solrSuggestionsService) {
        this.solrSuggestionsService = solrSuggestionsService;
    }

    @RequestMapping(value = "/json/suggestions", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String getTopSuggestions(@RequestParam(value = "query") String query, @RequestParam(value = "species", required = false) String species) {
        if (StringUtils.isBlank(query)) {
            return StringUtils.EMPTY;
        }

        LinkedHashSet<String> suggestions = Sets.newLinkedHashSet();

        species = StringUtils.lowerCase(species);

        if (!StringUtils.containsWhitespace(query)) {
            suggestions.addAll(solrSuggestionsService.fetchGeneIdSuggestionsInName(query, species));
        }

        if (suggestions.size() < MAX_NUMBER_OF_SUGGESTIONS) {
            suggestions.addAll(solrSuggestionsService.fetchGeneIdSuggestionsInSynonym(query, species));
        }

        if (suggestions.size() < MAX_NUMBER_OF_SUGGESTIONS) {
            suggestions.addAll(solrSuggestionsService.fetchGeneIdSuggestionsInIdentifier(query, species));
        }

        if (suggestions.size() < MAX_NUMBER_OF_SUGGESTIONS) {
            suggestions.addAll(solrSuggestionsService.fetchMultiTermSuggestions(query, species));
        }

        List<String> topSuggestions = Lists.newArrayList(suggestions);

        if (topSuggestions.size() > MAX_NUMBER_OF_SUGGESTIONS) {
            topSuggestions = topSuggestions.subList(0, MAX_NUMBER_OF_SUGGESTIONS);
        }

        Gson gson = new Gson();
        return gson.toJson(topSuggestions);
    }

}
