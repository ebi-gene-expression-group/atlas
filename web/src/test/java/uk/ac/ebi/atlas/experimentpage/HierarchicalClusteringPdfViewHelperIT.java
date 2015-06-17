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
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
public class HierarchicalClusteringPdfViewHelperIT {

    @Inject
    HierarchicalClusteringPdfViewHelper subject;

    @Test
    public void hasSingleSpeciesPdf_EMTAB_513() {
        assertThat(subject.hasSingleSpeciesPdf("E-MTAB-513"), is(true));
    }

    @Test
    public void hasPdf_EGEOD_30352_homo_sapiens() {
        assertThat(subject.hasPdf("E-GEOD-30352", "homo sapiens"), is(true));
    }


}