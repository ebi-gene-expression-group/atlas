package uk.ac.ebi.atlas.web;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableSet;
import org.hibernate.validator.constraints.Range;
import uk.ac.ebi.atlas.model.ExpressionUnit;
import uk.ac.ebi.atlas.profiles.baseline.BaselineExpressionLevelRounder;

import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.Set;

public abstract class ExperimentPageRequestPreferences<Unit extends ExpressionUnit> extends SearchRequest {

    public static final double nonZeroButVerySmallCutoffValue = 10e-100d;
    public static final int DEFAULT_NUMBER_OF_RANKED_GENES = 50;

    private static final int HEATMAP_SIZE_MIN = 0;
    private static final int HEATMAP_SIZE_MAX = 1000;

    private double cutoff = getDefaultCutoff();
    private Set<String> selectedColumnIds = Collections.emptySet();
    @NotNull
    @Range(min = HEATMAP_SIZE_MIN, max = HEATMAP_SIZE_MAX)
    private Integer heatmapMatrixSize = DEFAULT_NUMBER_OF_RANKED_GENES;
    private boolean specific = true;

    protected ExperimentPageRequestPreferences() {
    }

    @SuppressWarnings("unused")
    public void setSelectedColumnIds(Set<String> selectedColumnIds){
        this.selectedColumnIds = (selectedColumnIds == null) ? Collections.<String>emptySet() : ImmutableSet.copyOf(selectedColumnIds);
    }

    public Set<String> getSelectedColumnIds(){
        return selectedColumnIds;
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
        if (cutoff != null) {
            this.cutoff = BaselineExpressionLevelRounder.round(cutoff);
        }
    }

    public boolean isSpecific() {
        return this.specific;
    }

    public void setSpecific(boolean specific) {
        this.specific = specific;
    }

    public String toString() {
        return MoreObjects.toStringHelper(this.getClass())
                .addValue(super.toString())
                .add("cutoff", cutoff)
                .add("specific", specific)
                .toString();
    }

    public abstract Unit getUnit();

    /*
    Used for equality of cache keys.
    Currently:
    - Kryo serialized files
    - Histograms
    This combined with experiment accession should 1-1 map to a data file. The design is unclear and a bit of a wart. Sorry. :)

    When we serialize files, calculate histograms, or request everything for download, we mean "everything but zero"
    On the other hand we also offer an option in the UI to explicitly ask for zeros.
    We can't afford to kryo serialize these - the format is only better because we take advantage of data sparsity.
    If someone chooses to ask for zeros we need to go through the original text file.
     */
    public String serializationShortString(){
        return getClass().getSimpleName()+getCutoff().equals(0.0d);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExperimentPageRequestPreferences that = (ExperimentPageRequestPreferences) o;
        return serializationShortString().equals(that.serializationShortString());
    }

    @Override
    public int hashCode() {
        return serializationShortString().hashCode();
    }

}
