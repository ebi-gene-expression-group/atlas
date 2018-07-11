package uk.ac.ebi.atlas.solr.bioentities.query;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uk.ac.ebi.atlas.configuration.TestConfig;
import uk.ac.ebi.atlas.solr.BioentityPropertyName;

import javax.inject.Inject;
import java.util.Set;

import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class BioentitiesSolrClientIT {

    @Inject
    public BioentitiesSolrClient subject;

    @Test
    public void testGetBioentityIdentifiers() {
        Set<String> result = subject.getBioentityIdentifiers(BioentityPropertyName.MGI_ID, "MGI:3615484");

        assertThat(result.size(), Matchers.equalTo(1));
        assertThat(result, Matchers.contains("ENSMUSG00000033450"));
    }
}