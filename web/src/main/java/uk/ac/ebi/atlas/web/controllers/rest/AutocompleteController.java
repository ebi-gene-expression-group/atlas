package uk.ac.ebi.atlas.web.controllers.rest;


import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
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

    private SolrClient solrClient;

    @Inject
    public AutocompleteController(SolrClient solrClient){
        this.solrClient = solrClient;
    }

    @RequestMapping(value = "/json/suggestions", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String getTopSuggestions(@RequestParam(value = "query") String query){
        List<String> geneNameSuggestions = solrClient.findGeneNameSuggestions(query);
        LinkedHashSet<String> suggestions = Sets.newLinkedHashSet(geneNameSuggestions);

        if (suggestions.size()<10) {
            List<String> genePropertiesSuggestions = solrClient.findGenePropertySuggestions(query);
            suggestions.addAll(genePropertiesSuggestions);
        }

        int suggestionsCount = suggestions.size();

        List<String> topSuggestions = Lists.newArrayList(suggestions).subList(0, suggestionsCount > 10? 10 : suggestionsCount);
        Gson gson = new Gson();
        return gson.toJson(topSuggestions);
    }

}
