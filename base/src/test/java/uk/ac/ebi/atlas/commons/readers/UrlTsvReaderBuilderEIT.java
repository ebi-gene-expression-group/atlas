package uk.ac.ebi.atlas.commons.readers;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uk.ac.ebi.atlas.commons.readers.impl.TsvReaderImpl;

import java.io.IOException;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/test-applicationContext.xml", "/test-solrContext.xml", "/test-oracleContext.xml"})
public class UrlTsvReaderBuilderEIT {

    private static final String E_MTAB_513 = "E-MTAB-513";
    private static final String E_FOOBAR = "E-FOOBAR";

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
        TsvReader idfReader = subject.withExperimentAccession(E_MTAB_513).build();
        assertThat(idfReader, is(instanceOf(TsvReaderImpl.class)));
    }

    @Test
    public void buildMissingExperiment() throws Exception {
        thrown.expect(IOException.class);

        subject.withExperimentAccession(E_FOOBAR).build();
    }

}