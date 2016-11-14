package uk.ac.ebi.atlas.experimentpage;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.inject.Inject;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContext.xml", "classpath:oracleContext.xml"})
public class HierarchicalClusteringPdfViewHelperIT {
    @Inject
    ClusteringPdfViewHelper subject;

    @Test
    public void hasClusteringPdf() {
        assertThat(subject.hasFile("E-MTAB-513"), is(true));
        assertThat(subject.hasFile("E-GEOD-13316"), is(false));
    }

    @Test
    public void generateClusteringPdfUrl() {
        assertThat(subject.generateUrl("E-MTAB-513"), is("/experiments/E-MTAB-513/E-MTAB-513-heatmap.pdf"));
    }
}