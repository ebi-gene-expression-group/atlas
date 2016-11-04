package uk.ac.ebi.atlas.profiles.writer;

import uk.ac.ebi.atlas.utils.RegexMatcher;
import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.experimentpage.context.RnaSeqRequestContext;
import uk.ac.ebi.atlas.experimentpage.context.RnaSeqRequestContextBuilder;
import uk.ac.ebi.atlas.model.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.trader.cache.RnaSeqDiffExperimentsCache;
import uk.ac.ebi.atlas.web.DifferentialRequestPreferences;

import javax.inject.Inject;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContext.xml", "classpath:oracleContext.xml"})
public class RnaSeqProfilesTSVWriterIT {

    private static final String RNA_SEQ_EXPERIMENT_ACCESSION = "E-GEOD-22351";

    @Inject
    private RnaSeqProfilesTSVWriter subject;

    @Inject
    private RnaSeqDiffExperimentsCache rnaSeqDiffExperimentsCache;

    @Inject
    private RnaSeqRequestContextBuilder rnaSeqRequestContextBuilder;

    private DifferentialRequestPreferences requestPreferences = new DifferentialRequestPreferences();

    private DifferentialExperiment differentialExperiment;

    private RnaSeqRequestContext requestContext;

    @Before
    public void setUp() throws Exception {
        differentialExperiment = rnaSeqDiffExperimentsCache.getExperiment(RNA_SEQ_EXPERIMENT_ACCESSION);
        requestContext = rnaSeqRequestContextBuilder.forExperiment(differentialExperiment)
                .withPreferences(requestPreferences).build();
    }

    @Test
    public void headerTextShouldContainThreeRows(){
        String[] headerRows = subject.getTsvFileMasthead(requestContext, false).split("\n");

        assertThat(headerRows.length, is(3));
    }

    @Test
    public void thirdHeaderLineShouldDescribeTimestamp(){
        String[] headerRows = subject.getTsvFileMasthead(requestContext, false).split("\n");

        assertThat(headerRows[2], startsWith("# Timestamp: "));
        assertThat(headerRows[2].length(), greaterThan("# Timestamp: ".length()));

    }

    @Test
    public void firstHeaderLineShouldDescribeAtlasVersion(){
        String[] headerRows = subject.getTsvFileMasthead(requestContext, false).split("\n");

        assertThat(headerRows[0], startsWith("# Expression Atlas version: "));
        assertThat(headerRows[0].length(), greaterThan("# Expression Atlas version: ".length()));

    }

    @Test
    public void secondHeaderLineShouldDescribeQuery(){
        String[] headerRows = subject.getTsvFileMasthead(requestContext, false).split("\n");

        assertThat(headerRows[1], is("# Query: Genes matching: default query, specifically up/down differentially expressed in " +
                "any contrast given the p-value cutoff 0.05 and log2-fold change cutoff 1 in experiment "+RNA_SEQ_EXPERIMENT_ACCESSION));
    }

    @Test
    public void secondHeaderLineShouldDescribeQueryAlsoWhenSelectingContrasts(){
        requestPreferences.setQueryFactorValues(Sets.newTreeSet(Sets.newHashSet("g1_g2")));

        rnaSeqRequestContextBuilder.forExperiment(differentialExperiment)
                .withPreferences(requestPreferences).build();

        String[] headerRows = subject.getTsvFileMasthead(requestContext, false).split("\n");

        assertThat(headerRows[1], RegexMatcher.matches("# Query: Genes.*differentially.*"+RNA_SEQ_EXPERIMENT_ACCESSION));
        assertThat(headerRows[2], startsWith("# Timestamp: "));
    }

}
