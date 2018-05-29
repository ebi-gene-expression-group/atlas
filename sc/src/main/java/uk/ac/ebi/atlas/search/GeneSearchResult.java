package uk.ac.ebi.atlas.search;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class GeneSearchResult {

    @AutoValue
    public static abstract class Dto {
        public abstract String experimentAccession();
        public abstract String geneId();
        public abstract String cellId();
        public abstract Integer k();
        public abstract Integer clusterId();

        public static GeneSearchResult.Dto create(String experimentAccession, String geneId, String cellId, Integer k, Integer clusterId) {
            return new AutoValue_GeneSearchResult_Dto(experimentAccession, geneId, cellId, k, clusterId);
        }
    }

    public abstract String experimentAccession();
    public abstract String geneId();
    public abstract String cellId();
    public abstract Integer k();
    public abstract Integer clusterId();

    public static GeneSearchResult create(String experimentAccession, String geneId, String cellId, Integer k, Integer clusterId) {
        return new AutoValue_GeneSearchResult(experimentAccession, geneId, cellId, k, clusterId);
    }

}
