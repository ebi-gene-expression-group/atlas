package uk.ac.ebi.atlas.web.controllers;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;

public class Preferences {

    @DecimalMax("10")
    @Min(1)
    private Integer heatmapMatrixSize;

    @DecimalMax("1000")
    @Min(0)
    private Integer rankingSize;

    @DecimalMin("0")
    private Integer cutoff;


    public Integer getHeatmapMatrixSize() {
        return heatmapMatrixSize;
    }

    public void setHeatmapMatrixSize(Integer heatmapMatrixSize) {
        this.heatmapMatrixSize = heatmapMatrixSize;
    }

    public Integer getCutoff() {
        return cutoff;
    }

    public void setCutoff(Integer cutoff) {
        this.cutoff = cutoff;
    }

    public Integer getRankingSize() {
        return rankingSize;
    }

    public void setRankingSize(Integer rankingSize) {
        this.rankingSize = rankingSize;
    }
}
