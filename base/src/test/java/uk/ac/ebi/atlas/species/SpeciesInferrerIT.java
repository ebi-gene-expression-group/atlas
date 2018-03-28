package uk.ac.ebi.atlas.species;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uk.ac.ebi.atlas.search.SemanticQuery;
import uk.ac.ebi.atlas.search.SemanticQueryTerm;

import javax.inject.Inject;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class SpeciesInferrerIT {
    private static final String HOMO_SAPIENS = "homo sapiens";
    private static final String ARABIDOPSIS_THALIANA = "arabidopsis thaliana";
    private static final String GLYCINE_MAX = "glycine max";

    private static final SemanticQueryTerm HUMAN_REACTOME_TERM = SemanticQueryTerm.create("R-HSA-597592", "pathwayid");
    private static final SemanticQueryTerm PLANT_REACTOME_TERM = SemanticQueryTerm.create("R-GMA-2744341", "pathwayid");
    private static final SemanticQueryTerm LEAF_TERM = SemanticQueryTerm.create("leaf");

    private static final SemanticQuery EMPTY_QUERY = SemanticQuery.create();
    private static final SemanticQuery PLANT_CONDITION_QUERY = SemanticQuery.create(LEAF_TERM);
    private static final SemanticQuery HUMAN_GENE_QUERY = SemanticQuery.create(HUMAN_REACTOME_TERM);
    private static final SemanticQuery PLANT_GENE_QUERY = SemanticQuery.create(PLANT_REACTOME_TERM);
    private static final SemanticQuery MIXED_SPECIES_GENE_QUERY = SemanticQuery.create(SemanticQueryTerm.create("OS03G0852700", "ensgene"), SemanticQueryTerm.create("ENSMUSG00000002055", "ensgene"));

    @Inject
    private SpeciesInferrer subject;

    @Test
    public void inferSpeciesForGeneQuery() {
        Species species1 = subject.inferSpeciesForGeneQuery(HUMAN_GENE_QUERY);
        Species species2 = subject.inferSpeciesForGeneQuery(HUMAN_GENE_QUERY, "");
        Species species3 = subject.inferSpeciesForGeneQuery(PLANT_GENE_QUERY);

        assertThat(species1.getReferenceName(), is(HOMO_SAPIENS));
        assertThat(species2.getReferenceName(), is(HOMO_SAPIENS));
        assertThat(species3.getReferenceName(), is(GLYCINE_MAX));
        assertThat(species3.isPlant(),is(true));
    }

    @Test
    public void inferSpeciesForPlantGeneQuery() {
        Species species1 = subject.inferSpeciesForGeneQuery(PLANT_GENE_QUERY, "");
        Species species2 = subject.inferSpeciesForGeneQuery(PLANT_GENE_QUERY);

        assertThat(species1.getReferenceName(), is(GLYCINE_MAX));
        assertThat(species2.getReferenceName(), is(GLYCINE_MAX));

        assertThat(species1.isPlant(),is(true));
        assertThat(species2.isPlant(),is(true));
    }

    @Test
    public void conflictingSearch() {
        assertThat(subject.inferSpecies(HUMAN_GENE_QUERY, EMPTY_QUERY, "").isUnknown(), is(false));
        assertThat(subject.inferSpecies(EMPTY_QUERY, PLANT_CONDITION_QUERY, "").isUnknown(), is(true));
        assertThat(subject.inferSpecies(HUMAN_GENE_QUERY, PLANT_CONDITION_QUERY, "").isUnknown(), is(true));
    }

    @Test
    public void mixedSpeciesGeneQueryIsUnknown() {
        assertThat(subject.inferSpeciesForGeneQuery(MIXED_SPECIES_GENE_QUERY).isUnknown(), is(true));
    }

    @Test
    public void emptyQuery() {
        assertThat(subject.inferSpecies(EMPTY_QUERY, EMPTY_QUERY, "").isUnknown(), is(true));
    }

    @Test
    public void speciesStringOverridesQueryFields() {
        Species species = subject.inferSpecies(HUMAN_GENE_QUERY, EMPTY_QUERY, ARABIDOPSIS_THALIANA);

        assertThat(species.getReferenceName(), is(ARABIDOPSIS_THALIANA));
    }

    @Test
    public void inferSpeciesForGeneIds() {
        assertThat(
                subject.inferSpeciesForGeneQuery(SemanticQuery.create("ENSMUSG00000019082")).getReferenceName(),
                is("mus musculus"));
        assertThat(
                subject.inferSpeciesForGeneQuery(SemanticQuery.create("FBgn0260743")).getReferenceName(),
                is("drosophila melanogaster"));
    }
}