package uk.ac.ebi.atlas.solr.cloud.admin;

import org.apache.solr.client.solrj.SolrServerException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import uk.ac.ebi.atlas.configuration.TestConfig;

import javax.inject.Inject;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
public class SolrCloudAdminProxyIT {

    @Inject
    private SolrCloudAdminProxy subject;

    @Test
    public void validCollectionNamesWithoutAliases() throws IOException, SolrServerException {
        assertThat(subject.areCollectionsUp(Arrays.asList("bioentities", "analytics"))).isTrue();
    }

    @Test
    public void validCollectionNamesWithAliases() throws IOException, SolrServerException {
        assertThat(subject.areCollectionsUp(Arrays.asList("bioentities", "analytics"), "scxa-analytics")).isTrue();
    }

    @Test
    public void invalidCollectionName() {
        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> subject.areCollectionsUp(Collections.singletonList("foo")));
    }

    @Test
    public void invalidAlias() {
        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> subject.areCollectionsUp(Collections.singletonList("bioentities"), "foo"));
    }
}
