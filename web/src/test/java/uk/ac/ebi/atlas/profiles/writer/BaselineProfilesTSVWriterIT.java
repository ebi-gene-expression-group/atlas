
package uk.ac.ebi.atlas.profiles.writer;

import com.google.common.collect.Sets;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.experimentpage.context.BaselineRequestContext;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.trader.cache.RnaSeqBaselineExperimentsCache;
import uk.ac.ebi.atlas.web.BaselineRequestPreferences;

import javax.inject.Inject;

import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
public class BaselineProfilesTSVWriterIT {

    private static final String EXPERIMENT_ACCESSION = "E-MTAB-513";

    @Inject
    private BaselineProfilesTSVWriter subject;

    @Inject
    private RnaSeqBaselineExperimentsCache rnaSeqBaselineExperimentsCache;

    private BaselineRequestPreferences requestPreferences;

    private BaselineRequestContext requestContext;

    @Before
    public void setUp() throws Exception {
        requestPreferences = new BaselineRequestPreferences();
        requestPreferences.setQueryFactorType("ORGANISM_PART");
        BaselineExperiment baselineExperiment = rnaSeqBaselineExperimentsCache.getExperiment(EXPERIMENT_ACCESSION);
        requestContext = BaselineRequestContext.createFor(baselineExperiment,requestPreferences);
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
        assertThat(headerRows[1], is("# Query: Genes matching: 'protein_coding', specifically expressed in any Organism part above the expression level cutoff: 0.5 in experiment " + EXPERIMENT_ACCESSION));
    }

    @Test
    public void secondHeaderLineShouldIncludeBitsOfTheQuery() throws ExecutionException {
        BaselineExperiment experiment = rnaSeqBaselineExperimentsCache.getExperiment(EXPERIMENT_ACCESSION);

        String organismPart = "brain";

        requestPreferences.setQueryFactorType("ORGANISM_PART");
        requestPreferences.setQueryFactorValues(Sets.newTreeSet(Sets.newHashSet(organismPart)));

        BaselineRequestContext requestContext = BaselineRequestContext.createFor(experiment,requestPreferences);

        String[] headerRows = subject.getTsvFileMasthead(requestContext, false).split("\n");

        assertTrue(Pattern.matches("# Query: Genes.*expressed.*Organism part.*", headerRows[1]));
        assertTrue(headerRows[1].contains(organismPart));
    }

}
