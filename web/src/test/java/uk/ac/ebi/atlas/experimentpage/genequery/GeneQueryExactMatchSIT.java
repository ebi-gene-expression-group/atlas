
package uk.ac.ebi.atlas.experimentpage.genequery;


import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.HeatmapTableWithSearchFormPage;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class GeneQueryExactMatchSIT extends SeleniumFixture {

    private static final String E_MTAB_513_HTTP_PARAMETERS_WITH_EXACT_MATCH = "geneQuery=binding%09%22mRNA%20splicing,%20via%20spliceosome%22&_queryFactorValues=1&specific=true&_specific=on&cutoff=0.5";
    private static final String E_MTAB_599_HTTP_PARAMETERS_WITH_EXACT_MATCH = "geneQuery=%22mitochondrially+encoded+ATP+synthase+8%22&exactMatch=true&queryFactorType=ORGANISM_PART&heatmapMatrixSize=50&displayLevels=true&displayGeneDistribution=false&queryFactorValues=liver&_queryFactorValues=1&specific=true&cutoff=0.5";
    private static final String E_MTAB_599_HTTP_PARAMETERS_WITH_EXACT_MATCH_WITH_UNQUOTED_TERMS = "geneQuery=mitochondrially+encoded+ATP+8&exactMatch=true&queryFactorType=ORGANISM_PART&heatmapMatrixSize=50&displayLevels=true&displayGeneDistribution=false&queryFactorValues=liver&_queryFactorValues=1&specific=true&cutoff=0.5";

    protected HeatmapTableWithSearchFormPage subject;

    @Test
    public void shouldHaveExactMatchCheckboxSelected() {
        //given
        subject = new HeatmapTableWithSearchFormPage(driver, "E-MTAB-513", E_MTAB_513_HTTP_PARAMETERS_WITH_EXACT_MATCH);
        //when
        subject.get();
        //then
        assertThat(subject.isExactMatch(), is(true));

    }

    @Test
    public void shouldReturnOnlyGenesWithPropertyValuesExactlyMatchingToAtLeastOneOfTheProvidedGeneQueryTerms() {
        //given
        subject = new HeatmapTableWithSearchFormPage(driver, "E-MTAB-513", E_MTAB_513_HTTP_PARAMETERS_WITH_EXACT_MATCH);
        //when
        subject.get();

        //then
        assertThat(subject.getGeneNames(), contains("SRSF2", "RTDR1", "RANBP17", "POLR2B", "SNRPA"));

    }


    @Test
    public void shouldReturnOnlyOneGene() {
        //given
        subject = new HeatmapTableWithSearchFormPage(driver, "E-MTAB-599", E_MTAB_599_HTTP_PARAMETERS_WITH_EXACT_MATCH);
        //when
        subject.get();

        //then
        assertThat(subject.getGeneNames(), contains("mt-Atp8"));

    }

    @Test
    public void shouldReturnNoGene() {
        //given
        subject = new HeatmapTableWithSearchFormPage(driver, "E-MTAB-599", E_MTAB_599_HTTP_PARAMETERS_WITH_EXACT_MATCH_WITH_UNQUOTED_TERMS);
        //when
        subject.get();

        //then
        subject.getPreferencesErrors();
    }

}
