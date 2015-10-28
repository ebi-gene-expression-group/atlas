package uk.ac.ebi.atlas.bioentity.geneset;

import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.BaselineBioEntitiesSearchResult;
import uk.ac.ebi.atlas.acceptance.selenium.pages.BioEntitiesPage;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class GeneSetPageControllerMultiSpeciesGeneSetBaselineCountsSIT extends SeleniumFixture {

    private static final String GENESET = "IPR027417";

    private BioEntitiesPage subject;

    @Override
    protected void getStartingPage() {
        subject = new BioEntitiesPage(driver, "genesets/" + GENESET);
        subject.get();
    }

    @Test
    public void baselineResults() {
        subject.clickMoreBaselineResults();
        assertThat(subject.getBaselinePaneHeaderResultsMessage(), is("155 results"));

        List<BaselineBioEntitiesSearchResult> baselineCounts = subject.getAllBaselineResults();

        assertThat(baselineCounts, hasSize(155));

        BaselineBioEntitiesSearchResult result0 = baselineCounts.get(10);
        assertThat(result0.getExperimentAccession(), is("dummy-E-MTAB-2706"));
        assertThat(result0.getExperimentName(), is("Cell Lines - 675 Genentech - B-cell lymphoma, lymph node"));
        assertThat(result0.getSpecies(), is("Homo sapiens"));
        assertThat(result0.getHref(), endsWith("dummy-E-MTAB-2706?_specific=on&queryFactorType=CELL_LINE&queryFactorValues=&geneQuery=IPR027417&exactMatch=true&serializedFilterFactors=DISEASE:B-cell%20lymphoma,ORGANISM_PART:lymph%20node"));

        BaselineBioEntitiesSearchResult result1 = baselineCounts.get(121);
        assertThat(result1.getExperimentAccession(), is("E-GEOD-26284"));
        assertThat(result1.getExperimentName(), is("ENCODE cell lines - long non-polyA RNA, nucleus"));
        assertThat(result1.getSpecies(), is("Homo sapiens"));
        assertThat(result1.getHref(), endsWith("E-GEOD-26284?_specific=on&queryFactorType=CELL_LINE&queryFactorValues=&geneQuery=IPR027417&exactMatch=true&serializedFilterFactors=RNA:long%20non-polyA%20RNA,CELLULAR_COMPONENT:nucleus"));

        BaselineBioEntitiesSearchResult result2 = baselineCounts.get(131);
        assertThat(result2.getExperimentAccession(), is("E-PROT-1"));
        assertThat(result2.getExperimentName(), is("Human Proteome Map - fetus"));
        assertThat(result2.getSpecies(), is("Homo sapiens"));
        assertThat(result2.getHref(), endsWith("E-PROT-1?_specific=on&queryFactorType=ORGANISM_PART&queryFactorValues=&geneQuery=IPR027417&exactMatch=true&serializedFilterFactors=DEVELOPMENTAL_STAGE:fetus"));

        BaselineBioEntitiesSearchResult result3 = baselineCounts.get(141);
        assertThat(result3.getExperimentAccession(), is("E-MTAB-2980"));
        assertThat(result3.getExperimentName(), is("NCI-60 cancer cell lines - prostate adenocarcinoma"));
        assertThat(result3.getSpecies(), is("Homo sapiens"));
        assertThat(result3.getHref(), endsWith("E-MTAB-2980?_specific=on&queryFactorType=CELL_LINE&queryFactorValues=&geneQuery=IPR027417&exactMatch=true&serializedFilterFactors=DISEASE:prostate%20adenocarcinoma"));
    }

}
