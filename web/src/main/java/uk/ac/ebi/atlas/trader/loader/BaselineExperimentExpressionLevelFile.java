package uk.ac.ebi.atlas.trader.loader;

public interface BaselineExperimentExpressionLevelFile {
    String[] readOrderedAssayGroupIds(String experimentAccession);
}
