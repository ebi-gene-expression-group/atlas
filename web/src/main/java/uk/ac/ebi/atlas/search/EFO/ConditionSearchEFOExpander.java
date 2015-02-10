package uk.ac.ebi.atlas.search.EFO;

import com.google.common.base.Joiner;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.utils.StringUtil;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Set;

@Named
@Scope("singleton")
public class ConditionSearchEFOExpander {

    private EFOIdToTermMapper efoIdToTermMapper;

    @Inject
    public ConditionSearchEFOExpander(EFOIdToTermMapper efoIdToTermMapper) {
        this.efoIdToTermMapper = efoIdToTermMapper;
    }

    public String getIds(String queryTerms) {
        if (StringUtils.isBlank(queryTerms)) {
            return queryTerms;
        }

        if (queryTerms.contains(" AND ") || queryTerms.contains(" and ")) {
            return queryTerms;
        }

        return termPlusIds(StringUtil.trimSurroundingQuotes(queryTerms));
    }

    private String termPlusIds(String term) {
        Set<String> efoTerms = efoIdToTermMapper.getIdsForTermSubstring(term);
        return term + (efoTerms.isEmpty() ? "" : " " + Joiner.on(" ").join(efoTerms));
    }

}
