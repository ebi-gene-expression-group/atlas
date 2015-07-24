package uk.ac.ebi.atlas.search.analyticsindex.differential;

import autovalue.shaded.com.google.common.common.collect.Lists;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.web.GeneQuery;

import javax.inject.Inject;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
public class DifferentialAnalyticsSearchServiceIT extends TestCase {

    @Inject
    private DifferentialAnalyticsSearchService subject;

    @Test
    public void fetchDifferentialGeneQuerySelectionResultsAsJson() throws Exception {

        List<String> species = Lists.newArrayList();
        species.add("mus musculus");
        species.add("homo sapiens");

        List<String> experimentTypes = Lists.newArrayList();
        experimentTypes.add("rnaseq_mrna_differential");

        List<String> kingdoms = Lists.newArrayList();
        kingdoms.add("animals");

        List<String> factors = Lists.newArrayList();
        factors.add("genotype");
        factors.add("sex");

        GeneQuery geneQuery = GeneQuery.create("zinc finger");

        String json = subject.fetchDifferentialGeneQuerySelectionResultsAsJson(geneQuery, species, experimentTypes, kingdoms, factors, null, null);

        assertTrue(!json.isEmpty());

        assertThat(json, is("[{\"geneCount\":41,\"organism\":\"mus musculus\",\"contrastId\":\"g1_g2\",\"comparison\":\"genotype:'expressing human TDP-43' vs 'non transgenic'\"," +
                "\"experimentAccession\":\"E-GEOD-22351\",\"experimentName\":\"RNA-seq of mouse spinal cord expressing wild type human TDP-43\"},{\"geneCount\":28,\"organism\":\"mus musculus\"," +
                "\"contrastId\":\"g1_g2\",\"comparison\":\"sex:'male' vs 'female'\",\"experimentAccession\":\"E-MTAB-698\",\"experimentName\":\"RNA-seq of vomeronasal tissue from adult male and female mice\"}]"));
    }
}