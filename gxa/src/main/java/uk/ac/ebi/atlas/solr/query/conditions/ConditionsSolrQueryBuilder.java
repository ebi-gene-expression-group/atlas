
package uk.ac.ebi.atlas.solr.query.conditions;

import uk.ac.ebi.atlas.solr.query.BioentityPropertyValueTokenizer;
import com.google.common.base.Joiner;
import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.context.annotation.Scope;

import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

@Named
@Scope("singleton")
public class ConditionsSolrQueryBuilder {

    private static final String CONDITIONS_SEARCH_FIELD = "conditions_search";

    public SolrQuery build(String queryString) {
        SolrQuery solrQuery = new SolrQuery(buildQueryString(queryString));
        solrQuery.setRows(1000);
        return solrQuery;
    }

    String buildQueryString(String queryString){
        List<String> terms = BioentityPropertyValueTokenizer.splitBySpacePreservingQuotes(queryString);

        List<String> solrTerms = new ArrayList<>();

        String joinOn = " OR ";

        for (String term: terms) {
            if (term.equalsIgnoreCase("AND")) {
                joinOn = " AND ";
            } else {
                String escapedTerm = term.replace(":", "\\:");
                solrTerms.add(CONDITIONS_SEARCH_FIELD + ":" + escapedTerm);
            }
        }

        return Joiner.on(joinOn).join(solrTerms);
    }
}
