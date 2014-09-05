package uk.ac.ebi.atlas.bioentity.geneset;

import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.BaselineBioEntitiesSearchResult;
import uk.ac.ebi.atlas.acceptance.selenium.pages.BioEntitiesPage;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class GeneSetPageControllerReactomeBaselineCountsSIT extends SeleniumFixture {

    private static final String GENESET = "REACT_1619";

    private BioEntitiesPage subject;

    @Override
    protected void getStartingPage() {
        subject = new BioEntitiesPage(driver, "genesets/" + GENESET);
        subject.get();
    }

    @Test
    public void baselineResults() {
        assertThat(subject.getBaselinePaneHeaderResultsMessage(), is("2 results"));

        List<BaselineBioEntitiesSearchResult> baselineCounts = subject.getBaselineCounts();

        assertThat(baselineCounts, hasSize(3)); //including geneset description

        assertThat(baselineCounts.get(1).getExperimentAccession(), is("E-MTAB-1733"));
        assertThat(baselineCounts.get(1).getExperimentName(), is("Twenty seven tissues"));
        assertThat(baselineCounts.get(1).getSpecies(), is("Homo sapiens"));
        assertThat(baselineCounts.get(1).getHref(), endsWith("E-MTAB-1733?_specific=on&queryFactorType=ORGANISM_PART&queryFactorValues=&geneQuery=REACT_1619&exactMatch=true"));

        assertThat(baselineCounts.get(2).getExperimentAccession(), is("E-GEOD-30352"));
        assertThat(baselineCounts.get(2).getExperimentName(), is("Vertebrate tissues - Homo sapiens"));
        assertThat(baselineCounts.get(2).getSpecies(), is("Multi-species"));
        assertThat(baselineCounts.get(2).getHref(), endsWith("E-GEOD-30352?_specific=on&queryFactorType=ORGANISM_PART&queryFactorValues=&geneQuery=REACT_1619&exactMatch=true&serializedFilterFactors=ORGANISM:Homo%20sapiens"));
    }

}
