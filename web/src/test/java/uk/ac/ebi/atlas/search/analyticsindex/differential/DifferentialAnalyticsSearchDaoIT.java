package uk.ac.ebi.atlas.search.analyticsindex.differential;

import autovalue.shaded.com.google.common.common.collect.Lists;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.model.ExperimentType;
import uk.ac.ebi.atlas.web.GeneQuery;

import javax.inject.Inject;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.core.Is.is;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
public class DifferentialAnalyticsSearchDaoIT {

    public static final String SPECIES_HOMO_SAPIENS = "species:\"homo sapiens\"";

    @Inject
    private DifferentialAnalyticsSearchDao subject;

    @Test
    public void differentialSearchWithUrlParams() {

        List<String> species = Lists.newArrayList();
        species.add("mus musculus");
        species.add("homo sapiens");

        List<String> experimentTypes = Lists.newArrayList();
        experimentTypes.add(ExperimentType.RNASEQ_MRNA_DIFFERENTIAL.getDescription());

        List<String> kingdoms = Lists.newArrayList();
        kingdoms.add("ensembl"); kingdoms.add("plants");

        List<String> factors = Lists.newArrayList();
        factors.add("genotype");

        String json = subject.fetchDifferentialGeneQueryResultsAboveDefaultFoldChange(GeneQuery.create("zinc finger"), species, experimentTypes, kingdoms, factors, null, null);

        ReadContext jsonCtx = JsonPath.parse(json);

        Integer count = jsonCtx.read("$.facets.count");
        List<String> speciesJson = jsonCtx.read("$.facets.species.buckets[*].val");//   mus musculus
        List<String> experimentType = jsonCtx.read("$.facets.species.buckets[*].experimentType.buckets[*].val"); // rnaseq_mrna_differential

        assertThat(count, is(79));
        assertThat(speciesJson, contains("mus musculus"));
        assertThat(experimentType, contains("rnaseq_mrna_differential"));

    }



}