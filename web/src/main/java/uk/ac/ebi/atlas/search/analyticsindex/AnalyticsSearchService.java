package uk.ac.ebi.atlas.search.analyticsindex;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableSet;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.search.analyticsindex.solr.AnalyticsClient;
import uk.ac.ebi.atlas.search.analyticsindex.solr.AnalyticsQueryBuilder;
import uk.ac.ebi.atlas.solr.SolrUtil;
import uk.ac.ebi.atlas.web.SemanticQuery;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Collection;
import java.util.List;

/**
 * Created by Alfonso Muñoz-Pomer Fuentes <amunoz@ebi.ac.uk> on 22/02/2016.
 */
@Named
@Scope("singleton")
public class AnalyticsSearchService {

    private AnalyticsClient analyticsClient;

    @Inject
    public AnalyticsSearchService(AnalyticsClient analyticsClient) {
        this.analyticsClient = analyticsClient;
    }

    public Optional<ImmutableSet<String>> searchBioentityIdentifiers(SemanticQuery geneQuery, String species) {
        QueryResponse queryResponse = analyticsClient.query(new AnalyticsQueryBuilder().queryIdentifierSearch(geneQuery).ofSpecies(species).setRows(0).facetByBioentityIdentifier().build());
        return Optional.of(SolrUtil.extractFirstFacetValues(queryResponse));
    }

    public Collection<String> getBioentityIdentifiersForSpecies(String species){
        List<FacetField> facetFields =  analyticsClient.query(
                new AnalyticsQueryBuilder()
                        .ofSpecies(species)
                        .facetByBioentityIdentifier()
                        .setRows(0)
                        .setFacetLimit(45000)
                        .build()).getFacetFields();

        return facetFields.size() != 1
                ? ImmutableSet.<String>of()
                : Collections2.transform(facetFields.get(0).getValues(), new Function<FacetField.Count, String>() {
            @Override
            public String apply(FacetField.Count count) {
                return count.getName();
            }
        });

    }

}
