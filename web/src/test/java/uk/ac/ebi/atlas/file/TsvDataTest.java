package uk.ac.ebi.atlas.file;

import com.google.common.collect.ImmutableMap;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.junit.Assert.assertThat;

public class TsvDataTest {

    public static final String GENE_ID = "ENSMUSG00000030105";
    public static final String GENE_NAME = "Arl8b";

    @Test
    public void basicData() {
        ImmutableMap<String, String> line = ImmutableMap.of("Gene ID", GENE_ID, "Gene Name", GENE_NAME, "g1_g2.p-value", "1", "g1_g2.log2foldchange", "-0.00248510654802851");

        TsvData tsvData = new TsvData(line);

        assertThat(tsvData.getGeneId(), is(GENE_ID));
        assertThat(tsvData.getGeneName(), is(GENE_NAME));

        assertThat(tsvData.getEverythingElse().keySet(), contains("g1_g2.p-value", "g1_g2.log2foldchange"));
        assertThat(tsvData.getEverythingElse().values(), contains("1", "-0.00248510654802851"));
    }


    @Test
    public void orderOfLargerDatasetIsCorrect() {
        String[] headers = {"g1","g2","g3","g4","g5","g6","g7","g8","g9","g10","a1","a2","a3","a4","a5"};
        String[] values =  {"1","2","3","4","5","6","7","8","9","10","11","12","13","14.0","15.1"};

        ImmutableMap.Builder<String, String> builder = ImmutableMap.builder();
        builder.put("Gene ID", GENE_ID);
        builder.put("Gene Name", GENE_NAME);

        for (int i = 0; i < headers.length; i++) {
            builder.put(headers[i], values[i]);
        }

        TsvData tsvData = new TsvData(builder.build());

        assertThat(tsvData.getGeneId(), is(GENE_ID));
        assertThat(tsvData.getGeneName(), is(GENE_NAME));

        assertThat(tsvData.getEverythingElse().keySet(), contains(headers));
        assertThat(tsvData.getEverythingElse().values(), contains(values));
    }

}
