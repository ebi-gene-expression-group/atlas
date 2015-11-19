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
 *
 */

package uk.ac.ebi.atlas.web.controllers.rest;


import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import uk.ac.ebi.atlas.solr.query.SuggestionService;
import uk.ac.ebi.atlas.solr.query.TermSourceSuggestion;

import javax.inject.Inject;
import java.util.List;

@Controller
@Scope("request")
public class AutoCompleteController {

    private final SuggestionService suggestionService;

    @Inject
    public AutoCompleteController(SuggestionService suggestionService) {
        this.suggestionService = suggestionService;
    }

    @RequestMapping(value = "/json/suggestions", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String fetchTopSuggestions(@RequestParam(value = "query") String query, @RequestParam(value = "species", required = false) String species) {
        if (StringUtils.isBlank(query)) {
            return StringUtils.EMPTY;
        }

        List<TermSourceSuggestion> suggestions = suggestionService.fetchTopSuggestions(query, species);

        return new Gson().toJson(suggestions);
    }

}
