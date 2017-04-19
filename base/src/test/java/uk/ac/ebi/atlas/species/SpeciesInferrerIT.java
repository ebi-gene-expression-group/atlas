package uk.ac.ebi.atlas.species;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uk.ac.ebi.atlas.search.SemanticQuery;
import uk.ac.ebi.atlas.search.SemanticQueryTerm;

import javax.inject.Inject;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/applicationContext.xml", "/solrContext.xml", "/embeddedSolrServerContext.xml", "/dbContext.xml"})
public class SpeciesInferrerIT {

    private static final SemanticQueryTerm HUMAN_REACTOME_TERM = SemanticQueryTerm.create("R-HSA-597592", "pathwayid");
    private static final SemanticQueryTerm LEAF_TERM = SemanticQueryTerm.create("leaf");
    private static final SemanticQueryTerm DISEASE_TERM = SemanticQueryTerm.create("disease");

    private static final SemanticQuery EMPTY_QUERY = SemanticQuery.create();
    private static final SemanticQuery PLANT_CONDITION_QUERY = SemanticQuery.create(LEAF_TERM);
    private static final SemanticQuery HUMAN_GENE_QUERY = SemanticQuery.create(HUMAN_REACTOME_TERM);
    private static final SemanticQuery DISEASE_CONDITION_QUERY = SemanticQuery.create(DISEASE_TERM);

    @Inject
    private SpeciesFactory speciesFactory;

    @Inject
    private SpeciesInferrer subject;


    @Test
    public void inferSpecies() throws Exception {
        Species species1 = subject.inferSpecies(HUMAN_GENE_QUERY, DISEASE_CONDITION_QUERY);
        Species species2 = subject.inferSpecies(PLANT_CONDITION_QUERY, DISEASE_CONDITION_QUERY);
        assertThat(species1.getReferenceName(), is(not(species2.getReferenceName())));
    }

    @Test
    public void inferSpeciesForGeneQuery() throws Exception {
        Species species = subject.inferSpeciesForGeneQuery(HUMAN_GENE_QUERY);
        assertThat(species.getReferenceName(), is("homo sapiens"));
    }

    @Test
    public void inferSpeciesForConditionQuery() throws Exception {
        Species species = subject.inferSpeciesForConditionQuery(PLANT_CONDITION_QUERY);
        assertThat(species.isPlant(), is(true));
    }

    @Test
    public void conflictingSearch() throws Exception {
        Species mouseSpecies = subject.inferSpecies(null, null);
        Species plantSpecies = subject.inferSpecies(null, PLANT_CONDITION_QUERY);
        assertThat(mouseSpecies.getReferenceName(), is("mus musculus"));
        assertThat(plantSpecies.isPlant(), is (true));

        assertThat(subject.inferSpecies(null, PLANT_CONDITION_QUERY).isUnknown(), is(true));
    }

    @Test
    public void knownSpeciesWithoutIndexedData() throws Exception {
        Species aFumigatus = speciesFactory.create("aspergillus fumigatus");
        assertThat(aFumigatus.isUnknown(), is(false));
        assertThat(subject.inferSpecies(EMPTY_QUERY, EMPTY_QUERY).isUnknown(), is(true));
    }

    @Test
    public void emptyQuery() throws Exception {
        assertThat(subject.inferSpecies(EMPTY_QUERY, EMPTY_QUERY).isUnknown(), is(true));
        assertThat(subject.inferSpecies(null, null).isUnknown(), is(true));
    }

}