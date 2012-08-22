package uk.ac.ebi.atlas.acceptance.magetab;

import org.junit.Test;
import uk.ac.ebi.arrayexpress2.magetab.exception.ParseException;
import uk.ac.ebi.atlas.services.MageTabParser;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class DataFileLoadingTest {

    private MageTabParser subject = new MageTabParser();

    @Test
    public void experimentFilesShouldExist() throws IOException,ParseException {
        URL resource = DataFileLoadingTest.class.getResource("E-MTAB-513.idf.txt");

        InputStream inputStream = resource.openStream();

        subject.parse(inputStream);


    }

}
