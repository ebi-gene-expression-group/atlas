package uk.ac.ebi.atlas.commons.readers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uk.ac.ebi.atlas.commons.readers.impl.TsvReaderImpl;

import java.io.IOException;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class ArrayExpressIdfReaderFactoryEIT {
    private static final String E_MTAB_513 = "E-MTAB-513";
    private static final String E_FOOBAR = "E-FOOBAR";

    private ArrayExpressIdfReaderFactory subject;

    @Before
    public void setUp() {
        subject = new ArrayExpressIdfReaderFactory();
    }

    @Test
    public void build() throws Exception {
        TsvReader idfReader = subject.createArrayExpressIdfReader(E_MTAB_513);
        assertThat(idfReader, is(instanceOf(TsvReaderImpl.class)));
    }

    @Test(expected = IOException.class)
    public void buildMissingExperiment() throws Exception {
        subject.createArrayExpressIdfReader(E_FOOBAR);
    }
}