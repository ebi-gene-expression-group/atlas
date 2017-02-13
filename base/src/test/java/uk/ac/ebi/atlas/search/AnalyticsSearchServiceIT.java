package uk.ac.ebi.atlas.search;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uk.ac.ebi.atlas.search.analyticsindex.AnalyticsSearchService;
import uk.ac.ebi.atlas.species.Species;
import uk.ac.ebi.atlas.species.SpeciesFactory;

import javax.inject.Inject;
import java.util.Collection;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/applicationContext.xml", "/solrContext.xml", "/embeddedSolrServerContext.xml", "/oracleContext.xml"})
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
    public void speciesOfEmptyQueryIsAbsent() {
        Optional<String> species = subject.findSpeciesFor(SemanticQuery.create(), SemanticQuery.create());
        assertThat(species.isPresent(), is(false));
    }

    @Test
    public void speciesOfEmptyResultsIsAbsent() {
        SemanticQueryTerm foobarQueryTerm = SemanticQueryTerm.create("Foo", "Bar");
        Optional<String> species = subject.findSpeciesFor(SemanticQuery.create(), SemanticQuery.create(foobarQueryTerm));
        assertThat(species.isPresent(), is(false));
    }

    @Test
    public void speciesOfSpeciesSpecificSearch() {
        SemanticQueryTerm reactomeQueryTerm = SemanticQueryTerm.create("R-MMU-69002", "pathwayid");
        Optional<String> species = subject.findSpeciesFor(SemanticQuery.create(reactomeQueryTerm), SemanticQuery.create());
        assertThat(species.get(), is("mus musculus"));
    }

    @Test
    public void speciesOfMultipleSpeciesSearch() {
        SemanticQueryTerm reactomeQueryTerm = SemanticQueryTerm.create("GO:0008150", "go");
        Optional<String> species = subject.findSpeciesFor(SemanticQuery.create(reactomeQueryTerm), SemanticQuery.create());
        assertThat(speciesFactory.create(species.get()).isUnknown(), is(false));
    }

}