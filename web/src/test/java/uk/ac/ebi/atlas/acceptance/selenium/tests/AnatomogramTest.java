package uk.ac.ebi.atlas.acceptance.selenium.tests;

import org.junit.Test;
import org.openqa.selenium.By;
import uk.ac.ebi.atlas.acceptance.selenium.pages.AtlasPage;
import uk.ac.ebi.atlas.acceptance.selenium.utils.SeleniumFixture;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class AnatomogramTest extends SeleniumFixture {

    AtlasPage subject;

    public void getStartingPage() {
    }

    private void initStartingPage(final String url) {
        subject = new AtlasPage(driver) {
            @Override
            protected String getPageURI() {
                return url;
            }
        };
        subject.get();
    }

    private boolean isAnotomogramElementFound() {
        return driver.findElements(By.id("anatomogram")).size() > 0;
    }

    @Test
    public void testAnotomogramIsThereForHomoSapiensExp() {
        initStartingPage("/gxa/experiments/E-GEOD-30352");
        assertThat(isAnotomogramElementFound(), is(true));
    }


    @Test
    public void testAnotomogramIsNotThereForMultiSpeciesExpForChicken() {
        initStartingPage("/gxa/experiments/E-GEOD-30352?serializedFilterFactors=ORGANISM:gallus+gallus&queryFactorType=ORGANISM_PART&geneQuery=");
        assertThat(isAnotomogramElementFound(), is(false));
    }

    @Test
    public void testAnotomogramIsThereForMultiSpeciesExpForHomoSapiens() {
        initStartingPage("/gxa/experiments/E-GEOD-30352?serializedFilterFactors=ORGANISM:homo+sapiens&queryFactorType=ORGANISM_PART&geneQuery=");
        assertThat(isAnotomogramElementFound(), is(true));
    }

    @Test
    public void testAnotomogramIsNotThereForMultiSpeciesExpForOrganismPartFiltering() {
        initStartingPage("/gxa/experiments/E-GEOD-30352?serializedFilterFactors=ORGANISM_PART:liver&queryFactorType=ORGANISM&geneQuery=");
        assertThat(isAnotomogramElementFound(), is(false));
    }
}
