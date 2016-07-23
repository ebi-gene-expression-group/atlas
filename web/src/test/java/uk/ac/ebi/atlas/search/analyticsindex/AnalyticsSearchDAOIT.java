package uk.ac.ebi.atlas.search.analyticsindex;

import com.google.common.collect.ImmutableSet;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.search.SemanticQuery;

import javax.inject.Inject;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
public class AnalyticsSearchDAOIT {

    @Inject
    private AnalyticsIndexSearchDAO subject;

    @Test
    public void all() {
        ImmutableSet<String> experimentTypes = subject.fetchExperimentTypes(SemanticQuery.create(), SemanticQuery.create(), "");
        assertThat(experimentTypes, containsInAnyOrder("microarray_1colour_microrna_differential", "microarray_1colour_mrna_differential", "microarray_2colour_mrna_differential", "rnaseq_mrna_baseline", "rnaseq_mrna_differential", "proteomics_baseline"));
    }

    @Test
    public void baselineResultOnly() {
        ImmutableSet<String> experimentTypes = subject.fetchExperimentTypes(SemanticQuery.create("ENSG00000005810"), SemanticQuery.create(), "");
        assertThat(experimentTypes, containsInAnyOrder("proteomics_baseline", "rnaseq_mrna_baseline"));
    }

    @Test
    public void diffResultOnly() {
        ImmutableSet<String> experimentTypes = subject.fetchExperimentTypes(SemanticQuery.create("FBgn0000064"), SemanticQuery.create(), "");
        assertThat(experimentTypes, contains("rnaseq_mrna_differential"));
    }

}