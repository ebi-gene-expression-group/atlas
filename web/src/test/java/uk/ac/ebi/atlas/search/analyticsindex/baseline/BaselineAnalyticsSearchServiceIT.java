package uk.ac.ebi.atlas.search.analyticsindex.baseline;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.model.ExperimentType;
import uk.ac.ebi.atlas.model.baseline.Factor;
import uk.ac.ebi.atlas.model.baseline.FactorGroup;
import uk.ac.ebi.atlas.model.baseline.impl.FactorSet;
import uk.ac.ebi.atlas.search.baseline.BaselineExperimentProfile;
import uk.ac.ebi.atlas.search.baseline.BaselineExperimentProfilesList;
import uk.ac.ebi.atlas.search.baseline.BaselineExperimentSearchResult;
import uk.ac.ebi.atlas.web.GeneQuery;

import javax.inject.Inject;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
public class BaselineAnalyticsSearchServiceIT {

    private static final FactorGroup EMPTY_FACTOR_SET = new FactorSet();

    public static final String ORGANISM_PART = "ORGANISM_PART";
    static Set<Factor> organismPartFactors = new HashSet<>();
    static {
        organismPartFactors.add(new Factor(ORGANISM_PART, "adipose tissue"));
        organismPartFactors.add(new Factor(ORGANISM_PART, "adrenal gland"));
        organismPartFactors.add(new Factor(ORGANISM_PART, "animal ovary"));
        organismPartFactors.add(new Factor(ORGANISM_PART, "appendix"));
        organismPartFactors.add(new Factor(ORGANISM_PART, "bladder"));
        organismPartFactors.add(new Factor(ORGANISM_PART, "bone marrow"));
        organismPartFactors.add(new Factor(ORGANISM_PART, "CD4-positive T cell"));
        organismPartFactors.add(new Factor(ORGANISM_PART, "CD8-positive T cell"));
        organismPartFactors.add(new Factor(ORGANISM_PART, "cerebral cortex"));
        organismPartFactors.add(new Factor(ORGANISM_PART, "colon"));
        organismPartFactors.add(new Factor(ORGANISM_PART, "duodenum"));
        organismPartFactors.add(new Factor(ORGANISM_PART, "endometrium"));
        organismPartFactors.add(new Factor(ORGANISM_PART, "esophagus"));
        organismPartFactors.add(new Factor(ORGANISM_PART, "fallopian tube"));
        organismPartFactors.add(new Factor(ORGANISM_PART, "gall bladder"));
        organismPartFactors.add(new Factor(ORGANISM_PART, "heart"));
        organismPartFactors.add(new Factor(ORGANISM_PART, "kidney"));
        organismPartFactors.add(new Factor(ORGANISM_PART, "liver"));
        organismPartFactors.add(new Factor(ORGANISM_PART, "lung"));
        organismPartFactors.add(new Factor(ORGANISM_PART, "lymph node"));
        organismPartFactors.add(new Factor(ORGANISM_PART, "pancreas"));
        organismPartFactors.add(new Factor(ORGANISM_PART, "placenta"));
        organismPartFactors.add(new Factor(ORGANISM_PART, "prostate"));
        organismPartFactors.add(new Factor(ORGANISM_PART, "rectum"));
        organismPartFactors.add(new Factor(ORGANISM_PART, "salivary gland"));
        organismPartFactors.add(new Factor(ORGANISM_PART, "skeletal muscle"));
        organismPartFactors.add(new Factor(ORGANISM_PART, "skin"));
        organismPartFactors.add(new Factor(ORGANISM_PART, "small intestine"));
        organismPartFactors.add(new Factor(ORGANISM_PART, "smooth muscle"));
        organismPartFactors.add(new Factor(ORGANISM_PART, "spleen"));
        organismPartFactors.add(new Factor(ORGANISM_PART, "stomach"));
        organismPartFactors.add(new Factor(ORGANISM_PART, "testis"));
        organismPartFactors.add(new Factor(ORGANISM_PART, "tonsil"));
        organismPartFactors.add(new Factor(ORGANISM_PART, "thyroid"));
    }

