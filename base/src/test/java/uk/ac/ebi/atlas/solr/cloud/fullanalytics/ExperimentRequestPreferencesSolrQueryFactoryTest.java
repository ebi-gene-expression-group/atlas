package uk.ac.ebi.atlas.solr.cloud.fullanalytics;

import org.apache.solr.client.solrj.SolrQuery;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import uk.ac.ebi.atlas.model.Expression;
import uk.ac.ebi.atlas.model.ExpressionUnit;
import uk.ac.ebi.atlas.search.SemanticQuery;
import uk.ac.ebi.atlas.search.SemanticQueryTerm;
import uk.ac.ebi.atlas.solr.cloud.search.SolrQueryBuilder;
import uk.ac.ebi.atlas.web.BaselineRequestPreferences;
import uk.ac.ebi.atlas.web.ExperimentPageRequestPreferences;
import uk.ac.ebi.atlas.web.RnaSeqBaselineRequestPreferences;
import uk.ac.ebi.atlas.web.RnaSeqBaselineRequestPreferencesTest;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toSet;
import static org.apache.solr.client.solrj.util.ClientUtils.escapeQueryChars;
import static org.assertj.core.api.Assertions.assertThat;

public class ExperimentRequestPreferencesSolrQueryFactoryTest {

    private static final String E_MTAB_513 = "E-MTAB-513";

    @Test
    void testDefaultQuery() {
        RnaSeqBaselineRequestPreferences requestPreferences = new RnaSeqBaselineRequestPreferences();
        SolrQuery solrQuery =
                ExperimentRequestPreferencesSolrQueryFactory.createSolrQuery(E_MTAB_513, requestPreferences);

        assertThat(solrQuery)
                .hasFieldOrPropertyWithValue(
                        "filterQueries",
                        new String[]{
                                "experiment_accession:(\"" + escapeQueryChars(E_MTAB_513) + "\")",
                                "expression_level:[" + requestPreferences.getDefaultCutoff() + " TO *]"})
                .hasFieldOrPropertyWithValue(
                        "query",
                        "(keyword_gene_biotype:(\"protein_coding\"))")
                .hasFieldOrPropertyWithValue(
                        "fields",
                        "*")
                .hasFieldOrPropertyWithValue(
                        "rows",
                        SolrQueryBuilder.MAX_ROWS);
    }

    @Test
    void testEmptyGeneQuery() {
        RnaSeqBaselineRequestPreferences requestPreferences = new RnaSeqBaselineRequestPreferences();
        requestPreferences.setGeneQuery(SemanticQuery.create());
        SolrQuery solrQuery =
                ExperimentRequestPreferencesSolrQueryFactory.createSolrQuery(E_MTAB_513, requestPreferences);

        assertThat(solrQuery)
                .hasFieldOrPropertyWithValue(
                        "filterQueries",
                        new String[]{
                                "experiment_accession:(\"" + escapeQueryChars(E_MTAB_513) + "\")",
                                "expression_level:[" + requestPreferences.getDefaultCutoff() + " TO *]"})
                .hasFieldOrPropertyWithValue(
                        "query",
                        "*:*")
                .hasFieldOrPropertyWithValue(
                        "fields",
                        "*")
                .hasFieldOrPropertyWithValue(
                        "rows",
                        SolrQueryBuilder.MAX_ROWS);
    }

    @Test
    void testQueriesAreJoinedWithAnd() {
        int numAssayGroups = ThreadLocalRandom.current().nextInt(1, 100);
        Set<String> assayGroups = new HashSet<>(numAssayGroups);
        while (assayGroups.size() < numAssayGroups) {
            assayGroups.add("g" + ThreadLocalRandom.current().nextInt(1, 1000));
        }

        RnaSeqBaselineRequestPreferences requestPreferences = new RnaSeqBaselineRequestPreferences();
        requestPreferences.setSelectedColumnIds(assayGroups);
        SolrQuery solrQuery =
                ExperimentRequestPreferencesSolrQueryFactory.createSolrQuery(E_MTAB_513, requestPreferences);

        List<String> explodedQuery = Arrays.asList(solrQuery.getQuery().split(" AND "));

        assertThat(explodedQuery)
                .hasSize(2)
                .allMatch(query -> query.matches("\\(\\w+:.+\\)"));

        assertThat(
                explodedQuery.stream()
                        .filter(query -> query.startsWith("(assay_group_id"))
                        .findFirst()
                        .orElse("")
                        .split(":")[1].split(" OR "))
                .hasSize(numAssayGroups);
    }

    // TODO test gene query OR’s the fields
}