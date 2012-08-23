package uk.ac.ebi.atlas.acceptance.magetab;

import org.junit.Before;
import org.junit.Test;
import uk.ac.ebi.arrayexpress2.magetab.exception.ParseException;
import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.services.MageTabParser;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class MageTabParserTest {

    private static final String EXPERIMENT_ACCESSION = "E-MTAB-513";

    private MageTabParser subject = new MageTabParser();

    private InputStream idfFileinputStream;

    @Before
    public void getInputStream() throws IOException{
        URL resource = MageTabParserTest.class.getResource("E-MTAB-513.idf.txt");
        idfFileinputStream = resource.openStream();
    }

    @Test
    public void parseMethodShouldPartiallyBuildAnExperiment() throws ParseException {


        //given
        Experiment experiment = subject.parse(idfFileinputStream);

        //then
        assertThat(experiment, is(notNullValue()));
        //and
        assertThat(experiment.getAccession(), is(EXPERIMENT_ACCESSION));

    }

}
