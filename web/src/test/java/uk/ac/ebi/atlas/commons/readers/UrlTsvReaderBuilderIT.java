package uk.ac.ebi.atlas.commons.readers;

import org.apache.commons.exec.ExecuteException;
import org.apache.commons.io.IOExceptionWithCause;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.commons.readers.impl.TsvReaderImpl;

import java.io.IOException;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})

public class UrlTsvReaderBuilderIT {

    private static final String EMTAB513 = "E-MTAB-513";
    private static final String EFOOBAR = "E-FOOBAR";

    @Value("#{configuration['experiment.magetab.idf.url.template']}")
    private String arrayExpressUrlTemplate;

    private UrlTsvReaderBuilder subject;

    @Rule
    public ExpectedException thrown = ExpectedException.none();


    @Before
    public void setUp() {
        subject = new UrlTsvReaderBuilder().forTsvFileUrlTemplate(arrayExpressUrlTemplate);
    }

    @Test
    public void build() throws Exception {
        TsvReader idfReader = subject.withExperimentAccession(EMTAB513).build();
        assertThat(idfReader, is(instanceOf(TsvReaderImpl.class)));
    }

    @Test
    public void buildMissingExperiment() throws Exception {
        thrown.expect(IOException.class);

        subject.withExperimentAccession(EFOOBAR).build();
    }

}