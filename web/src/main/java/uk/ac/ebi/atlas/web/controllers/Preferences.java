package uk.ac.ebi.atlas.web.controllers;

import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class Preferences {

    private static final int DEFAULT_NUMBER_OF_TOP_EXPRESSIONS_TO_BE_HIGHLIGHTED = 10;
    private static final int DEFAULT_RANKING_SIZE = 100;
    private static final double DEFAULT_CUTOFF = 0d;

    @Range(min = 0, max = 1000)
    private Integer heatmapMatrixSize = DEFAULT_NUMBER_OF_TOP_EXPRESSIONS_TO_BE_HIGHLIGHTED;

    @NotNull
    @Range(min = 1, max = 1000)
    private Integer rankingSize = DEFAULT_RANKING_SIZE;

    @NotNull
    @Min(0)
    private Double cutoff = DEFAULT_CUTOFF;


    public Integer getHeatmapMatrixSize() {
        return this.heatmapMatrixSize;
    }

    public void setHeatmapMatrixSize(Integer heatmapMatrixSize) {
        this.heatmapMatrixSize = heatmapMatrixSize;
    }

    public Double getCutoff() {
        return this.cutoff;
    }

    public void setCutoff(Double cutoff) {
        this.cutoff = cutoff;
    }

    public Integer getRankingSize() {
        return this.rankingSize;
    }

    public void setRankingSize(Integer rankingSize) {
        this.rankingSize = rankingSize;
    }
}