    private static final String CELL_LINE = "CELL_LINE";
    static Set<Factor> cellLineFactors = new HashSet<>();
    static {
        cellLineFactors.add(new Factor(CELL_LINE, "A549"));
        cellLineFactors.add(new Factor(CELL_LINE, "AG445"));
        cellLineFactors.add(new Factor(CELL_LINE, "BJ"));
        cellLineFactors.add(new Factor(CELL_LINE, "CD20-positive B cell cell line"));
        cellLineFactors.add(new Factor(CELL_LINE, "CD34-positive mobilized cell cell line"));
        cellLineFactors.add(new Factor(CELL_LINE, "GM12878"));
        cellLineFactors.add(new Factor(CELL_LINE, "H1-hESC"));
        cellLineFactors.add(new Factor(CELL_LINE, "HFDPC cell line"));
        cellLineFactors.add(new Factor(CELL_LINE, "HMEC cell line"));
        cellLineFactors.add(new Factor(CELL_LINE, "HPC-PL cell line"));
        cellLineFactors.add(new Factor(CELL_LINE, "HSMM cell line"));
        cellLineFactors.add(new Factor(CELL_LINE, "HUVEC cell line"));
        cellLineFactors.add(new Factor(CELL_LINE, "HeLa-S3"));
        cellLineFactors.add(new Factor(CELL_LINE, "HepG2"));
        cellLineFactors.add(new Factor(CELL_LINE, "hMSC-AT cell line"));
        cellLineFactors.add(new Factor(CELL_LINE, "IMR-90"));
        cellLineFactors.add(new Factor(CELL_LINE, "K562"));
        cellLineFactors.add(new Factor(CELL_LINE, "MCF-7"));
        cellLineFactors.add(new Factor(CELL_LINE, "NHEK cell line"));
        cellLineFactors.add(new Factor(CELL_LINE, "NHLF cell line"));
        cellLineFactors.add(new Factor(CELL_LINE, "SK-N-SH"));
        cellLineFactors.add(new Factor(CELL_LINE, "SK-N-SH_RA"));
    }



    @Inject
    private BaselineAnalyticsSearchService subject;

    @Test
    public void singleSpeciesGeneAccessionTissues() {
        //"ENSG00000006062" seems to be a famous enough gene
        BaselineExperimentSearchResult result = subject.findExpressions(GeneQuery.create("ENSG00000006062"), "Homo sapiens", "ORGANISM_PART");

        BaselineExperimentProfilesList baselineProfilesList = result.getExperimentProfiles();

        assertTrue(baselineProfilesList.size() > 0);
        for (BaselineExperimentProfile profile : baselineProfilesList) {
            assertThat(profile.getId(), not(isEmptyOrNullString()));
            assertThat(profile.getName(), not(isEmptyOrNullString()));
            assertThat(profile.getShortName(), not(isEmptyOrNullString()));
            assertTrue(ExperimentType.get(profile.getExperimentType()).isBaseline());
            assertTrue(profile.getMinExpressionLevel()< profile.getMaxExpressionLevel());
            assertThat(profile.getFilterFactors(), is(EMPTY_FACTOR_SET));
            assertThat(profile.getConditions().size(), greaterThan(4));
            assertTrue(profile.isExpressedOnAnyOf(organismPartFactors));
            assertFalse(profile.isExpressedOnAnyOf(cellLineFactors));


        }
    }

    @Test
    public void geneQueryCellLine() {
        BaselineExperimentSearchResult result = subject.findExpressions(
                GeneQuery.create("protein_coding"), "homo sapiens",
                CELL_LINE);

        BaselineExperimentProfilesList baselineProfilesList = result.getExperimentProfiles();

        assertThat(baselineProfilesList.size(), greaterThan(0));
        for(BaselineExperimentProfile profile : baselineProfilesList) {
            assertThat(profile.getFilterFactors(), is(EMPTY_FACTOR_SET));
            assertTrue(profile.getMinExpressionLevel()< profile.getMaxExpressionLevel());
            assertFalse(profile.isExpressedOnAnyOf(organismPartFactors));
            assertTrue(profile.isExpressedOnAnyOf(cellLineFactors));
        }
    }

}