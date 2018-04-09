package uk.ac.ebi.atlas.solr.cloud.fullanalytics;

import org.apache.solr.client.solrj.SolrQuery;
import org.junit.Test;
import uk.ac.ebi.atlas.model.ExpressionUnit;
import uk.ac.ebi.atlas.search.SemanticQuery;
import uk.ac.ebi.atlas.search.SemanticQueryTerm;
import uk.ac.ebi.atlas.web.RnaSeqBaselineRequestPreferences;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.stream.Collectors.joining;
import static org.assertj.core.api.Assertions.assertThat;

public class ExperimentRequestPreferencesSolrQueryFactoryTest {

    private static final double MAX_PROTEOMICS_EXPRESSION_LEVEL = 1.9659E11;
    private static final double MAX_TPM_EXPRESSION_LEVEL = 1663197.0;
    private static final double MAX_FPKM_EXPRESSION_LEVEL = 3196594.0;

    @Test
    public void constructWithRnaSeqBaselineRequestPreferences() {
        double cutoff = ThreadLocalRandom.current().nextDouble(0.0, MAX_TPM_EXPRESSION_LEVEL);

        SemanticQuery geneQuery =
                SemanticQuery.create(
                        SemanticQueryTerm.create("ENSFOOBAR"), SemanticQueryTerm.create("foobar", "symbol"));

        int numOfAssayGroups = ThreadLocalRandom.current().nextInt(0, 1000);
        HashSet<String> assayGroups = new HashSet<>(numOfAssayGroups);
        while (assayGroups.size() < numOfAssayGroups) {
            assayGroups.add("g" + Integer.toString(ThreadLocalRandom.current().nextInt(1, 1000)));
        }

        RnaSeqBaselineRequestPreferences requestPreferences = new RnaSeqBaselineRequestPreferences();
        requestPreferences.setUnit(ExpressionUnit.Absolute.Rna.TPM);
        requestPreferences.setCutoff(cutoff);
        requestPreferences.setGeneQuery(geneQuery);
        requestPreferences.setSelectedColumnIds(assayGroups);

        SolrQuery solrQuery =
                ExperimentRequestPreferencesSolrQueryFactory.createSolrQueryForBaselineExperiment(
                        "E-FOOBAR", requestPreferences);

        assertThat(solrQuery.getFilterQueries())
                .hasSize(2)
                .containsExactlyInAnyOrder(
                        "experiment_accession:(\"E-FOOBAR\")",
                        "expression_level:[" + cutoff + " TO *]");

        Pattern fieldValueRegex = Pattern.compile("[a-z_]+?:\\(.+?\\)");
        Matcher matcher = fieldValueRegex.matcher(solrQuery.getQuery());
        List<String> matchedGroups = new ArrayList<>();
        while (matcher.find()) {
            matchedGroups.add(matcher.group());
        }

        // Free text terms are both searched in identifier_search and bioentity_identifier_search because some gene IDs
        // aren’t present in the bioentities collection and won’t be available in the suggester, and that’s the only
        // way to look for them.
        assertThat(matchedGroups)
                .hasSize(4)
                .containsExactlyInAnyOrder(
                        "assay_group_id:(" + assayGroups.stream().map(assayGroup -> "\"" + assayGroup + "\"").collect(joining(" OR ")),
                        "identifier_search:(\"ensfoobar\")",
                        "bioentity_identifier_search:(\"ensfoobar\")",
                        "keyword_symbol:(\"foobar\")"
                );

//        assertThat(solrQuery.getQuery())
//                .startsWith(
//                        "(" +
//                        ASSAY_GROUP_ID.name() + ":(" +
//                        assayGroups.stream().map(assayGroup -> "\"" + assayGroup + "\"").collect(joining(" OR ")) +
//                        "))")
//                .endsWith(
//                        " AND (" +
//                        "keyword_symbol:(\"foobar\") OR " +
//                        BIOENTITY_IDENTIFIER_SEARCH.name() + ":(\"ensfoobar\" OR \"foobar\") OR " +
//                        IDENTIFIER_SEARCH.name() + ":(\"ensfoobar\")" +
//                        ")"
//                );
    }

}