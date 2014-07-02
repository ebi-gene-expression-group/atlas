package uk.ac.ebi.atlas.search.EFO;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.solr.query.BioentityPropertyValueTokenizer;

import javax.inject.Inject;
import javax.inject.Named;

@Named
@Scope("singleton")
public class ConditionSearchEFOExpander {

    private static final Logger LOGGER = Logger.getLogger(ConditionSearchEFOExpander.class);

    private EFOChildrenClient efoChildrenClient;
    private BioentityPropertyValueTokenizer bioentityPropertyValueTokenizer;

    @Inject
    public ConditionSearchEFOExpander(EFOChildrenClient efoChildrenClient, BioentityPropertyValueTokenizer bioentityPropertyValueTokenizer) {
        this.efoChildrenClient = efoChildrenClient;
        this.bioentityPropertyValueTokenizer = bioentityPropertyValueTokenizer;
    }

    public String fetchExpandedTermWithEFOChildren(String term) {
        if (StringUtils.isBlank(term)) {
            return term;
        }

        if (term.contains(" AND ")) {
            //TODO: implement AND, and fix test  BioentitiesSearchControllerConditionQuery2ANDTermsDifferentialSIT and BioentitiesSearchControllerConditionQuery2ANDTermsBaselineSIT
            throw new NotImplementedException("EFO expansion of terms containing AND not yet implemented");
        }

        return termPlusEFOChildren(term);
    }

    private String termPlusEFOChildren(String term) {
        ImmutableList<String> efoChildren = efoChildrenClient.fetchEFOChildren(term);
        return term + (efoChildren.isEmpty() ? "" : " " + Joiner.on(" ").join(efoChildren));
    }

//
//    String buildQueryString(String queryString){
//        List<String> terms = bioentityPropertyValueTokenizer.split(queryString);
//
//        List<String> solrTerms = new ArrayList<>();
//
//        String joinOn = " OR ";
//
//        for (String term: terms) {
//            if (term.equalsIgnoreCase("AND")) {
//                joinOn = " AND ";
//            } else {
//                String escapedTerm = term.replace(":", "\\:");
//                solrTerms.add(CONDITIONS_SEARCH_FIELD + ":" + escapedTerm);
//            }
//        }
//
//        return Joiner.on(joinOn).join(solrTerms);
//    }
//
//

}
