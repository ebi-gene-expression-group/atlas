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
@ContextConfiguration("classpath:applicationContext.xml")
public class SpeciesInferrerIT {

    static final String HOMO_SAPIENS = "homo sapiens";
    static final String ARABIDOPSIS_THALIANA = "arabidopsis thaliana";

    static final SemanticQueryTerm HUMAN_REACTOME_TERM = SemanticQueryTerm.create("R-HSA-597592", "pathwayid");
    static final SemanticQueryTerm LEAF_TERM = SemanticQueryTerm.create("leaf");

    static final SemanticQuery EMPTY_QUERY = SemanticQuery.create();
    static final SemanticQuery PLANT_CONDITION_QUERY = SemanticQuery.create(LEAF_TERM);
    static final SemanticQuery HUMAN_GENE_QUERY = SemanticQuery.create(HUMAN_REACTOME_TERM);

    @Inject
    SpeciesInferrer subject;

    @Test
    public void inferSpeciesForGeneQuery() throws Exception {
        Species species1 = subject.inferSpeciesForGeneQuery(HUMAN_GENE_QUERY);
        Species species2 = subject.inferSpeciesForGeneQuery(HUMAN_GENE_QUERY, "");

        assertThat(species1.getReferenceName(), is(HOMO_SAPIENS));
        assertThat(species2.getReferenceName(), is(HOMO_SAPIENS));
    }

    @Test
    public void conflictingSearch() throws Exception {
        assertThat(subject.inferSpecies(HUMAN_GENE_QUERY, EMPTY_QUERY, "").isUnknown(), is(false));
        assertThat(subject.inferSpecies(EMPTY_QUERY, PLANT_CONDITION_QUERY, "").isUnknown(), is(false));
        assertThat(subject.inferSpecies(HUMAN_GENE_QUERY, PLANT_CONDITION_QUERY, "").isUnknown(), is(true));
    }

    @Test
    public void emptyQuery() throws Exception {
        assertThat(subject.inferSpecies(EMPTY_QUERY, EMPTY_QUERY, "").isUnknown(), is(true));
        assertThat(subject.inferSpecies(null, EMPTY_QUERY, "").isUnknown(), is(true));
        assertThat(subject.inferSpecies(EMPTY_QUERY, null, "").isUnknown(), is(true));
        assertThat(subject.inferSpecies(null, null, "").isUnknown(), is(true));
        assertThat(subject.inferSpecies(EMPTY_QUERY, EMPTY_QUERY, null).isUnknown(), is(true));
        assertThat(subject.inferSpecies(null, EMPTY_QUERY, null).isUnknown(), is(true));
        assertThat(subject.inferSpecies(EMPTY_QUERY, null, null).isUnknown(), is(true));
        assertThat(subject.inferSpecies(null, null, null).isUnknown(), is(true));

    }

    @Test
    public void speciesStringOverridesQueryFields() throws Exception {
        Species species1 = subject.inferSpecies(HUMAN_GENE_QUERY, EMPTY_QUERY, null);
        Species species2 = subject.inferSpecies(HUMAN_GENE_QUERY, EMPTY_QUERY, ARABIDOPSIS_THALIANA);

        assertThat(species1.getReferenceName(), is(HOMO_SAPIENS));
        assertThat(species2.getReferenceName(), is(ARABIDOPSIS_THALIANA));
    }

}