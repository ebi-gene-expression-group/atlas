package uk.ac.ebi.atlas.solr.query;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import uk.ac.ebi.atlas.web.SemanticQueryTerm;

import javax.annotation.Nullable;
import javax.inject.Inject;
import java.util.LinkedHashSet;
import java.util.List;

@Controller
@Scope("request")
public class SuggestionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SuggestionService.class);

    private static final int MAX_NUMBER_OF_SUGGESTIONS = 15;

    private GeneIdSuggestionService geneIdSuggestionService;
    private MultiTermSuggestionService multiTermSuggestionService;

    @Inject
    public SuggestionService(GeneIdSuggestionService geneIdSuggestionService, MultiTermSuggestionService multiTermSuggestionService) {
        this.geneIdSuggestionService = geneIdSuggestionService;
        this.multiTermSuggestionService = multiTermSuggestionService;
    }

    public List<SemanticQueryTerm> fetchTopSuggestions(String query, @Nullable String species) {
        LOGGER.info("fetchTopSuggestions for query {}, species {}", query, species);

        LinkedHashSet<SemanticQueryTerm> suggestions = Sets.newLinkedHashSet();

        suggestions.addAll(geneIdSuggestionService.fetchGeneIdSuggestionsInName(query, species));

        if (suggestions.size() < MAX_NUMBER_OF_SUGGESTIONS) {
            suggestions.addAll(geneIdSuggestionService.fetchGeneIdSuggestionsInSynonym(query, species));
        }

        if (suggestions.size() < MAX_NUMBER_OF_SUGGESTIONS) {
            suggestions.addAll(geneIdSuggestionService.fetchGeneIdSuggestionsInIdentifier(query, species));
        }

        if (suggestions.size() < MAX_NUMBER_OF_SUGGESTIONS) {
            List<SemanticQueryTerm> multiTermSuggestions = multiTermSuggestionService.fetchMultiTermSuggestions(query);
            suggestions.addAll(multiTermSuggestions);
        }

        List<SemanticQueryTerm> topSuggestions = Lists.newArrayList(suggestions);

        if (topSuggestions.size() > MAX_NUMBER_OF_SUGGESTIONS) {
            topSuggestions = topSuggestions.subList(0, MAX_NUMBER_OF_SUGGESTIONS);
        }

        return topSuggestions;
    }

}
