package uk.ac.ebi.atlas.solr.cloud.search;

import com.google.common.collect.ImmutableSet;
import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import uk.ac.ebi.atlas.solr.cloud.CollectionProxy;
import uk.ac.ebi.atlas.solr.cloud.SchemaField;

import java.util.Collection;

import static java.util.stream.Collectors.joining;
import static uk.ac.ebi.atlas.solr.cloud.search.SolrQueryUtils.createDoubleBoundRangeQuery;
import static uk.ac.ebi.atlas.solr.cloud.search.SolrQueryUtils.createLowerBoundRangeQuery;
import static uk.ac.ebi.atlas.solr.cloud.search.SolrQueryUtils.createOrBooleanQuery;
import static uk.ac.ebi.atlas.solr.cloud.search.SolrQueryUtils.createUpperBoundRangeQuery;

public class SolrQueryBuilder<T extends CollectionProxy> {
    // Some magic Solr number, from the logs:
    // ERROR (qtp511707818-76) [   ] o.a.s.s.HttpSolrCall null:java.lang.IllegalArgumentException: maxSize must be <= 2147483630; got: 2147483646
    public static final int SOLR_MAX_ROWS = 2147483630;
    public static final int MAX_ROWS = 1000000;

    private ImmutableSet.Builder<String> fqClausesBuilder = ImmutableSet.builder();
    private ImmutableSet.Builder<String> qClausesBuilder = ImmutableSet.builder();
    private ImmutableSet.Builder<String> flBuilder = ImmutableSet.builder();
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

    public SolrQueryBuilder<T> setRows(int rows) {
        this.rows = rows;
        return this;
    }

    public SolrQuery build() {
        ImmutableSet<String> fqClauses = fqClausesBuilder.build();
        ImmutableSet<String> qClauses = qClausesBuilder.build();
        ImmutableSet<String> fl = flBuilder.build();

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
                .setRows(rows);
    }
}
