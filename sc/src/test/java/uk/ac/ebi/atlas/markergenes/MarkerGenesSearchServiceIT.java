package uk.ac.ebi.atlas.markergenes;

import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.inject.Inject;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertThat;
import static org.junit.Assume.assumeThat;
import static uk.ac.ebi.atlas.markergenes.MarkerGenesSearchService.DEFAULT_P_THRESHOLD;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContext.xml", "classpath:dbContext.xml"})
public class MarkerGenesSearchServiceIT {

    @Inject
    JdbcTemplate jdbcTemplate;

    @Inject
    MarkerGenesSearchService subject;

    @Test
    public void searchIdsOfGenesWithHighProbability() throws Exception {
        List<String> fiveMarkerGeneIds =
                jdbcTemplate.queryForList(
                        "SELECT DISTINCT(gene_id) FROM marker_genes WHERE marker_probability>? LIMIT 5",
                        DEFAULT_P_THRESHOLD).stream()
            .map(rowMap -> (String) rowMap.get("gene_id"))
            .collect(Collectors.toList());

        assumeThat(fiveMarkerGeneIds, hasSize(5));

        for (String geneId : fiveMarkerGeneIds) {
            ImmutableList<ImmutablePair<MarkerGeneProfile, String>> results = subject.searchMarkerGenesByGeneId(geneId);
            assertThat(results, hasSize(greaterThan(0)));

            // No exceptions thrown: URLs are well-formed
            subject.searchMarkerGenesByGeneId(geneId).forEach(pair ->
                    assertThat(URI.create(pair.getRight()).toString(), startsWith("/"))); // URI is relative
        }
    }

    @Test
    public void searchIdsOfGenesWithLowProbability() throws Exception {
        List<String> fiveNonMarkerGeneIds = jdbcTemplate.queryForList(
                "SELECT all_p.gene_id FROM marker_genes all_p LEFT JOIN " +
                "  (SELECT gene_id, marker_probability FROM marker_genes WHERE marker_probability>?) high_p ON all_p.gene_id=high_p.gene_id " +
                "WHERE high_p.gene_id IS NULL LIMIT 5", DEFAULT_P_THRESHOLD).stream()
                .map(rowMap -> (String) rowMap.get("gene_id"))
                .collect(Collectors.toList());

        assumeThat(fiveNonMarkerGeneIds, hasSize(5));

        for (String geneId : fiveNonMarkerGeneIds) {
            assertThat(subject.searchMarkerGenesByGeneId(geneId), hasSize(0));
        }
    }

    @Test
    public void searchNonExistentGeneId() throws Exception {
        assertThat(subject.searchMarkerGenesByGeneId("FOOBAR"), hasSize(0));
    }

}