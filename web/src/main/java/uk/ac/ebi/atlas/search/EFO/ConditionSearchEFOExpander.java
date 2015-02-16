package uk.ac.ebi.atlas.search.EFO;

import com.google.common.collect.ImmutableSet;
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

    public ConditionQuery addEfoAccessions(ConditionQuery query) {
        if (query.isEmpty()) {
            return query;
        }

        if (query.containsIgnoreCase("and")) {
            return query;
        }

        ImmutableSet<String> termAndIds = buildTermsAndEfoAccessions(query.terms());

        return ConditionQuery.create(termAndIds);
    }

    private ImmutableSet<String> buildTermsAndEfoAccessions(Iterable<String> terms) {
        ImmutableSet.Builder<String> builder = ImmutableSet.builder();
        for (String term : terms) {
            HashSet<String> efoTerms = efoIdToTermMapper.getIdsForTermSubstring(term);
            builder.add(term);
            builder.addAll(efoTerms);
        }

        return builder.build();
    }

}
