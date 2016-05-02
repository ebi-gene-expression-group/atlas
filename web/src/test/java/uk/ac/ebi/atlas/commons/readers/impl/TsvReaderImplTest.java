package uk.ac.ebi.atlas.commons.readers.impl;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isA;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.containsString;

public class TsvReaderImplTest {

    private static final String COMMENT_LINE = "#Comment\tComment";
    private static final String HEADER_LINE = "Column1\tColumn2\tColumn3";
    private static final String VALUES_LINE = "Data1\tData2\tData3";

    private static final String[] COMMENT_LINE_AS_ARRAY = COMMENT_LINE.split("\t");
    private static final String[] HEADER_LINE_AS_ARRAY = HEADER_LINE.split("\t");
    private static final String[] VALUES_LINE_AS_ARRAY = VALUES_LINE.split("\t");

    private static final String DATA =
            COMMENT_LINE + "\n" +
            HEADER_LINE + "\n" +
            VALUES_LINE + "\n";

    private TsvReaderImpl subject;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() {
        InputStreamReader tsvInputStreamReader = new InputStreamReader(new ByteArrayInputStream(DATA.getBytes()));
        subject = new TsvReaderImpl(tsvInputStreamReader);
    }

    @Test
    public void readFirstLine() {
        assertThat(subject.readLine(0), is(COMMENT_LINE_AS_ARRAY));
    }

    @Test
    public void readSecondLine() {
        assertThat(subject.readLine(1), is(HEADER_LINE_AS_ARRAY));
    }

    @Test
    public void readThirdLine() {
        assertThat(subject.readLine(2), is(VALUES_LINE_AS_ARRAY));
    }

    @Test
    public void readLineBeyondEndOfStreamReturnsNull() {
        assertThat(subject.readLine(5), is(nullValue()));
    }

    @Test
    public void underlyingReaderIsClosedAfterReading() {
        thrown.expect(RuntimeException.class);
        thrown.expectCause(isA(IOException.class));
        thrown.expectMessage(containsString("Stream closed"));

        subject.readLine(0);
        subject.readLine(0);
    }

    @Test
    public void readAllIgnoresComments() {
        List<String[]> lines = subject.readAll();

        assertThat(lines.size(), is(2));
        assertThat(lines.get(0), is(HEADER_LINE_AS_ARRAY));
        assertThat(lines.get(1), is(VALUES_LINE_AS_ARRAY));
    }

}
