
package uk.ac.ebi.atlas.search;

import uk.ac.ebi.atlas.acceptance.selenium.fixture.SeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.BioEntitiesPage;
import org.hamcrest.MatcherAssert;
import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.pages.BaselineBioEntitiesSearchResult;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class BioentitiesSearchControllerConditionQuery2TermsBaselineSIT extends SeleniumFixture {

    @Test
    public void adiposeOrThymus() {
        BioEntitiesPage subject = BioEntitiesPage.search(driver, "condition=adipose%09thymus");
        subject.get();
        List<BaselineBioEntitiesSearchResult> baselineCounts = subject.getAllBaselineResults();

        assertThat(baselineCounts, hasSize(5));

        BaselineBioEntitiesSearchResult result1 = SearchTestUtil.selectResult(baselineCounts, "E-GEOD-26284");
        assertThat(result1.getExperimentName(), is("ENCODE cell lines - total RNA, whole cell"));
        assertThat(result1.getSpecies(), is("Homo sapiens"));
        assertThat(result1.getHref(), endsWith("E-GEOD-26284?_specific=on&queryFactorType=CELL_LINE&queryFactorValues=hMSC-AT%20cell%20line&geneQuery=&serializedFilterFactors=RNA:total%20RNA,CELLULAR_COMPONENT:whole%20cell"));

        BaselineBioEntitiesSearchResult result2 = SearchTestUtil.selectResult(baselineCounts, "E-MTAB-1733");
        assertThat(result2.getExperimentName(), is("Twenty seven tissues"));
        assertThat(result2.getSpecies(), is("Homo sapiens"));
        assertThat(result2.getHref(), endsWith("E-MTAB-1733?_specific=on&queryFactorType=ORGANISM_PART&queryFactorValues=adipose%20tissue&geneQuery="));
    }

    @Test
    public void searchFullPhraseAndNotIndividualWords() {
        BioEntitiesPage subject = BioEntitiesPage.search(driver, "condition=whole+post-emergence+inflorescence");
        subject.get();
        List<BaselineBioEntitiesSearchResult> baselineCounts = subject.getAllBaselineResults();

        //should not contain experiments with the world "whole", eg: E-GEOD-26284 (Homo sapiens - ENCODE cell lines - long non-polyA RNA, whole cell)
        MatcherAssert.assertThat(SearchTestUtil.hasResult(baselineCounts, "E-GEOD-26284"), is(false));
        assertThat(baselineCounts, hasSize(1));

        BaselineBioEntitiesSearchResult result1 = SearchTestUtil.selectResult(baselineCounts, "E-MTAB-2039");
        assertThat(result1.getExperimentName(), is("Tissues - 9 Davidson"));
        assertThat(result1.getSpecies(), is("Oryza sativa Japonica Group"));
        assertThat(result1.getHref(), endsWith("E-MTAB-2039?_specific=on&queryFactorType=ORGANISM_PART&queryFactorValues=emerging%20inflorescence&geneQuery="));
    }

}
