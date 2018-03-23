package uk.ac.ebi.atlas.commons.readers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class ArrayExpressIdfStreamerFactoryEIT {
    private static final String E_MTAB_513 = "E-MTAB-513";
    private static final String E_FOOBAR = "E-FOOBAR";

    private ArrayExpressIdfStreamerFactory subject;

    @Before
    public void setUp() {
        subject = new ArrayExpressIdfStreamerFactory();
    }

    @Test
    public void build() throws Exception {
        TsvStreamer idfStreamer = subject.createArrayExpressIdfReader(E_MTAB_513);
        assertThat(idfStreamer).isInstanceOf(TsvStreamer.class);
    }

    @Test(expected = IOException.class)
    public void buildMissingExperiment() throws Exception {
        subject.createArrayExpressIdfReader(E_FOOBAR);
    }
}