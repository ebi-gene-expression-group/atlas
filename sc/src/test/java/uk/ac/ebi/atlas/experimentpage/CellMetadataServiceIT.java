package uk.ac.ebi.atlas.experimentpage;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.experimentimport.idf.IdfParser;
import uk.ac.ebi.atlas.solr.cloud.SolrCloudCollectionProxyFactory;

import javax.inject.Inject;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:dispatcher-servlet.xml"})
public class CellMetadataServiceIT {

    @Inject
    private IdfParser idfParser;
    @Inject
    private SolrCloudCollectionProxyFactory solrCloudCollectionProxyFactory;

    private CellMetadataService subject;

    @Before
    public void setUp() {
        this.subject = new CellMetadataService(idfParser, solrCloudCollectionProxyFactory);
    }

    @Test
    public void existingInferredCellType() {
        assertThat(subject.getInferredCellType("E-GEOD-84465", "SRR3934353")).isPresent();
    }

    @Test
    public void inferredCellTypeForNonexistentExperimentId() {
        assertThat(subject.getInferredCellType("FOO", "SRR3934353")).isNotPresent();
    }

    @Test
    public void inferredCellTypeForNonexistentCellId() {
        assertThat(subject.getInferredCellType("E-GEOD-84465", "FOO")).isNotPresent();
    }

    @Test
    public void experimentWithoutMetadataFieldsInIdf() {
        assertThat(subject.getAttributeFromIdfFile("E-ENAD-13", "SRR5101952")).isEmpty();
    }
}
