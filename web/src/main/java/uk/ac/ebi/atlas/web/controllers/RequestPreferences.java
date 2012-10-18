package uk.ac.ebi.atlas.web.controllers;

import com.google.common.base.Strings;
import com.google.common.collect.Sets;
import org.hibernate.validator.constraints.Range;
import org.springframework.context.annotation.Scope;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Set;
import java.util.regex.Pattern;

@Scope("prototype")
public class RequestPreferences {

    private static final int DEFAULT_NUMBER_OF_TOP_EXPRESSIONS_TO_BE_HIGHLIGHTED = 100;
    private static final int DEFAULT_RANKING_SIZE = 100;
    private static final double DEFAULT_CUTOFF = 0d;
    private static final Pattern commaOrSpaceSeparatorPattern = Pattern.compile("\\s*(,+|\\s)+\\s*");

    @Range(min = 0, max = 1000)
    private Integer heatmapMatrixSize = DEFAULT_NUMBER_OF_TOP_EXPRESSIONS_TO_BE_HIGHLIGHTED;

    @NotNull
    @Range(min = 1, max = 1000)
    private Integer rankingSize = DEFAULT_RANKING_SIZE;

    @NotNull
    @Min(0)
    private Double cutoff = DEFAULT_CUTOFF;

    private Set<String> organismParts;

    private String geneIDsString;

    private Set<String> geneIDs;

    public Set<String> getOrganismParts() {
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

    public String getGeneIDsString() {
        return this.geneIDsString;
    }

    public void setGeneIDsString(String geneIDsString) {
        this.geneIDsString = geneIDsString;
        updateGeneIDs();
    }

    private void updateGeneIDs() {
        if (!Strings.isNullOrEmpty(geneIDsString)) {
            this.geneIDs = Sets.newHashSet(commaOrSpaceSeparatorPattern.split(geneIDsString));
        }
    }

    public Set<String> getGeneIDs() {
        return geneIDs;
    }
}
