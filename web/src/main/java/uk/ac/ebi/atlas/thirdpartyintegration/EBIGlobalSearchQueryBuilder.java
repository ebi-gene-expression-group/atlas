package uk.ac.ebi.atlas.thirdpartyintegration;

import com.google.common.base.Joiner;
import uk.ac.ebi.atlas.search.SemanticQuery;
import uk.ac.ebi.atlas.solr.query.BioentityPropertyValueTokenizer;

import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

import static uk.ac.ebi.atlas.utils.StringUtil.quoteIfMoreThanOneWord;

@Named
public class EBIGlobalSearchQueryBuilder {

    public String buildGlobalSearchTerm(String geneQuery, SemanticQuery condition) {
//        return buildGlobalSearchTerm(BioentityPropertyValueTokenizer.splitBySpacePreservingQuotes(geneQuery), condition.terms());
        return "dummy";
    }

    private String buildGlobalSearchTerm(List<String> geneQueryTerms, List<String> condition) {
        StringBuilder stringBuilder = new StringBuilder();

        if (!geneQueryTerms.isEmpty()) {

            boolean moreThanOneQueryTermAndCondition = geneQueryTerms.size() > 1 && !condition.isEmpty();

            if (moreThanOneQueryTermAndCondition) {
                stringBuilder.append("(");
            }

            stringBuilder.append(Joiner.on(" OR ").join(geneQueryTerms));

            if (moreThanOneQueryTermAndCondition) {
                stringBuilder.append(")");
            }

            if (!condition.isEmpty()) {
                stringBuilder.append(" AND ");
            }
        }

        if (!condition.isEmpty()) {

            boolean moreThanOneConditionAndQueryTerm = condition.size() > 1 && !geneQueryTerms.isEmpty();

            List<String> conditionTermsWithoutAnd = new ArrayList<>();

            String joinOn = " OR ";

            for (String term: condition) {
                if (term.equalsIgnoreCase("AND")) {
                    joinOn = " AND ";
                } else {
                    conditionTermsWithoutAnd.add(quoteIfMoreThanOneWord(term));
                }
            }

            if (moreThanOneConditionAndQueryTerm) {
                stringBuilder.append("(");
            }

            stringBuilder.append(Joiner.on(joinOn).join(conditionTermsWithoutAnd));

            if (moreThanOneConditionAndQueryTerm) {
                stringBuilder.append(")");
            }
        }

        return stringBuilder.toString();
    }


}
