package uk.ac.ebi.atlas.file;

import com.google.common.collect.ImmutableMap;
import org.junit.Test;

import java.io.StringReader;
import java.util.Iterator;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class TsvStreamingParserTest {

    @Test
    public void parseSingleLine() {
        String tsvContents = "id\tname\n1\tone";
        TsvStreamingParser subject = new TsvStreamingParser(new StringReader(tsvContents));

        for (ImmutableMap<String, String> line : subject) {
            assertThat(line.get("id"), is("1"));
            assertThat(line.get("name"), is("one"));
        }
    }

    @Test
    public void parseSingleLineWithTrailingNewLine() {
        String tsvContents = "id\tname\n1\tone\n";
        TsvStreamingParser subject = new TsvStreamingParser(new StringReader(tsvContents));

        for (ImmutableMap<String, String> line : subject) {
            assertThat(line.get("id"), is("1"));
            assertThat(line.get("name"), is("one"));
        }
    }

    @Test
    public void parseTwoLines() {
        String tsvContents = "id\tname\n1\tone\n2\ttwo";
        TsvStreamingParser subject = new TsvStreamingParser(new StringReader(tsvContents));

        Iterator<ImmutableMap<String,String>> iterator = subject.iterator();
        ImmutableMap<String, String> line1 = iterator.next();
        ImmutableMap<String, String> line2 = iterator.next();

        assertThat(line1.get("id"), is("1"));
        assertThat(line1.get("name"), is("one"));
        assertThat(line2.get("id"), is("2"));
        assertThat(line2.get("name"), is("two"));
    }


    @Test
    public void parseHeaderWithSpace() {
        String tsvContents = "1st col\t2nd col\n1\tone\n2\ttwo";
        TsvStreamingParser subject = new TsvStreamingParser(new StringReader(tsvContents));

        Iterator<ImmutableMap<String,String>> iterator = subject.iterator();
        ImmutableMap<String, String> line1 = iterator.next();
        ImmutableMap<String, String> line2 = iterator.next();

        assertThat(line1.get("1st col"), is("1"));
        assertThat(line1.get("2nd col"), is("one"));
        assertThat(line2.get("1st col"), is("2"));
        assertThat(line2.get("2nd col"), is("two"));
    }

}