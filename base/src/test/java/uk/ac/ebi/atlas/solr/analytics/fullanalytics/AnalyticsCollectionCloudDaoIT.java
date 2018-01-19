package uk.ac.ebi.atlas.solr.analytics.fullanalytics;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import org.apache.solr.client.solrj.io.Tuple;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class AnalyticsCollectionCloudDaoIT {

    @Inject
    private AnalyticsCollectionCloudDao subject;

    @Test
    public void blah() {
        List<Tuple> results = subject.facetedSearch(ImmutableMap.of(), ImmutableMap.of(), ImmutableSet.of(AnalyticsCollectionProxy.Field.BIOENTITY_IDENTIFIER));
        System.out.println(results.size());
        assertThat(results).isNotEmpty();
    }

}