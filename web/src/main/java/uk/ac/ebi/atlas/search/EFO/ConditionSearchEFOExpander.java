package uk.ac.ebi.atlas.search.EFO;

import com.google.common.base.Joiner;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.experimentimport.EFOParentsLookupService;
import uk.ac.ebi.atlas.utils.StringUtil;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Set;

@Named
@Scope("singleton")
public class ConditionSearchEFOExpander {

    private static final Logger LOGGER = Logger.getLogger(ConditionSearchEFOExpander.class);

    private EFOIdToTermMapper efoIdToTermMapper;

    @Inject
    public ConditionSearchEFOExpander(EFOIdToTermMapper efoIdToTermMapper) {
        this.efoIdToTermMapper = efoIdToTermMapper;
    }

    public String fetchExpandedTermWithEFOChildren(String queryTerms) {
        if (StringUtils.isBlank(queryTerms)) {
            return queryTerms;
        }

        if (queryTerms.contains(" AND ") || queryTerms.contains(" and ")) {
            return queryTerms;
        }

        return termPlusEFOTerms(StringUtil.trimSurroundingQuotes(queryTerms));
    }

    private String termPlusEFOTerms(String term) {
        Set<String> efoTerms = efoIdToTermMapper.getIdsFromTerm(term);
        return term + (efoTerms.isEmpty() ? "" : " " + Joiner.on(" ").join(efoTerms));
    }



}
