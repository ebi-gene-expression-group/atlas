package uk.ac.ebi.atlas.web.controllers.rest;


import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import uk.ac.ebi.atlas.geneindex.SolrClient;

import javax.inject.Inject;
import java.util.LinkedHashSet;
import java.util.List;

@Controller
@Scope("request")
public class AutocompleteController {

    private static final int MAX_NUMBER_OF_SUGGESTIONS = 10;

    private SolrClient solrClient;

    @Inject
    public AutocompleteController(SolrClient solrClient){
        this.solrClient = solrClient;
    }

    @RequestMapping(value = "/json/suggestions", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String getTopSuggestions(@RequestParam(value = "query") String query){
        LinkedHashSet<String> suggestions = Sets.newLinkedHashSet();

        if (!StringUtils.containsWhitespace(query)){
            suggestions.addAll(solrClient.findGeneNameSuggestions(query));
        }

        if (suggestions.size() < MAX_NUMBER_OF_SUGGESTIONS) {
            suggestions.addAll(solrClient.findGenePropertySuggestions(query));
        }

        List<String> topSuggestions = Lists.newArrayList(suggestions);

        if (topSuggestions.size() > MAX_NUMBER_OF_SUGGESTIONS){
            topSuggestions.subList(0, MAX_NUMBER_OF_SUGGESTIONS);
        }

        Gson gson = new Gson();
        return gson.toJson(topSuggestions);
    }

}
