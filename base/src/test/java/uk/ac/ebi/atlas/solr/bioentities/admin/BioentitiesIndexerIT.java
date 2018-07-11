package uk.ac.ebi.atlas.solr.bioentities.admin;

import com.google.common.collect.ImmutableSet;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.embedded.EmbeddedSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.params.SolrParams;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uk.ac.ebi.atlas.configuration.TestConfig;
import uk.ac.ebi.atlas.solr.EmbeddedSolrServerFactory;
import uk.ac.ebi.atlas.solr.bioentities.BioentityProperty;
import uk.ac.ebi.atlas.solr.bioentities.admin.monitor.BioentityIndexMonitor;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DirtiesContext
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class BioentitiesIndexerIT {
    @Inject
    private EmbeddedSolrServerFactory embeddedSolrServerFactory;

    @Inject
    private BioentityIndexMonitor bioentityIndexMonitor;

    private EmbeddedSolrServer embeddedBioentitiesSolrServer;
    private BioentityPropertiesSource bioentityPropertiesSource;

    private BioentitiesIndexer subject;

    @Before
    public void setUp() throws Exception {
        embeddedBioentitiesSolrServer = embeddedSolrServerFactory.createEmbeddedSolrServerInstance("bioentities");
        bioentityPropertiesSource = mock(BioentityPropertiesSource.class);
        subject = new BioentitiesIndexer(bioentityIndexMonitor, bioentityPropertiesSource, embeddedBioentitiesSolrServer);
    }

    @After
    public void tearDown() {
        subject.deleteAll();
    }

    @Test(timeout = 10*5000)
    public void test() throws Exception {
        addAndRetrieveAreOpposites(10000);
        for(int i = 0 ; i < 10 ; i++){
            addAndRetrieveAreOpposites(5);
        }
    }

    private List<BioentityProperty> randomProperties(int size) {
        List<BioentityProperty> result = new ArrayList<>(size);
        for(int i = 0 ; i< size; i++){
            result.add(new BioentityProperty(
                    RandomStringUtils.randomAlphanumeric(10),
                    RandomStringUtils.randomAlphabetic(5)+"_"+RandomStringUtils.randomAlphabetic(5),
                    RandomStringUtils.randomAlphabetic(10),
                    RandomStringUtils.randomAlphanumeric(10)
                    ));
        }
        return result;
    }

    private void addAndRetrieveAreOpposites(int size) throws Exception {
        List<BioentityProperty> data = randomProperties(size);

        BioentityPropertiesSource.AnnotationFile annotationFile = mock(BioentityPropertiesSource.AnnotationFile.class);
        when(annotationFile.get()).thenReturn(data.stream());

        when(bioentityPropertiesSource.getAnnotationFiles()).thenReturn(Stream.of(annotationFile));
        when(bioentityPropertiesSource.getArrayDesignMappingFiles()).thenReturn(Stream.of());
        when(bioentityPropertiesSource.getReactomePropertyFiles()).thenReturn(Stream.of());

        subject.doRebuild();

        embeddedBioentitiesSolrServer.commit();
        SolrParams solrQuery = new SolrQuery("*:*").setRows(2*size+10);

        QueryResponse queryResponse = embeddedBioentitiesSolrServer.query(solrQuery);

        assertThat(ImmutableSet.copyOf(queryResponse.getBeans(BioentityProperty.class)), is(ImmutableSet.copyOf(data)));
    }
}
