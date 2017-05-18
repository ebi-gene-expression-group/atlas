package uk.ac.ebi.atlas.web;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableSet;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.Set;

public abstract class ExperimentPageRequestPreferences extends SearchRequest {

    public static final int DEFAULT_NUMBER_OF_RANKED_GENES = 50;

    private static final int HEATMAP_SIZE_MIN = 0;

    private static final int HEATMAP_SIZE_MAX = 1000;

    private double cutoff = getDefaultCutoff();

    private String serializedFilterFactors;

    private String queryFactorType;

    private Set<String> queryFactorValues = Collections.emptySet();

    @NotNull
    @Range(min = HEATMAP_SIZE_MIN, max = HEATMAP_SIZE_MAX)
    private Integer heatmapMatrixSize = DEFAULT_NUMBER_OF_RANKED_GENES;

    private boolean specific = true;

    private boolean displayLevels;

    private boolean displayGeneDistribution;

    private String rootContext;

    protected ExperimentPageRequestPreferences() {
    }

    Set<String> selectedColumnIds = Collections.emptySet();

    @SuppressWarnings("unused")
    public void setSelectedColumnIds(Set<String> selectedColumnIds){
        this.selectedColumnIds = (selectedColumnIds == null) ? Collections.<String>emptySet() : ImmutableSet.copyOf(selectedColumnIds);
    }

    public Set<String> getSelectedColumnIds(){
        return selectedColumnIds;
    }

    public boolean isDisplayGeneDistribution() {
        return displayGeneDistribution;
    }

    public void setDisplayGeneDistribution(boolean displayGeneDistribution) {
        this.displayGeneDistribution = displayGeneDistribution;
    }

    public Integer getHeatmapMatrixSize() {
        return this.heatmapMatrixSize;
    }

    public void setHeatmapMatrixSize(Integer heatmapMatrixSize) {
        if (heatmapMatrixSize != null) {
            this.heatmapMatrixSize = heatmapMatrixSize;
        }
    }

    public Double getCutoff() {
        return this.cutoff;
    }

    //must be public because the jsp needs to access it
    public abstract double getDefaultCutoff();

    public void setCutoff(Double cutoff) {
        // handle no value case, eg: when textbox is left empty
        if (cutoff != null) {
            this.cutoff = cutoff;
        }
    }

    public boolean isSpecific() {
        return this.specific;
    }

    public void setSpecific(boolean specific) {
        this.specific = specific;
    }

    public boolean getDisplayLevels() {
        return displayLevels;
    }

    public void setDisplayLevels(boolean displayLevels) {
        this.displayLevels = displayLevels;
    }

    public String getRootContext() {
        return rootContext;
    }

    public void setRootContext(String rootContext) {
        this.rootContext = rootContext;
    }

    public String toString() {
        return MoreObjects.toStringHelper(this.getClass())
                .addValue(super.toString())
                .add("queryFactorType", queryFactorType)
                .add("cutoff", cutoff)
                .add("serializedFilterFactors", serializedFilterFactors)
                .add("specific", specific)
                .toString();
    }

}
