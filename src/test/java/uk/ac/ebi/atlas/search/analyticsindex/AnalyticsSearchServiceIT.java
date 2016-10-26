package uk.ac.ebi.atlas.search.analyticsindex;

import com.google.common.collect.ImmutableSet;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.model.Species;
import uk.ac.ebi.atlas.model.SpeciesTest;
import uk.ac.ebi.atlas.search.SemanticQuery;
import uk.ac.ebi.atlas.trader.SpeciesFactory;

import javax.inject.Inject;

import java.util.Collection;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContext.xml", "classpath:oracleContext.xml"})
public class AnalyticsSearchServiceIT {

    @Inject
    AnalyticsSearchService subject;

    SemanticQuery query = SemanticQuery.create("zinc finger");

    SemanticQuery condition = SemanticQuery.create("pish");

    Species species = SpeciesTest.RICE;

    @Test
    public void fetchExperimentTypes1() {
        ImmutableSet<String> result = subject.fetchExperimentTypes("ENSG00000006432");
        assertThat(result.size(), greaterThan(0));
    }

    @Test
    public void fetchExperimentTypesInAnyField() {
        ImmutableSet<String> result = subject.fetchExperimentTypesInAnyField(query);

        assertThat(result.size(), greaterThan(0));
    }

    @Test
    public void fetchExperimentTypes2() {
        ImmutableSet<String> result = subject.fetchExperimentTypes(query, species);
        assertThat(result.size(), greaterThan(0));
    }

    @Test
    public void fetchExperimentTypes3() {
        ImmutableSet<String> result = subject.fetchExperimentTypes(query,condition,species);

        assertThat(result.size(), greaterThan(0));
    }

    @Test
    public void searchMoreThanOneBioentityIdentifier() {
        ImmutableSet<String> result = subject.searchMoreThanOneBioentityIdentifier(query,condition,species);

        assertThat(result.size(), greaterThan(0));
    }

    @Test
    public void searchBioentityIdentifiers() {
        ImmutableSet<String> result = subject.searchBioentityIdentifiers(query,condition,species);

        assertThat(result.size(), greaterThan(0));
    }


    @Test
    public void getBioentityIdentifiersForSpecies(){
        Collection<String> result = subject.getBioentityIdentifiersForSpecies(species);

        assertThat(result.size(), greaterThan(100));
    }


    @Test
    public void tissueExpressionAvailableFor() {
        boolean result = subject.tissueExpressionAvailableFor(query);

        assertThat(result, is(true));
    }
}