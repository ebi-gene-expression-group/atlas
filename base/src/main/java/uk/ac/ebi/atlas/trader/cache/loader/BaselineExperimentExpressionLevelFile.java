package uk.ac.ebi.atlas.trader.cache.loader;

public interface BaselineExperimentExpressionLevelFile {
    String[] readOrderedAssayGroupIds(String experimentAccession);
}
