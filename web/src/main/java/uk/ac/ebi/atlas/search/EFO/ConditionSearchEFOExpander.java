package uk.ac.ebi.atlas.search.EFO;

import com.google.common.base.Joiner;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.search.ConditionQuery;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.HashSet;

@Named
@Scope("singleton")
public class ConditionSearchEFOExpander {

    private EFOIdToTermMapper efoIdToTermMapper;

    @Inject
    public ConditionSearchEFOExpander(EFOIdToTermMapper efoIdToTermMapper) {
        this.efoIdToTermMapper = efoIdToTermMapper;
    }

    public ConditionQuery getIds(ConditionQuery query) {
        if (query.isEmpty()) {
            return query;
        }

        if (query.containsIgnoreCase("and")) {
            return query;
        }

        //TODO: add support for multiple terms
        String allTerms = Joiner.on(" ").join(query);

        HashSet<String> termAndIds = termPlusIds(allTerms);

        return ConditionQuery.create(termAndIds);
    }

    private HashSet<String> termPlusIds(String term) {
        HashSet<String> efoTerms = efoIdToTermMapper.getIdsForTermSubstring(term);
        efoTerms.add(term);
        return efoTerms;
    }

}
