package uk.ac.ebi.atlas.search.analyticsindex;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uk.ac.ebi.atlas.search.SemanticQuery;
import uk.ac.ebi.atlas.search.SemanticQueryTerm;
import uk.ac.ebi.atlas.solr.analytics.AnalyticsSearchService;
import uk.ac.ebi.atlas.species.Species;
import uk.ac.ebi.atlas.species.SpeciesFactory;

import javax.inject.Inject;
import java.util.Collection;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class AnalyticsSearchServiceIT {

    @Inject
    private SpeciesFactory speciesFactory;

    @Inject
    private AnalyticsSearchService subject;

    private SemanticQuery query = SemanticQuery.create("zinc finger");
    private SemanticQuery condition = SemanticQuery.create("pish");
    private Species species;

    @Before
    public void setUp() {
        species = speciesFactory.create("oryza sativa");
    }

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
        ImmutableSet<String> result = subject.fetchExperimentTypes(query, species.getReferenceName());
        assertThat(result.size(), greaterThan(0));
    }

    @Test
    public void fetchExperimentTypes3() {
        ImmutableSet<String> result = subject.fetchExperimentTypes(query, condition, species.getReferenceName());
        assertThat(result.size(), greaterThan(0));
    }

    @Test
    public void searchMoreThanOneBioentityIdentifier() {
        ImmutableSet<String> result = subject.searchMoreThanOneBioentityIdentifier(query, condition, species.getReferenceName());
        assertThat(result.size(), greaterThan(0));
    }

    @Test
    public void searchBioentityIdentifiers() {
        ImmutableSet<String> result = subject.searchBioentityIdentifiers(query, condition, species.getReferenceName());
        assertThat(result.size(), greaterThan(0));
    }


    @Test
    public void getBioentityIdentifiersForSpecies(){
        Collection<String> result = subject.getBioentityIdentifiersForSpecies(species.getReferenceName());
        assertThat(result.size(), greaterThan(100));
    }


    @Test
    public void tissueExpressionAvailableFor() {
        boolean result = subject.tissueExpressionAvailableFor(query);
        assertThat(result, is(true));
    }

    @Test
    public void speciesOfEmptyQuery() {
        ImmutableList<String> species = subject.findSpecies(SemanticQuery.create(), SemanticQuery.create());
        assertThat(species.size(), is(greaterThan(25)));    // Number of species present in analytics index is now 27
    }

    @Test
    public void speciesWhenNoResults() {
        SemanticQueryTerm foobarQueryTerm = SemanticQueryTerm.create("Foo", "Bar");
        ImmutableList<String> species = subject.findSpecies(SemanticQuery.create(), SemanticQuery.create(foobarQueryTerm));
        assertThat(species, hasSize(0));
    }

    @Test
    public void speciesSpecificSearch() {
        SemanticQueryTerm reactomeQueryTerm = SemanticQueryTerm.create("R-MMU-69002", "pathwayid");
        ImmutableList<String> species = subject.findSpecies(SemanticQuery.create(reactomeQueryTerm), SemanticQuery.create());
        assertThat(species, hasSize(1));
        assertThat(species.get(0), is("mus musculus"));
    }

    @Test
    public void multipleSpeciesSearch() {
        SemanticQueryTerm reactomeQueryTerm = SemanticQueryTerm.create("GO:0008150", "go");
        ImmutableList<String> species = subject.findSpecies(SemanticQuery.create(reactomeQueryTerm), SemanticQuery.create());
        assertThat(species.size(), is(greaterThan(0)));
        assertThat(speciesFactory.create(species.get(0)).isUnknown(), is(false));
    }

}