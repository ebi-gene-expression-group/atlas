package uk.ac.ebi.atlas.solr.cloud.search;

import org.apache.commons.lang3.StringUtils;
import uk.ac.ebi.atlas.solr.cloud.SchemaField;

import java.util.Arrays;
import java.util.Collection;

import static java.util.stream.Collectors.joining;
import static org.apache.solr.client.solrj.util.ClientUtils.escapeQueryChars;

public class SolrQueryUtils {
    protected SolrQueryUtils() {
        throw new UnsupportedOperationException();
    }

    // I don’t think using the Standard Query Parser
    // (https://lucene.apache.org/solr/guide/7_1/the-standard-query-parser.html#the-standard-query-parser) for fields
    // such as assay_group_id or experiment_accession incurs in a performance penalty since there’s no analysis that
    // can be done for those fields. My educated guess is that the term(s) query parser improves performance when it’s
    // used on an analyzed field because it avoids that processing step.
    private static final String STANDARD_QUERY_PARSER_FIELD_QUERY_TEMPLATE = "%s:(%s)";
    private static final String STANDARD_QUERY_PARSER_LOWER_BOUND_RANGE_QUERY_TEMPLATE = "%s:[%s TO *]";
    private static final String STANDARD_QUERY_PARSER_UPPER_BOUND_RANGE_QUERY_TEMPLATE = "%s:[* TO %s]";
    private static final String STANDARD_QUERY_PARSER_DOUBLE_BOUND_RANGE_QUERY_TEMPLATE = "%s:[%s TO %s]";

    private static String normalize(String str) {
        return "\"" + escapeQueryChars(str.trim()) + "\"";
    }

    public static String createOrBooleanQuery(SchemaField field, String... values) {
        return Arrays.stream(values).anyMatch(StringUtils::isNotBlank) ?
                String.format(
                        STANDARD_QUERY_PARSER_FIELD_QUERY_TEMPLATE,
                        field.name(),
                        Arrays.stream(values)
                                .filter(StringUtils::isNotBlank)
                                .map(SolrQueryUtils::normalize)
                                .distinct()
                                .collect(joining(" OR "))) :
                "";
    }

    public static String createOrBooleanQuery(SchemaField field, Collection<String> values) {
        return createOrBooleanQuery(field, values.toArray(new String[0]));
    }

    public static String createLowerBoundRangeQuery(SchemaField field, double min) {
        return String.format(
                STANDARD_QUERY_PARSER_LOWER_BOUND_RANGE_QUERY_TEMPLATE,
                field.name(),
                Double.toString(min));
    }

    public static String createUpperBoundRangeQuery(SchemaField field, double max) {
        return String.format(
                STANDARD_QUERY_PARSER_UPPER_BOUND_RANGE_QUERY_TEMPLATE,
                field.name(),
                Double.toString(max));
    }

    public static String createDoubleBoundRangeQuery(SchemaField field, double min, double max) {
        return String.format(
                STANDARD_QUERY_PARSER_DOUBLE_BOUND_RANGE_QUERY_TEMPLATE,
                field.name(),
                Double.toString(min),
                Double.toString(max));
    }
}
