package uk.ac.ebi.atlas.solr;

import com.google.common.collect.ImmutableSet;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.QueryResponse;

import java.util.List;

public final class SolrUtil {

    public static ImmutableSet<String> extractFirstFacetValues(QueryResponse solrResponse) {
        ImmutableSet.Builder<String> results = ImmutableSet.builder();
        List<FacetField.Count> firstFacetFieldCounts = solrResponse.getFacetFields().get(0).getValues();
        if (firstFacetFieldCounts != null) {
            for (FacetField.Count facetFieldCount : firstFacetFieldCounts) {
                results.add(facetFieldCount.getName());
            }
        }
        return results.build();
    }

}
