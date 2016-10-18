
package uk.ac.ebi.atlas.experimentpage.heatmap.genesetmatch;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.HeatmapTableWithSearchFormPageAsGeneSets;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.junit.Assert.assertThat;

public class GeneSetExpressionLevelsSIT extends SeleniumFixture {

    private static final String E_E_GEOD_30352_ACCESSION = "E-GEOD-30352";
    protected HeatmapTableWithSearchFormPageAsGeneSets subject;

    @Before
    public void initSubject() {
    }

    @Test
    public void shouldHaveAGeneHeader() {
        String geneSetMatchQuery = "serializedFilterFactors=ORGANISM%3AHomo+sapiens&queryFactorType=ORGANISM_PART&heatmapMatrixSize=50" +
                "&displayLevels=true&displayGeneDistribution=true&geneQuery=%22Alpha-1-acid+glycoprotein%22&geneSetMatch=true" +
                "&_geneSetMatch=on&_queryFactorValues=1&specific=true&_specific=on&cutoff=0.5";
        //given
        subject = new HeatmapTableWithSearchFormPageAsGeneSets(driver, E_E_GEOD_30352_ACCESSION, geneSetMatchQuery);
        //when
        subject.get();
        subject.clickShowGeneSetProfiles();
        //then

        assertThat(subject.getGeneColumnHeader(), is("Gene set"));
    }

    @Test
    public void shouldFindExpressionLevelsForTermInDoubleQuotes() {
        String geneSetMatchQuery = "serializedFilterFactors=ORGANISM%3AHomo+sapiens&queryFactorType=ORGANISM_PART&heatmapMatrixSize=50" +
                "&displayLevels=true&displayGeneDistribution=true&geneQuery=%22Alpha-1-acid+glycoprotein%22&geneSetMatch=true" +
                "&_geneSetMatch=on&_queryFactorValues=1&specific=true&_specific=on&cutoff=0.5";
        //given
        subject = new HeatmapTableWithSearchFormPageAsGeneSets(driver, E_E_GEOD_30352_ACCESSION, geneSetMatchQuery);
        //when
        subject.get();
        subject.clickShowGeneSetProfiles();
        //then
        assertThat(subject.getGeneNames(), contains("\"Alpha-1-acid glycoprotein\""));
        assertThat(subject.getFirstGeneProfile(), contains("", "", "0.5", "0.5", "6451", "", "", "3" ));

    }

    @Test
    @Ignore
    public void shouldFindExpressionLevelsForReactomeTerm() { //This react_1619 does not exist anymore
        String geneSetMatchQuery = "serializedFilterFactors=ORGANISM%3AHomo+sapiens&queryFactorType=ORGANISM_PART&heatmapMatrixSize=50&" +
                "displayLevels=true&displayGeneDistribution=true&geneQuery=react_1619" +
                "&geneSetMatch=true&_geneSetMatch=on&_queryFactorValues=1&specific=true&_specific=on&cutoff=0.5";
        //given
        subject = new HeatmapTableWithSearchFormPageAsGeneSets(driver, E_E_GEOD_30352_ACCESSION, geneSetMatchQuery);
        //when
        subject.get();
        subject.clickShowGeneSetProfiles();
        //then
        assertThat(subject.getGeneNames(), contains("react_1619"));
        assertThat(subject.getFirstGeneProfile(), contains("4", "4", "12", "22", "17", "4", "6", "14"));

    }

    @Test
    public void shouldFindExpressionLevelsForMultipleTerms() {
        String geneSetMatchQuery = "serializedFilterFactors=ORGANISM%3AHomo+sapiens&queryFactorType=ORGANISM_PART&heatmapMatrixSize=50" +
                "&displayLevels=true&displayGeneDistribution=true&geneQuery=%22Alpha-1-acid+glycoprotein%22+react_1619" +
                "&geneSetMatch=true&_geneSetMatch=on&_queryFactorValues=1&specific=true&_specific=on&cutoff=0.5";
        //given
        subject = new HeatmapTableWithSearchFormPageAsGeneSets(driver, E_E_GEOD_30352_ACCESSION, geneSetMatchQuery);
        //when
        subject.get();
        subject.clickShowGeneSetProfiles();
        //then
        assertThat(subject.getGeneNames(), contains("\"Alpha-1-acid glycoprotein\""));
        assertThat(subject.getFirstGeneProfile(), contains("", "", "0.5", "0.5", "6451", "", "", "3" ));
    }

    @Test
    public void shouldDisplayNumberOfGeneSetsFound(){
        String geneSetMatchQuery = "serializedFilterFactors=ORGANISM%3AHomo+sapiens&queryFactorType=ORGANISM_PART&heatmapMatrixSize=50" +
                "&displayLevels=true&displayGeneDistribution=true&geneQuery=%22Alpha-1-acid+glycoprotein%22+react_1619" +
                "&geneSetMatch=true&_geneSetMatch=on&_queryFactorValues=1&specific=true&_specific=on&cutoff=0.5";
        //given
        subject = new HeatmapTableWithSearchFormPageAsGeneSets(driver, E_E_GEOD_30352_ACCESSION, geneSetMatchQuery);
        //when
        subject.get();
        subject.clickShowGeneSetProfiles();
        String geneCountMessage = subject.getGeneCount();
        //then
        assertThat(geneCountMessage, is("Showing 1 of 1 gene sets found:"));
    }

}
