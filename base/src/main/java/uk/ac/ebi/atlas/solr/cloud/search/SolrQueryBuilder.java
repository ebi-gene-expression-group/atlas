package uk.ac.ebi.atlas.solr.cloud.search;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.SortClause;
import uk.ac.ebi.atlas.solr.cloud.CollectionProxy;
import uk.ac.ebi.atlas.solr.cloud.SchemaField;

import java.util.Collection;

import static java.util.stream.Collectors.joining;
import static uk.ac.ebi.atlas.solr.cloud.search.SolrQueryUtils.createDoubleBoundRangeQuery;
import static uk.ac.ebi.atlas.solr.cloud.search.SolrQueryUtils.createLowerBoundRangeQuery;
import static uk.ac.ebi.atlas.solr.cloud.search.SolrQueryUtils.createOrBooleanQuery;
import static uk.ac.ebi.atlas.solr.cloud.search.SolrQueryUtils.createUpperBoundRangeQuery;
import static uk.ac.ebi.atlas.utils.GsonProvider.GSON;

public class SolrQueryBuilder<T extends CollectionProxy> {
    // Some magic Solr number, from the logs:
    // ERROR (qtp511707818-76) [   ] o.a.s.s.HttpSolrCall null:java.lang.IllegalArgumentException:
    // maxSize must be <= 2147483630; got: 2147483646
    public static final int SOLR_MAX_ROWS = 2147483630;
    public static final int DEFAULT_ROWS = 1000000;

    private ImmutableSet.Builder<String> fqClausesBuilder = ImmutableSet.builder();
    private ImmutableSet.Builder<String> qClausesBuilder = ImmutableSet.builder();
    private ImmutableSet.Builder<String> flBuilder = ImmutableSet.builder();
    private ImmutableList.Builder<SortClause> sortBuilder = ImmutableList.builder();

    // For now, the builder will only support a single facet with an unlimited number of subfacets
    private String facetField;
    private ImmutableSet.Builder<String> subFacetBuilder = ImmutableSet.builder();

    private int rows = MAX_ROWS;

    public <U extends SchemaField<T>> SolrQueryBuilder<T> addFilterFieldByTerm(U field, String... values) {
        fqClausesBuilder.add(createOrBooleanQuery(field, values));
        return this;
    }

    public <U extends SchemaField<T>> SolrQueryBuilder<T> addFilterFieldByRangeMin(U field, double min) {
        fqClausesBuilder.add(createLowerBoundRangeQuery(field, min));
        return this;
    }

    public <U extends SchemaField<T>> SolrQueryBuilder<T> addFilterFieldByRangeMax(U field, double max) {
        fqClausesBuilder.add(createUpperBoundRangeQuery(field, max));
        return this;
    }

    public <U extends SchemaField<T>> SolrQueryBuilder<T> addFilterFieldByRangeMinMax(U field,
                                                                                      double min,
                                                                                      double max) {
        fqClausesBuilder.add(createDoubleBoundRangeQuery(field, min, max));
        return this;
    }

    public <U extends SchemaField<T>> SolrQueryBuilder<T> addQueryFieldByTerm(U field, String... values) {
        qClausesBuilder.add(createOrBooleanQuery(field, values));
        return this;
    }

    public <U extends SchemaField<T>> SolrQueryBuilder<T> addQueryFieldByTerm(U field, Collection<String> values) {
        return addQueryFieldByTerm(field, values.toArray(new String[0]));
    }

    @SafeVarargs
    public final <U extends SchemaField<T>> SolrQueryBuilder<T> setFieldList(U... fields) {
        for (SchemaField field : fields) {
            flBuilder.add(field.name());
        }
        return this;
    }

    public final <U extends SchemaField<T>> SolrQueryBuilder<T> setFacetField(U field) {
        facetField = field.name();
        return this;
    }

    @SafeVarargs
    public final <U extends SchemaField<T>> SolrQueryBuilder<T> setSubFacetList(U... fields) {
        for (SchemaField field : fields) {
            subFacetBuilder.add(field.name());
        }
        return this;
    }

    public <U extends SchemaField<T>> SolrQueryBuilder<T> sortBy(U field, SolrQuery.ORDER order) {
        sortBuilder.add(new SortClause(field.name(), order));
        return this;
    }

    public SolrQueryBuilder<T> setRows(int rows) {
        this.rows = rows;
        return this;
    }

    public SolrQuery build() {
        ImmutableSet<String> fqClauses = fqClausesBuilder.build();
        ImmutableSet<String> qClauses = qClausesBuilder.build();
        ImmutableSet<String> fl = flBuilder.build();
        ImmutableSet<String> subFacets = subFacetBuilder.build();
        ImmutableList<SortClause> sorts = sortBuilder.build();

        return new SolrQuery()
                .addFilterQuery(fqClauses.toArray(new String[0]))
                .setQuery(
                        qClauses.isEmpty() ?
                                "*:*" :
                                qClauses.stream().filter(StringUtils::isNotBlank).collect(joining(" AND ")))
                .setFields(
                        fl.isEmpty() ?
                                "*" :
                                fl.stream().filter(StringUtils::isNotBlank).collect(joining(",")))
                .setParam("json.facet", GSON.toJson(buildJsonFacetObject(facetField, subFacets)))
                .setSorts(sorts)
                .setRows(rows);
    }

    private JsonObject buildJsonFacetObject(String facetField, ImmutableSet<String> subFacets) {
        JsonObject result = new JsonObject();

        if (facetField != null && !facetField.isEmpty()) {
            JsonObject facetObject = makeFacetObject(facetField, false);

            if (!subFacets.isEmpty()) {
                JsonObject subFacetWrapper = new JsonObject();

                subFacets.forEach(subFacetField ->
                        subFacetWrapper.add(subFacetField, makeFacetObject(subFacetField, true))
                );
                facetObject.add("facet", subFacetWrapper);
            }

            result.add(facetField, facetObject);
        }

        return result;

    }

    private JsonObject makeFacetObject(String fieldName, boolean hasLimit) {
        JsonObject facetObject = new JsonObject();

        facetObject.addProperty("type", "terms");
        facetObject.addProperty("field", fieldName);

        if (hasLimit) {
            facetObject.addProperty("limit", -1);
        }

        return facetObject;
    }
}
