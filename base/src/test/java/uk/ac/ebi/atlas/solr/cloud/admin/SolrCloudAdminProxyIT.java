package uk.ac.ebi.atlas.solr.cloud.admin;

import org.apache.solr.client.solrj.SolrServerException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.inject.Inject;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("classpath:applicationContext.xml")
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
        assertThrows(RuntimeException.class, () -> subject.areCollectionsUp(Collections.singletonList("foo")));
    }

    @Test
    public void invalidAlias() {
        assertThrows(RuntimeException.class, () -> subject.areCollectionsUp(Collections.singletonList("bioentities"), "foo"));
    }
}
