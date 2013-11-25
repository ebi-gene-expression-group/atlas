package uk.ac.ebi.atlas.integration;

import com.google.common.base.Joiner;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.solr.query.BioentityPropertyValueTokenizer;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

@Named
@Scope("singleton")
public class EBIGlobalSearchQueryBuilder {

    private BioentityPropertyValueTokenizer bioentityPropertyValueTokenizer;

    @Inject
    public EBIGlobalSearchQueryBuilder(BioentityPropertyValueTokenizer bioentityPropertyValueTokenizer) {
        this.bioentityPropertyValueTokenizer = bioentityPropertyValueTokenizer;
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
                if (term.toUpperCase().equals("AND")) {
                    joinOn = " AND ";
                } else {
                    conditionTermsWithoutAnd.add(term);
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

    public String buildGlobalSearchTerm(String geneQuery, String condition) {
        return buildGlobalSearchTerm(bioentityPropertyValueTokenizer.split(geneQuery), bioentityPropertyValueTokenizer.split(condition));
    }

}
