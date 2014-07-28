package uk.ac.ebi.atlas.search.EFO;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.solr.query.BioentityPropertyValueTokenizer;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

@Named
@Scope("singleton")
public class ConditionSearchEFOExpander {

    private static final Logger LOGGER = Logger.getLogger(ConditionSearchEFOExpander.class);

    private EFOChildrenClient efoChildrenClient;

    @Inject
    public ConditionSearchEFOExpander(EFOChildrenClient efoChildrenClient) {
        this.efoChildrenClient = efoChildrenClient;
    }

    public String fetchExpandedTermWithEFOChildren(String queryTerms) {
        if (StringUtils.isBlank(queryTerms)) {
            return queryTerms;
        }

        if (queryTerms.contains(" AND ") || queryTerms.contains(" and ")) {
            return queryTerms;
        }

        return termPlusEFOChildren(queryTerms);
    }

    private String termPlusEFOChildren(String term) {
        ImmutableList<String> efoChildren = efoChildrenClient.fetchEFOChildren(term);

        // don't return more than 1024 terms because maxBooleanClauses in solr is 1024
        // TODO: reimplement this
        Iterable<String> topEfoChildren = Iterables.limit(efoChildren, 1023);
        return term + (efoChildren.isEmpty() ? "" : " " + Joiner.on(" ").join(topEfoChildren));
    }

}
