package uk.ac.ebi.atlas.web;

import com.google.common.base.Strings;
import com.google.common.collect.Sets;
import org.hibernate.validator.constraints.Range;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.utils.NumberUtils;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Set;
import java.util.SortedSet;
import java.util.regex.Pattern;

@Scope("prototype")
public class RequestPreferences {

    private static final int DEFAULT_NUMBER_OF_TOP_RANKED_GENES = 50;
    private static final double DEFAULT_CUTOFF = 0.5d;
    private static final Pattern commaOrSpaceSeparatorPattern = Pattern.compile("\\s*(,+|\\s)+\\s*");

    @NotNull
    @Range(min = 0, max = 1000)
    private Integer heatmapMatrixSize = DEFAULT_NUMBER_OF_TOP_RANKED_GENES;

    @NotNull
    @Min(0)
    private Double cutoff = DEFAULT_CUTOFF;

    private SortedSet<String> organismParts;

    private String geneIDsString;

    private Set<String> geneIDs;

    private boolean displayLevels;

    public SortedSet<String> getOrganismParts() {
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
        this.cutoff = NumberUtils.round(cutoff);
    }

    public void setDisplayLevels(boolean displayLevels){
        this.displayLevels = displayLevels;
    }

    public boolean getDisplayLevels(){
        return displayLevels;
    }

    public void setOrganismParts(SortedSet<String> organismParts) {
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
