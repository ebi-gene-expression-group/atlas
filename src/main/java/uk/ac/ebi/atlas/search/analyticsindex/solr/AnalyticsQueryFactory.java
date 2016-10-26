package uk.ac.ebi.atlas.search.analyticsindex.solr;

import com.google.common.collect.ImmutableList;
import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.Resource;
import uk.ac.ebi.atlas.search.SemanticQuery;

import javax.inject.Inject;
import javax.inject.Named;
import java.text.MessageFormat;
import java.util.List;

import static uk.ac.ebi.atlas.search.analyticsindex.solr.AnalyticsQueryFactory.Field.*;
import static uk.ac.ebi.atlas.search.analyticsindex.solr.AnalyticsSolrQueryTree.Operator.AND;
import static uk.ac.ebi.atlas.utils.ResourceUtils.readPlainTextResource;

@Named
@Scope("prototype")
public class AnalyticsQueryFactory {

    private final Resource baselineFacetsQueryJSON;
    private final Resource differentialFacetsQueryJSON;
    private final Resource experimentTypesQueryJson;
    private final Resource bioentityIdentifiersQueryJson;

    @Inject
    public AnalyticsQueryFactory( @Value("classpath:baseline.heatmap.pivot.query.json") Resource  baselineFacetsQueryJSON,
                                  @Value("classpath:differential.facets.query.json") Resource differentialFacetsQueryJSON,
                                  @Value("classpath:experimentType.query.json") Resource experimentTypesQueryJson,
                                  @Value("classpath:bioentityIdentifier.query.json") Resource bioentityIdentifiersQueryJson){
        this.baselineFacetsQueryJSON = baselineFacetsQueryJSON;
        this.differentialFacetsQueryJSON = differentialFacetsQueryJSON;
        this.experimentTypesQueryJson = experimentTypesQueryJson;
        this.bioentityIdentifiersQueryJson = bioentityIdentifiersQueryJson;
    }

    public Builder builder(){
        return new Builder();
    }


    public class Builder {

        private Builder(){
            /*
             we put some more data than matches the 0.05 pValue limit to give ourselves some wiggle
             room but we don't actually want them
             */
            solrQuery.addFilterQuery("-pValue:[0.05 TO *]");
            solrQuery.set("omitHeader", true);
        }

        /*
        Interesting lack of symmetry - baselineResults are retrieved with a different code path! :)
         */

        private void setFacets(Resource r){
            solrQuery.setRows(0);
            solrQuery.set("json.facet", readPlainTextResource(r).replaceAll("\\s+",""));
        }

        public Builder baselineFacets(){
            setFacets(baselineFacetsQueryJSON);
            solrQuery.addFilterQuery("experimentType:(rnaseq_mrna_baseline OR proteomics_baseline)");
            return this;
        }

        private Builder differential(){
            solrQuery.addFilterQuery("experimentType:(rnaseq_mrna_differential OR " +
                    "microarray_1colour_mrna_differential OR microarray_2colour_mrna_differential OR microarray_1colour_microrna_differential)");
            return this;
        }

        public Builder differentialResults(){
            solrQuery.setRows(1000);
            solrQuery.set("sort", "abs(foldChange)desc");
            return differential();
        }

        public Builder differentialFacets(){
            setFacets(differentialFacetsQueryJSON);
            return differential();
        }

        public Builder experimentTypeFacets(){
            setFacets(experimentTypesQueryJson);
            return this;
        }

        public Builder bioentityIdentifierFacets(int facetLimit){
            solrQuery.setRows(0);
            solrQuery.set("json.facet", readPlainTextResource(bioentityIdentifiersQueryJson).replace("\"limit\": -1",
                    MessageFormat.format("\"limit\": {0}", new Integer(facetLimit).toString())).replaceAll("\\s+",""));
            return this;
        }

        // Values below cutoff are excluded in index build, keeping comments just for reference
        // public static final double DEFAULT_BASELINE_CUT_OFF = 0.5;
        // public static final double DEFAULT_PROTEOMICS_CUT_OFF = 0;

        private static final String BASELINE_ABOVE_CUTOFF = "(experimentType:(rnaseq_mrna_baseline OR proteomics_baseline))";
        private static final String DIFFERENTIAL_ABOVE_CUTOFF =
                "(experimentType:(rnaseq_mrna_differential OR microarray_1colour_mrna_differential OR microarray_2colour_mrna_differential OR microarray_1colour_microrna_differential) " +
                        "AND pValue:[* TO 0.05])";
        private static final String DEFAULT_QUERY = "*:*";

        private ImmutableList.Builder<AnalyticsSolrQueryTree> queryClausesBuilder = ImmutableList.builder();

        private SolrQuery solrQuery = new SolrQuery();

        private void addQueryClause(Field searchField, SemanticQuery searchValue) {
            if (searchValue.isNotEmpty()) {
                queryClausesBuilder.add(new AnalyticsSolrQueryTree(searchField.toString(), searchValue));
            }
        }

        private void addQueryClause(Field searchField, String searchValue) {
            if (!searchValue.isEmpty()) {
                queryClausesBuilder.add(new AnalyticsSolrQueryTree(searchField.toString(), searchValue));
            }
        }

        public Builder queryIdentifierOrConditionsSearch(SemanticQuery query) {
            queryClausesBuilder.add(new AnalyticsSolrQueryTree(
                    AnalyticsSolrQueryTree.Operator.OR,
                    new AnalyticsSolrQueryTree(IDENTIFIER_SEARCH.toString(), query),
                    new AnalyticsSolrQueryTree(CONDITIONS_SEARCH.toString(), query)
            ));
            return this;
        }

        public Builder queryIdentifierSearch(SemanticQuery geneQuery) {
            addQueryClause(IDENTIFIER_SEARCH, geneQuery);
            return this;
        }

        public Builder queryConditionsSearch(SemanticQuery conditionQuery) {
            addQueryClause(CONDITIONS_SEARCH, conditionQuery);
            return this;
        }

        public Builder withFactorType(String factorType) {
            addQueryClause(FACTOR_TYPE, factorType);
            return this;
        }

        public Builder ofSpecies(String species) {
            addQueryClause(SPECIES, species);
            return this;
        }


        public Builder setRows(int rows) {
            solrQuery.setRows(rows);
            return this;
        }

        public SolrQuery build() {
            List<AnalyticsSolrQueryTree> queryClauses = queryClausesBuilder.build();

            if (queryClauses.isEmpty()) {
                solrQuery.setQuery(DEFAULT_QUERY);
            } else {
                solrQuery.setQuery(new AnalyticsSolrQueryTree(AND, queryClauses.toArray(new AnalyticsSolrQueryTree[0])).toString());
            }

            return solrQuery;
        }

    }
    enum Field {
        EXPERIMENT_TYPE("experimentType"),
        BIOENTITY_IDENTIFIER("bioentityIdentifier"),
        SPECIES("species"),
        IDENTIFIER_SEARCH("identifierSearch"),
        CONDITIONS_SEARCH("conditionsSearch"),
        FACTOR_TYPE("defaultQueryFactorType");
        private final String name;

        Field(String name){
            this.name = name;
        }

        @Override
        public String toString() {
            return this.name;
        }
    }
}
