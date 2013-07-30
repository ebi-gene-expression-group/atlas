package uk.ac.ebi.atlas.solr.index;

import au.com.bytecode.opencsv.CSVReader;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class PropertyStreamTest {

    private PropertyStream subject;

    @Mock
    private CSVReader csvReaderMock;

    @Before
    public void setUp() throws Exception {
        subject = new PropertyStream(csvReaderMock);
    }

    @Test
    public void testNext() throws Exception {

        subject.next();
        verify(csvReaderMock.readNext());

    }
}
