package uk.ac.ebi.atlas.acceptance.magetab;

import org.junit.Before;
import org.junit.Test;
import uk.ac.ebi.arrayexpress2.magetab.exception.ParseException;
import uk.ac.ebi.arrayexpress2.magetab.parser.MAGETABParser;
import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.services.AtlasMageTabParser;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class MageTabParserIT {

    private static final String EXPERIMENT_ACCESSION = "E-MTAB-513";

    private AtlasMageTabParser subject;

    private InputStream idfFileinputStream;

    @Before
    public void initSubject() throws IOException, ParseException {
        URL resource = MageTabParserIT.class.getResource("E-MTAB-513.idf.txt");
        idfFileinputStream = resource.openStream();

        MAGETABParser mageTabParser = new MAGETABParser();
        subject = new AtlasMageTabParser(mageTabParser);
        subject.parse(idfFileinputStream);
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

}
