package uk.ac.ebi.atlas.acceptance.magetab;

import org.junit.Before;
import org.junit.Test;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.sdrf.node.ScanNode;
import uk.ac.ebi.arrayexpress2.magetab.exception.ParseException;
import uk.ac.ebi.arrayexpress2.magetab.parser.MAGETABParser;
import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.model.ExperimentRun;
import uk.ac.ebi.atlas.services.AtlasMageTabParser;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.hasItem;

public class MageTabParserIT {

    private static final String EXPERIMENT_ACCESSION = "E-MTAB-513";

    private AtlasMageTabParser subject;

    private InputStream idfFileinputStream;

    @Before
    public void initSubject() throws IOException, ParseException {
        URL resource = MageTabParserIT.class.getResource("E-MTAB-513.idf.txt");

        MAGETABParser mageTabParser = new MAGETABParser();
        subject = new AtlasMageTabParser(mageTabParser);
        subject.parse(resource);
    }

    @Test
    public void getExperimentTest() throws ParseException {
        //given
        Experiment experiment = subject.getExperiment();

        //then
        assertThat(experiment, is(notNullValue()));

        //and
        assertThat(experiment.getAccession(), is(EXPERIMENT_ACCESSION));


    }

    @Test
    public void testParseRunsReturnsMoreThanOneRun() {
        Map<String, ExperimentRun> experimentRunMap = subject.parseExperimentRuns();
        assertThat(experimentRunMap.size(), is(48));
    }

    @Test
    public void testSDRFParser() throws IOException, ParseException {
        URL resource = MageTabParserIT.class.getResource("E-MTAB-513.sdrf.txt");
        String[] sdrfHeaders = subject.getSDRFHeaders(resource.openStream());

        assertThat(sdrfHeaders, is(notNullValue()));
        assertThat(Arrays.asList(sdrfHeaders), hasItem("Factor Value[ORGANISMPART]"));
    }

    @Test
    public void testScanNodes() {
        Collection<ScanNode> scanNodes = subject.getScanNodes();
        System.out.println("scanNodes = " + scanNodes);
    }

    @Test
    public void testScanNode() {
        ScanNode scanNodes = subject.getScanNode("50bp_PE_mRNA_Seq_FCA_s_3_1_sequence.txt.gz");
    }
}
