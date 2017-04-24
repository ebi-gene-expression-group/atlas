package uk.ac.ebi.atlas.search.analyticsindex.baseline;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.model.FactorAcrossExperiments;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.model.experiment.baseline.Factor;
import uk.ac.ebi.atlas.search.SemanticQuery;
import uk.ac.ebi.atlas.search.baseline.BaselineExperimentProfile;
import uk.ac.ebi.atlas.search.baseline.BaselineExperimentProfilesList;
import uk.ac.ebi.atlas.search.baseline.BaselineExperimentSearchResult;
import uk.ac.ebi.atlas.species.Species;
import uk.ac.ebi.atlas.species.SpeciesFactory;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/applicationContext.xml", "/solrContext.xml", "/dbContext.xml"})
public class BaselineAnalyticsSearchServiceIT {

    private static Species HUMAN;
    private static final List<FactorAcrossExperiments> organismPartFactors = new ArrayList<>();

    public static final String ORGANISM_PART = "ORGANISM_PART";

    @Inject
    private SpeciesFactory speciesFactory;

    static {
        organismPartFactors.add(new FactorAcrossExperiments(new Factor(ORGANISM_PART, "adipose")));
        organismPartFactors.add(new FactorAcrossExperiments(new Factor(ORGANISM_PART, "adrenal")));//
        organismPartFactors.add(new FactorAcrossExperiments(new Factor(ORGANISM_PART, "adrenal gland")));//
        organismPartFactors.add(new FactorAcrossExperiments(new Factor(ORGANISM_PART, "appendix")));
        organismPartFactors.add(new FactorAcrossExperiments(new Factor(ORGANISM_PART, "bladder")));
        organismPartFactors.add(new FactorAcrossExperiments(new Factor(ORGANISM_PART, "bone marrow")));
        organismPartFactors.add(new FactorAcrossExperiments(new Factor(ORGANISM_PART, "CD4-positive T cell")));
        organismPartFactors.add(new FactorAcrossExperiments(new Factor(ORGANISM_PART, "CD8-positive T cell")));
        organismPartFactors.add(new FactorAcrossExperiments(new Factor(ORGANISM_PART, "cerebral cortex")));
        organismPartFactors.add(new FactorAcrossExperiments(new Factor(ORGANISM_PART, "colon")));
        organismPartFactors.add(new FactorAcrossExperiments(new Factor(ORGANISM_PART, "duodenum")));
        organismPartFactors.add(new FactorAcrossExperiments(new Factor(ORGANISM_PART, "endometrium")));
        organismPartFactors.add(new FactorAcrossExperiments(new Factor(ORGANISM_PART, "esophagus")));
        organismPartFactors.add(new FactorAcrossExperiments(new Factor(ORGANISM_PART, "fallopian tube")));
        organismPartFactors.add(new FactorAcrossExperiments(new Factor(ORGANISM_PART, "gall bladder")));
        organismPartFactors.add(new FactorAcrossExperiments(new Factor(ORGANISM_PART, "heart")));
        organismPartFactors.add(new FactorAcrossExperiments(new Factor(ORGANISM_PART, "kidney")));
        organismPartFactors.add(new FactorAcrossExperiments(new Factor(ORGANISM_PART, "liver")));
        organismPartFactors.add(new FactorAcrossExperiments(new Factor(ORGANISM_PART, "lung")));
        organismPartFactors.add(new FactorAcrossExperiments(new Factor(ORGANISM_PART, "lymph node")));
        organismPartFactors.add(new FactorAcrossExperiments(new Factor(ORGANISM_PART, "ovary")));
        organismPartFactors.add(new FactorAcrossExperiments(new Factor(ORGANISM_PART, "pancreas")));
        organismPartFactors.add(new FactorAcrossExperiments(new Factor(ORGANISM_PART, "placenta")));
        organismPartFactors.add(new FactorAcrossExperiments(new Factor(ORGANISM_PART, "prostate")));
        organismPartFactors.add(new FactorAcrossExperiments(new Factor(ORGANISM_PART, "rectum")));
        organismPartFactors.add(new FactorAcrossExperiments(new Factor(ORGANISM_PART, "salivary gland")));
        organismPartFactors.add(new FactorAcrossExperiments(new Factor(ORGANISM_PART, "skeletal muscle")));
        organismPartFactors.add(new FactorAcrossExperiments(new Factor(ORGANISM_PART, "skin")));
        organismPartFactors.add(new FactorAcrossExperiments(new Factor(ORGANISM_PART, "small intestine")));
        organismPartFactors.add(new FactorAcrossExperiments(new Factor(ORGANISM_PART, "smooth muscle")));
        organismPartFactors.add(new FactorAcrossExperiments(new Factor(ORGANISM_PART, "spleen")));
        organismPartFactors.add(new FactorAcrossExperiments(new Factor(ORGANISM_PART, "stomach")));
        organismPartFactors.add(new FactorAcrossExperiments(new Factor(ORGANISM_PART, "testis")));
        organismPartFactors.add(new FactorAcrossExperiments(new Factor(ORGANISM_PART, "tonsil")));
        organismPartFactors.add(new FactorAcrossExperiments(new Factor(ORGANISM_PART, "thyroid")));
    }

