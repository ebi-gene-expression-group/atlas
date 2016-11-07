package uk.ac.ebi.atlas.commons.readers;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.commons.readers.impl.TsvReaderDummy;
import uk.ac.ebi.atlas.commons.readers.impl.TsvReaderImpl;

import java.nio.file.NoSuchFileException;


import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isA;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContext.xml", "classpath:oracleContext.xml"})
public class FileTsvReaderBuilderIT {

    private static final String E_MTAB_513 = "E-MTAB-513";
    private static final String E_FOOBAR = "E-FOOBAR";

    @Value("#{configuration['experiment.analysis-method.path.template']}")
    private String analysisMethodsTemplate;

    private FileTsvReaderBuilder subject;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() {
        subject = new FileTsvReaderBuilder().forTsvFilePathTemplate(analysisMethodsTemplate);
    }

    @Test
    public void buildExistingFile() {
        TsvReader tsvReader = subject.withExperimentAccession(E_MTAB_513).build();
        assertThat(tsvReader, is(instanceOf(TsvReaderImpl.class)));
    }

    @Test
    public void buildMissingFile() {
        thrown.expect(RuntimeException.class);
        thrown.expectCause(isA(NoSuchFileException.class));

        subject.withExperimentAccession(E_FOOBAR).build();
    }

    @Test
    public void returnDummyIfFileMissing() {
        TsvReader tsvReader = subject.withExperimentAccession(E_FOOBAR).returnDummyIfFileMissing().build();

        assertThat(tsvReader, is(instanceOf(TsvReaderDummy.class)));
    }

}