package uk.ac.ebi.atlas.search.baseline;

import autovalue.shaded.com.google.common.common.collect.Sets;
import com.google.common.base.Joiner;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.model.ExperimentType;
import uk.ac.ebi.atlas.model.OntologyTerm;
import uk.ac.ebi.atlas.model.baseline.Factor;
import uk.ac.ebi.atlas.solr.query.SolrQueryService;
import uk.ac.ebi.atlas.trader.ExperimentTrader;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
public class BaselineExperimentAssayGroupSearchServiceIT {

    @Inject
    BaselineExperimentAssayGroupSearchService subject;

    @Inject
    SolrQueryService solrQueryService;
    
    public void kinaseIsAPopularProtein()  {
        List<String> results = resultsFor("kinase","","");

        assertTrue(Sets.newHashSet(results).size() > 2);
    }

    @Test
    public void conditionsOR() {
        String geneQuery = "";
        String condition = "adipose thymus";
        String species = "";

        Set<BaselineExperimentAssayGroup> results = subject.query(geneQuery, condition, species, Optional.<Set<String>>absent());
        assertTrue(results.size()>0);
        for(BaselineExperimentAssayGroup result: results){
            Set<String> factorValues = result.getDefaultFactorValuesForSpecificAssayGroupsWithCondition();

            assertTrue(factorValues.contains("adipose") || factorValues.contains("thymus"));

        }
    }

    @Test
    public void conditionsAND()  {
        List<String> r0 = resultsFor("", "heart","");
        List<String> r1 = resultsFor("", "heart AND heart","");
        List<String> r2 = resultsFor("", "heart AND surely_nonexistent_organism_part","");
        assertEquals(r0,r1);
        assertEquals(Lists.newArrayList(), r2);
    }

    @Test
    public void buildResults_MTAB513() {
        ImmutableSetMultimap<String, String> assayGroupsWithExpressionByExperiment =
                new ImmutableSetMultimap.Builder<String, String>()
                .putAll("E-MTAB-513", "g12", "g14", "g16", "g8", "g2", "g4", "g9")
                .build();
        String species = "";
        Set<BaselineExperimentAssayGroup> baselineExperimentAssayGroups = subject.buildResults(assayGroupsWithExpressionByExperiment, true, species);
        BaselineExperimentAssayGroup searchResult = baselineExperimentAssayGroups.iterator().next();

        assertThat(baselineExperimentAssayGroups, hasSize(1));
        assertThat(searchResult.getFilterFactors().size(), is(0));
    }

    @Test
    public void wildTypeIsAValidConditionForThatWormExperiment(){
        List<String> results = resultsFor("","wild type genotype", "");
        assertTrue(results.contains("E-MTAB-2812"));
    }

    @Test
    public void weHaveAnMTABExperimentAboutASPM(){
        for(String name: resultsFor("ASPM","","")){
            if (Pattern.matches(".*MTAB.*", name)){
                return;
            }
        }
        fail();
    }

    @Test
    public void madeUpParametersYieldNoResults(){
        assertEquals(Lists.newArrayList(), resultsFor("such_a_wrong_gene_query","",""));
        assertEquals(Lists.newArrayList(), resultsFor("","totally_made_up_condition",""));
        assertEquals(Lists.newArrayList(), resultsFor("","","species_that_definitely_doesnt_exist"));
    }

    @Test
    public void partiallyMadeUpParametersYieldNoResultsEither(){
        /* interestingly that doesn't hold
        assertEquals(Lists.newArrayList(), resultsFor("Such_a_wrong_gene_query", "adult",""));
        */
        assertEquals(Lists.newArrayList(), resultsFor("","totally_made_up_condition","homo_sapiens"));
        assertEquals(Lists.newArrayList(), resultsFor("protein_coding","","species_yeti"));
    }

    @Test
    public void justGeneQueryNeedsExpansionIntoGeneIds(){
        String geneQuery = "ASPM";
        String condition = "";
        String species = "";

        List<String> fullResults = getExperimentAccessions(subject.query(geneQuery, condition, species, solrQueryService
                .expandGeneQueryIntoGeneIds
                (geneQuery, species.toLowerCase(), true)));

        List<String> resultsWithoutExpansion = getExperimentAccessions(subject.query(geneQuery, condition, species,
                Optional
                .<Set<String>>absent()));

        assertTrue(fullResults.size()>0);
        assertEquals(Lists.newArrayList(),resultsWithoutExpansion);
    }

    @Test
    public void someResultsForAdult(){
        List<String> r0 = resultsFor("","adult","");
        List<String> r1 = resultsFor("","adult","homo sapiens");
        assertTrue(r0.size()>10);
        assertTrue(r1.size()>0);
        assertTrue(r0.containsAll(r1));
        assertFalse(r1.equals(r0));
    }


    private List<String> resultsFor(String geneQuery, String condition, String species){
        return getExperimentAccessions(
                subject.query(geneQuery, condition, species, solrQueryService.expandGeneQueryIntoGeneIds
                        (geneQuery, species.toLowerCase(), true))
                );
    }

    private static List<String> getExperimentAccessions(Set<BaselineExperimentAssayGroup> results) {
        List<String> names = Lists.newArrayList();
        for (BaselineExperimentAssayGroup result: results) {
            names.add(result.getExperimentAccession());
        }
        return names;
    }
}
