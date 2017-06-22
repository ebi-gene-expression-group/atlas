package uk.ac.ebi.atlas.markergenes;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.inject.Inject;

import java.net.MalformedURLException;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertThat;
import static org.junit.Assume.assumeThat;

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
        List<String> tenMarkerGeneIds = jdbcTemplate.queryForList("SELECT DISTINCT(GENE_ID) FROM MARKER_GENES WHERE MARKER_PROBABILITY > 0.95 LIMIT 10").stream()
            .map(rowMap -> (String) rowMap.get("GENE_ID"))
            .collect(Collectors.toList());

        assumeThat(tenMarkerGeneIds, hasSize(10));

        for (String geneId : tenMarkerGeneIds) {
            List<Pair<MarkerGene, String>> results = subject.searchMarkerGenesByGeneId(geneId);
            assertThat(results, hasSize(greaterThan(0)));

            // No exceptions thrown: URLs are well-formed
            subject.searchMarkerGenesByGeneId(geneId).forEach(pair ->
                    assertThat(URI.create(pair.getRight()).toString(), startsWith("/"))); // URI is relative
        }
    }

    @Test
    public void searchIdsOfGenesWithLowProbability() throws Exception {
        List<String> tenNonMarkerGeneIds = jdbcTemplate.queryForList(
                "SELECT all_p.GENE_ID FROM MARKER_GENES all_p LEFT JOIN " +
                "  (SELECT GENE_ID, MARKER_PROBABILITY FROM MARKER_GENES WHERE MARKER_PROBABILITY > 0.95) high_p ON all_p.GENE_ID  = high_p.GENE_ID " +
                "WHERE high_p.GENE_ID IS NULL LIMIT 10").stream()
                .map(rowMap -> (String) rowMap.get("GENE_ID"))
                .collect(Collectors.toList());

        assumeThat(tenNonMarkerGeneIds, hasSize(10));

        for (String geneId : tenNonMarkerGeneIds) {
            assertThat(subject.searchMarkerGenesByGeneId(geneId), hasSize(0));
        }

    }

    @Test
    public void searchNonExistentGeneId() throws Exception {
        assertThat(subject.searchMarkerGenesByGeneId("FOOBAR"), hasSize(0));
    }

}