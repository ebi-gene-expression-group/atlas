package uk.ac.ebi.atlas.utils;

import com.google.common.collect.ImmutableSet;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;
import uk.ac.ebi.atlas.experimentimport.condensedSdrf.IdfParser;

import javax.inject.Inject;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/applicationContext.xml", "/solrContext.xml", "/embeddedSolrServerContext.xml", "/dbContext.xml"})
public class ArrayExpressClientTestEIT {

    private final static String E_MTAB_513 = "E-MTAB-513";
    private final static String E_MTAB_513_NAME = "RNA-Seq of human individual tissues and mixture of 16 tissues (Illumina Body Map)";
    private final static String E_MTAB_1929 = "E-MTAB-1929";
    private final static String[] E_MTAB_1929_NAME =
            {"Transcription profiling by high throughput sequencing of Insm1+/− and Insm1−/− endocrine progenitor " +
                    "cell populations at embryonic day 15.5",
                    "Transcriptional regulation of endocrine cell differentiation by Insm1"};
    private final static String TEST_RNASEQ_BASELINE = "TEST-RNASEQ-BASELINE";

    @Inject
    private ArrayExpressClient subject;

    @Test
    public void fetchExperimentName() {
        String result = subject.fetchExperimentName(E_MTAB_513);
        assertThat(result, is(E_MTAB_513_NAME));
    }

    @Test
    public void multipleExperimentNames() {
        String experimentName = subject.fetchExperimentName(E_MTAB_1929);
        assertThat(experimentName, is(E_MTAB_1929_NAME[0]));
    }

    @Test
    public void noExperimentNameUsesIdfAsFallback() {
        String experimentName = subject.fetchExperimentName(TEST_RNASEQ_BASELINE);
        assertThat(experimentName, is(E_MTAB_513_NAME));
    }

    @Test(expected = RuntimeException.class)
    public void throwIfIdfParserReturnsAnEmptyName() throws Exception {
        IdfParser idfParser = mock(IdfParser.class);
        when(idfParser.parse("foobar")).thenReturn(ImmutablePair.of("", ImmutableSet.of()));

        subject = new ArrayExpressClient(new RestTemplate(), idfParser);

        subject.fetchExperimentName("foobar");
    }

}
