package uk.ac.ebi.atlas.services;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.IDF;
import uk.ac.ebi.arrayexpress2.magetab.parser.IDFParser;
import uk.ac.ebi.atlas.model.Experiment;

import java.io.InputStream;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class MageTabParserTest {


    private MageTabParser subject;

    @Mock
    private InputStream inputStreamMock;

    @Mock
    private IDFParser idfParserMock;

    @Mock
    private IDF idfMock;

    @Before
    public void initMocks() throws Exception {
        when(idfParserMock.parse(inputStreamMock)).thenReturn(idfMock);
    }


    @Before
    public void initSubject() throws Exception {
        subject = new MageTabParser(idfParserMock);
    }

    @Test
    public void testParse() throws Exception {
        //given
        Experiment experiment = subject.parse(inputStreamMock);

        //then
        verify(idfParserMock).parse(inputStreamMock);
    }
}
