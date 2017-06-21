package uk.ac.ebi.atlas.markergenes;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.inject.Inject;

import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContext.xml", "classpath:dbContext.xml"})
public class MarkerGenesSearchServiceIT {

    @Inject
    JdbcTemplate jdbcTemplate;

    MarkerGenesSearchService subject;

    @Test
    public void searchIdsOfGenesWithHighProbability() throws Exception {
        List<String> tenMarkerGeneIds = jdbcTemplate.queryForList("SELECT DISTINCT(GENE_ID) FROM MARKER_GENES WHERE MARKER_PROBABILITY > 0.95 LIMIT 10").stream()
            .map(rowMap -> (String) rowMap.get("GENE_ID"))
            .collect(Collectors.toList());


        for (String geneId : tenMarkerGeneIds) {
            assertThat(subject.searchMarkerGenesByGeneId(geneId), hasSize(greaterThan(0)));
        }
    }

    @Test
    public void searchIdsOfGenesWithLowProbability() throws Exception {
        List<String> tenNonMarkerGeneIds = jdbcTemplate.queryForList("SELECT DISTINCT(GENE_ID) FROM MARKER_GENES WHERE MARKER_PROBABILITY < 0.95 LIMIT 10").stream()
                .map(rowMap -> (String) rowMap.get("GENE_ID"))
                .collect(Collectors.toList());

        for (String geneId : tenNonMarkerGeneIds) {
            assertThat(subject.searchMarkerGenesByGeneId(geneId), hasSize(0));
        }

    }

    @Test
    public void searchNonExistentGeneId() throws Exception {
        assertThat(subject.searchMarkerGenesByGeneId("FOOBAR"), hasSize(0));
    }

}