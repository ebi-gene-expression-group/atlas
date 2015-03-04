package uk.ac.ebi.atlas.search.analyticsindex.differential;

import com.google.common.collect.Multimap;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.web.GeneQuery;

import javax.inject.Inject;

import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
public class DifferentialAnalyticsSearchDaoIT {

    @Inject
    private DifferentialAnalyticsSearchDao subject;

    @Test
    public void test() {
        Multimap<String, NameValue> stringNameValueMultimap = subject.fetchFacets(GeneQuery.create("*"));

        assertThat(stringNameValueMultimap.get("species"), hasItem(NameValue.create("arabidopsis thaliana")));
        assertThat(stringNameValueMultimap.get("experimentType"), hasItem(NameValue.create("rnaseq_mrna_differential")));
        assertThat(stringNameValueMultimap.get("factors"), hasItem(NameValue.create("genotype")));
        assertThat(stringNameValueMultimap.get("numReplicates"), hasItem(NameValue.create("3")));
        assertThat(stringNameValueMultimap.get("regulation"), hasItem(NameValue.create("UP")));
    }

}