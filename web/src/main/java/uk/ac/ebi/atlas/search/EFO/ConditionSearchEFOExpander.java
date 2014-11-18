package uk.ac.ebi.atlas.search.EFO;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.dao.EFOTreeDAO;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Set;

@Named
@Scope("singleton")
public class ConditionSearchEFOExpander {

    private static final Logger LOGGER = Logger.getLogger(ConditionSearchEFOExpander.class);

    private EFOTreeDAO efoTreeDAO;

    @Inject
    public ConditionSearchEFOExpander(EFOTreeDAO efoTreeDAO) {
        this.efoTreeDAO = efoTreeDAO;
    }

    public String fetchExpandedTermWithEFOChildren(String queryTerms) {
        if (StringUtils.isBlank(queryTerms)) {
            return queryTerms;
        }

        if (queryTerms.contains(" AND ") || queryTerms.contains(" and ")) {
            return queryTerms;
        }

        return termPlusEFOTerms(queryTerms);
    }

    private String termPlusEFOTerms(String term) {
        String efoTerm = efoTreeDAO.getIdFromTerm(term);
        return term + (efoTerm.isEmpty() ? "" : " " + efoTerm);
    }

}