    private static final String CELL_LINE = "CELL_LINE";
    private static final List<FactorAcrossExperiments> cellLineFactors = new ArrayList<>();

    static {
        cellLineFactors.add(new FactorAcrossExperiments(new Factor(CELL_LINE, "A549")));
        cellLineFactors.add(new FactorAcrossExperiments(new Factor(CELL_LINE, "AG445")));
        cellLineFactors.add(new FactorAcrossExperiments(new Factor(CELL_LINE, "BJ")));
        cellLineFactors.add(new FactorAcrossExperiments(new Factor(CELL_LINE, "CD20-positive B cell cell line")));
        cellLineFactors.add(new FactorAcrossExperiments(new Factor(CELL_LINE, "CD34-positive mobilized cell cell line")));
        cellLineFactors.add(new FactorAcrossExperiments(new Factor(CELL_LINE, "GM12878")));
        cellLineFactors.add(new FactorAcrossExperiments(new Factor(CELL_LINE, "H1-hESC")));
        cellLineFactors.add(new FactorAcrossExperiments(new Factor(CELL_LINE, "HFDPC cell line")));
        cellLineFactors.add(new FactorAcrossExperiments(new Factor(CELL_LINE, "HMEC cell line")));
        cellLineFactors.add(new FactorAcrossExperiments(new Factor(CELL_LINE, "HPC-PL cell line")));
        cellLineFactors.add(new FactorAcrossExperiments(new Factor(CELL_LINE, "HSMM cell line")));
        cellLineFactors.add(new FactorAcrossExperiments(new Factor(CELL_LINE, "HUVEC cell line")));
        cellLineFactors.add(new FactorAcrossExperiments(new Factor(CELL_LINE, "HeLa-S3")));
        cellLineFactors.add(new FactorAcrossExperiments(new Factor(CELL_LINE, "HepG2")));
        cellLineFactors.add(new FactorAcrossExperiments(new Factor(CELL_LINE, "hMSC-AT cell line")));
        cellLineFactors.add(new FactorAcrossExperiments(new Factor(CELL_LINE, "IMR-90")));
        cellLineFactors.add(new FactorAcrossExperiments(new Factor(CELL_LINE, "K562")));
        cellLineFactors.add(new FactorAcrossExperiments(new Factor(CELL_LINE, "MCF-7")));
        cellLineFactors.add(new FactorAcrossExperiments(new Factor(CELL_LINE, "NHEK cell line")));
        cellLineFactors.add(new FactorAcrossExperiments(new Factor(CELL_LINE, "NHLF cell line")));
        cellLineFactors.add(new FactorAcrossExperiments(new Factor(CELL_LINE, "SK-N-SH")));
        cellLineFactors.add(new FactorAcrossExperiments(new Factor(CELL_LINE, "SK-N-SH_RA")));
    }

    @Inject
    private BaselineAnalyticsSearchService subject;

    @Before
    public void setUp() {
        HUMAN = speciesFactory.create("homo sapiens");
    }

    @Ignore
    public void singleSpeciesGeneAccessionTissues() {
        //"ENSG00000006062" seems to be a famous enough gene
        BaselineExperimentSearchResult result = subject.findExpressions(SemanticQuery.create("ENSG00000006062"),
                SemanticQuery.create(), HUMAN, "ORGANISM_PART");

        BaselineExperimentProfilesList baselineProfilesList = result.getExperimentProfiles();

        assertTrue(baselineProfilesList.size() > 0);
        for (BaselineExperimentProfile profile : baselineProfilesList) {
            assertThat(profile.getId(), not(isEmptyOrNullString()));
            assertThat(profile.getName(), not(isEmptyOrNullString()));
            assertThat(ExperimentType.get(profile.getExperimentType()).isBaseline(), is(true));
            assertThat(profile.getConditions().size(), greaterThan(1));
            assertThat(profile.isExpressedOnAnyOf(organismPartFactors), is(true));
            assertThat(profile.isExpressedOnAnyOf(cellLineFactors), is(false));
        }

        // test organism part is the default
        BaselineExperimentSearchResult result2 = subject.findExpressions(SemanticQuery.create("ENSG00000006062"),
                SemanticQuery.create(), HUMAN, "");

        assertEquals(result.getExperimentProfiles(), result2.getExperimentProfiles());

    }

    @Ignore
    public void geneQueryCellLine() {
        BaselineExperimentSearchResult result = subject.findExpressions(SemanticQuery.create("protein_coding"), SemanticQuery.create(), HUMAN, CELL_LINE);

        BaselineExperimentProfilesList baselineProfilesList = result.getExperimentProfiles();

        assertThat(baselineProfilesList.size(), greaterThan(0));
        for(BaselineExperimentProfile profile : baselineProfilesList) {
            assertThat(profile.isExpressedOnAnyOf(organismPartFactors), is(false));
            assertThat(profile.isExpressedOnAnyOf(cellLineFactors), is(true));
        }
    }

}