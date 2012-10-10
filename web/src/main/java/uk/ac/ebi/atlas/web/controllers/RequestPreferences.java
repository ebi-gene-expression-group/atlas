package uk.ac.ebi.atlas.web.controllers;

import org.hibernate.validator.constraints.Range;
import org.springframework.context.annotation.Scope;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Scope("prototype")
public class RequestPreferences {

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

    private Set<String> organismParts;

    public Set<String> getOrganismParts(){
        return organismParts;
    }

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

    public void setOrganismParts(Set<String> organismParts) {
        this.organismParts = organismParts;
    }
}
