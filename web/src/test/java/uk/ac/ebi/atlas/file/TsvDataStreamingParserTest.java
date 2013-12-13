package uk.ac.ebi.atlas.file;

import org.junit.Test;

import java.io.Reader;
import java.io.StringReader;
import java.util.Iterator;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.junit.Assert.assertThat;

public class TsvDataStreamingParserTest {

    @Test
    public void twoTsvLines() {
        StringBuilder tsvContents = new StringBuilder();
        tsvContents.append("Gene ID\tGene Name\tg1\tg2\tg3\tg4\tg5\n");
        tsvContents.append("ENSMUSG00000030105\tArl8b\t1\t2\t3\t4\t-0.00248510654802851\n");
        tsvContents.append("ENSG00000127720\tMETTL25\t0\t0\t0\t0\t1\n");

        Reader tsvReader = new StringReader(tsvContents.toString());

        Iterable<TsvData> tsvDataStreamingParser = TsvDataStreamingParser.create(new TsvStreamingParser(tsvReader));

        Iterator<TsvData> iterator = tsvDataStreamingParser.iterator();

        TsvData line1 = iterator.next();
        TsvData line2 = iterator.next();

        assertThat(line1.getGeneId(), is("ENSMUSG00000030105"));
        assertThat(line1.getGeneName(), is("Arl8b"));

        assertThat(line1.getEverythingElse().keySet(), contains("g1","g2","g3","g4","g5"));
        assertThat(line1.getEverythingElse().values(), contains(1d,2d,3d,4d,-0.00248510654802851));

        assertThat(line2.getGeneId(), is("ENSG00000127720"));
        assertThat(line2.getGeneName(), is("METTL25"));

        assertThat(line2.getEverythingElse().keySet(), contains("g1","g2","g3","g4","g5"));
        assertThat(line2.getEverythingElse().values(), contains(0d,0d,0d,0d,1d));

    }

}
