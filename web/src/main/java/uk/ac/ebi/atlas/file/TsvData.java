package uk.ac.ebi.atlas.file;

import com.google.common.base.Objects;
import com.google.common.collect.ImmutableMap;

import java.util.Map;

/*
 * Generic representation of data in a TSV file, ie: gene ID, gene name, map of everything else
 * This is the format that all Array Atlas pipeline TSVs follow
 */
public class TsvData {

    public static final String GENE_ID = "Gene ID";
    public static final String GENE_NAME = "Gene Name";

    private String geneId;
    private String geneName;
    private ImmutableMap<String, Double> everythingElse;

    public TsvData(Map<String, String> line) {
        this.geneId = line.get(GENE_ID);
        this.geneName = line.get(GENE_NAME);

        ImmutableMap.Builder<String, Double> mapBuilder = ImmutableMap.builder();

        for (Map.Entry<String,String> entry: line.entrySet()) {
            String key = entry.getKey();
            if (!(key.equalsIgnoreCase(GENE_ID) || key.equalsIgnoreCase(GENE_NAME))) {
                Double value = Double.parseDouble(entry.getValue());
                mapBuilder.put(key, value);
            }
        }

        everythingElse = mapBuilder.build();
    }

    public String getGeneId() {
        return geneId;
    }

    public String getGeneName() {
        return geneName;
    }

    public ImmutableMap<String, Double> getEverythingElse() {
        return everythingElse;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TsvData tsvData = (TsvData) o;

        return everythingElse.equals(tsvData.everythingElse) && geneId.equals(tsvData.geneId) && geneName.equals(tsvData.geneName);

    }

    @Override
    public int hashCode() {
        return Objects.hashCode(geneId, geneName, everythingElse);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("geneId", geneId)
                .add("geneName", geneName)
                .add("everythingElse", everythingElse)
                .toString();
    }
}