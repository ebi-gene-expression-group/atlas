package uk.ac.ebi.atlas.search.analyticsindex;

import com.google.common.collect.ImmutableSet;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.web.GeneQuery;

import javax.inject.Inject;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
public class AnalyticsSearchDaoIT {

    @Inject
    private AnalyticsSearchDao subject;

    // TODO Ignore until https://www.pivotaltracker.com/story/show/101118548
    @Ignore
    public void all() {
        ImmutableSet<String> experimentTypes = subject.fetchExperimentTypes(GeneQuery.create("*"));

        assertThat(experimentTypes, containsInAnyOrder("microarray_1colour_microrna_differential", "microarray_1colour_mrna_differential", "microarray_2colour_mrna_differential", "rnaseq_mrna_baseline", "rnaseq_mrna_differential", "proteomics_baseline"));
    }

    @Test
    public void baselineResultOnly() {
        ImmutableSet<String> experimentTypes = subject.fetchExperimentTypes(GeneQuery.create("OS12G0515800"));

        assertThat(experimentTypes, contains("rnaseq_mrna_baseline"));
    }


    @Test
    public void diffResultOnly() {
        ImmutableSet<String> experimentTypes = subject.fetchExperimentTypes(GeneQuery.create("AT5G32505"));

        assertThat(experimentTypes, contains("rnaseq_mrna_differential"));
    }

    @Test
    public void noDifferentialResultsWhenLogFoldChangeLessThanOne() {
        ImmutableSet<String> experimentTypes = subject.fetchExperimentTypes(GeneQuery.create("AT2G38660"));

        assertThat(experimentTypes, hasSize(0));
    }






}