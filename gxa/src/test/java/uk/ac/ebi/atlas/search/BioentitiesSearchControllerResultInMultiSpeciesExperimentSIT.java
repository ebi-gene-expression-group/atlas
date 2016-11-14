
package uk.ac.ebi.atlas.search;

import uk.ac.ebi.atlas.acceptance.selenium.fixture.SinglePageSeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.BaselineBioEntitiesSearchResult;
import uk.ac.ebi.atlas.acceptance.selenium.pages.BioEntitiesPage;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class BioentitiesSearchControllerResultInMultiSpeciesExperimentSIT extends SinglePageSeleniumFixture {

    private BioEntitiesPage subject;

    @Override
    protected void getStartingPage() {
        subject = BioEntitiesPage.search(driver, "geneQuery=tex33");
        subject.get();
    }


    @Test
    public void checkBaselineExperimentCounts() {
        List<BaselineBioEntitiesSearchResult> baselineCounts = subject.getAllBaselineResults();

        assertThat(baselineCounts, hasSize(4));

        assertThat(baselineCounts.get(2).getExperimentAccession(), is("E-GEOD-30352"));
        assertThat(baselineCounts.get(2).getExperimentName(), is("Vertebrate tissues"));
        assertThat(baselineCounts.get(2).getSpecies(), is("Homo sapiens"));
        assertThat(baselineCounts.get(2).getHref(), endsWith("E-GEOD-30352?_specific=on&queryFactorType=ORGANISM_PART&queryFactorValues=&geneQuery=tex33&serializedFilterFactors=ORGANISM:Homo%20sapiens"));


        assertThat(baselineCounts.get(3).getExperimentAccession(), is("E-GEOD-30352"));
        assertThat(baselineCounts.get(3).getExperimentName(), is("Vertebrate tissues"));
        assertThat(baselineCounts.get(3).getSpecies(), is("Mus musculus"));
        assertThat(baselineCounts.get(3).getHref(), endsWith("E-GEOD-30352?_specific=on&queryFactorType=ORGANISM_PART&queryFactorValues=&geneQuery=tex33&serializedFilterFactors=ORGANISM:Mus%20musculus"));
    }

}
