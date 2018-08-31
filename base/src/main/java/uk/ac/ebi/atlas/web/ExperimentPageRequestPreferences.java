package uk.ac.ebi.atlas.web;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableSet;
import uk.ac.ebi.atlas.model.ExpressionUnit;
import uk.ac.ebi.atlas.search.SemanticQuery;

import java.util.Set;

public abstract class ExperimentPageRequestPreferences<U extends ExpressionUnit> {
    public static final double VERY_SMALL_NON_ZERO_VALUE = Double.MIN_VALUE;
    public static final int DEFAULT_NUMBER_OF_RANKED_GENES = 50;

    private int heatmapMatrixSize = DEFAULT_NUMBER_OF_RANKED_GENES;
    private SemanticQuery geneQuery = getDefaultGeneQuery();
    private double cutoff = getDefaultCutoff();
    private Set<String> selectedColumnIds = ImmutableSet.of();
    private boolean specific = true;

    protected ExperimentPageRequestPreferences() {
    }

    public void setSelectedColumnIds(Set<String> selectedColumnIds) {
        if (selectedColumnIds != null) {
            this.selectedColumnIds = ImmutableSet.copyOf(selectedColumnIds);
        }
    }

    public Set<String> getSelectedColumnIds() {
        return selectedColumnIds;
    }

    public void setHeatmapMatrixSize(int heatmapMatrixSize) {
        this.heatmapMatrixSize = heatmapMatrixSize;
    }

    public int getHeatmapMatrixSize() {
        return this.heatmapMatrixSize;
    }

    public void setGeneQuery(SemanticQuery geneQuery) {
        this.geneQuery = geneQuery;
    }

    public SemanticQuery getGeneQuery() {
        return this.geneQuery;
    }

    protected SemanticQuery getDefaultGeneQuery() {
        return SemanticQuery.create();
    }

    public void setCutoff(double cutoff) {
        this.cutoff = cutoff;
    }

    public double getCutoff() {
        return this.cutoff;
    }

    public abstract double getDefaultCutoff();

    public void setSpecific(boolean specific) {
        this.specific = specific;
    }

    public boolean isSpecific() {
        return this.specific;
    }

    public String toString() {
        return MoreObjects.toStringHelper(this.getClass())
                .addValue(super.toString())
                .add("cutoff", cutoff)
                .add("specific", specific)
                .toString();
    }

    public abstract U getUnit();
}
