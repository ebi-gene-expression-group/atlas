package uk.ac.ebi.atlas.solr.cloud.search;

import com.google.common.collect.ImmutableSet;
import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.util.ClientUtils;
import uk.ac.ebi.atlas.solr.cloud.CollectionProxy;
import uk.ac.ebi.atlas.solr.cloud.SchemaField;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;

import static java.util.stream.Collectors.joining;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.apache.solr.client.solrj.util.ClientUtils.escapeQueryChars;

public class SolrQueryBuilder<T extends CollectionProxy> {
    // I don’t think using the Standard Query Parser
    // (https://lucene.apache.org/solr/guide/7_1/the-standard-query-parser.html#the-standard-query-parser) for fields
    // such as assay_group_id or experiment_accession doesn’t incur in a performance penalty since there’s no analysis
    // that can be done for those fields. My educated guess is that the term(s) query parser improves performance when
    // it’s used on a tokenised/filtered field because it avoids that processing step.
    private static final String STANDARD_QUERY_PARSER_FIELD_QUERY_TEMPLATE = "%s:(%s)";
    private static final String STANDARD_QUERY_PARSER_LOWER_BOUND_RANGE_QUERY_TEMPLATE = "%s:[%f TO *]";
    private static final String STANDARD_QUERY_PARSER_UPPER_BOUND_RANGE_QUERY_TEMPLATE = "%s:[* TO %f]";
    private static final String STANDARD_QUERY_PARSER_DOUBLE_BOUND_RANGE_QUERY_TEMPLATE = "%s:[%f TO %f]";

    private ImmutableSet.Builder<String> fqClausesBuilder = ImmutableSet.builder();
    private ImmutableSet.Builder<String> qClausesBuilder = ImmutableSet.builder();
    private ImmutableSet.Builder<String> flBuilder = ImmutableSet.builder();
    // Some magic Solr number, from the logs:
    // ERROR (qtp511707818-76) [   ] o.a.s.s.HttpSolrCall null:java.lang.IllegalArgumentException: maxSize must be <= 2147483630; got: 2147483646
    private int rows = 1000;

    private static String normalize(String str) {
        return "\"" + escapeQueryChars(str.trim()) + "\"";
    }

    public static String createOrBooleanQuery(SchemaField field, String... values) {
        return values.length > 0 ?
                String.format(
                        STANDARD_QUERY_PARSER_FIELD_QUERY_TEMPLATE,
                        field.name(),
                        Arrays.stream(values)
                                .filter(StringUtils::isNotBlank)
                                .map(SolrQueryBuilder::normalize)
                                .collect(joining(" OR "))) :
                "";
    }

    public static String createOrBooleanQuery(SchemaField field, Collection<String> values) {
        return createOrBooleanQuery(field, values.toArray(new String[0]));
    }

    public static String createFieldQuery(SchemaField field, String value) {
        return isNotBlank(value) ?
                String.format(STANDARD_QUERY_PARSER_FIELD_QUERY_TEMPLATE, field.name(), normalize(value)) :
                "";
    }

    public static String createLowBoundRangeQuery(SchemaField field, double value) {
        return String.format(STANDARD_QUERY_PARSER_LOWER_BOUND_RANGE_QUERY_TEMPLATE, field.name(), value);
    }

    public static String createUpperBoundRangeQuery(SchemaField field, double value) {
        return String.format(STANDARD_QUERY_PARSER_UPPER_BOUND_RANGE_QUERY_TEMPLATE, field.name(), value);
    }

    public static String createDoubleBoundRangeQuery(SchemaField field, double lower, double upper) {
        return String.format(STANDARD_QUERY_PARSER_DOUBLE_BOUND_RANGE_QUERY_TEMPLATE, field.name(), lower, upper);
    }

    public <U extends SchemaField<T>> SolrQueryBuilder<T> filterField(U field, String value) {
        fqClausesBuilder.add(createFieldQuery(field, value));
        return this;
    }

    public <U extends SchemaField<T>> SolrQueryBuilder<T> queryField(U field, String value) {
        qClausesBuilder.add(createFieldQuery(field, value));
        return this;
    }

    public <U extends SchemaField<T>> SolrQueryBuilder<T> queryFieldOr(U field, String... values) {
        qClausesBuilder.add(createOrBooleanQuery(field, values));
        return this;
    }

    public <U extends SchemaField<T>> SolrQueryBuilder<T> queryFieldOr(U field, Collection<String> values) {
        return queryFieldOr(field, values.toArray(new String[0]));
    }

    public <U extends SchemaField<T>> SolrQueryBuilder<T> filterFieldOr(U field, String... values) {
        fqClausesBuilder.add(createOrBooleanQuery(field, values));
        return this;
    }

    public <U extends SchemaField<T>> SolrQueryBuilder<T> filterFieldLowerRange(U field, double value) {
        fqClausesBuilder.add(createLowBoundRangeQuery(field, value));
        return this;
    }

    public <U extends SchemaField<T>> SolrQueryBuilder<T> filterFieldUpperRange(U field, double value) {
        fqClausesBuilder.add(createUpperBoundRangeQuery(field, value));
        return this;
    }

    public <U extends SchemaField<T>> SolrQueryBuilder<T> filterFieldDoubleRange(U field, double lower, double upper) {
        fqClausesBuilder.add(createDoubleBoundRangeQuery(field, lower, upper));
        return this;
    }

    public <U extends SchemaField<T>> SolrQueryBuilder<T> setFieldList(U... fields) {
        for (SchemaField<T> field : fields) {
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

        return mapParams(
                "fq", fqClauses.stream().filter(StringUtils::isNotBlank).collect(joining(" AND ")),
                "q", qClauses.isEmpty() ? "*:*" : qClauses.stream().filter(StringUtils::isNotBlank).collect(joining(" AND ")),
                "fl", fl.isEmpty() ? "*" : fl.stream().filter(StringUtils::isNotBlank).collect(joining(",")),
                "rows", Integer.toString(rows));
                // fl can also be left empty to return all fields, but "*" shows an explicit intent
    }

    private static SolrQuery mapParams(String... vals) {
        SolrQuery solrQuery = new SolrQuery();
        for (int idx = 0 ; idx < vals.length ; idx += 2) {
            solrQuery.add(vals[idx], vals[idx + 1]);
        }

        return solrQuery;
    }
}
