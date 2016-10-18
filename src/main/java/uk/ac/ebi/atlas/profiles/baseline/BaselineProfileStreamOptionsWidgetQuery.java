package uk.ac.ebi.atlas.profiles.baseline;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSetMultimap;
import org.apache.commons.lang3.StringUtils;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.baseline.Factor;
import uk.ac.ebi.atlas.web.BaselineRequestPreferences;
import uk.ac.ebi.atlas.web.ExperimentPageRequestPreferences;

import java.util.Collections;
import java.util.Set;

/*
 * This will load baseline profiles in the same way as when baseline profiles are loaded
 * via the /widgets/heatmap/referenceExperiment controller.
 * This is used by BioEntityPageController to check the number of gene profiles that
 * will be loaded by the widget.
 */
//TODO delete no longer needed and went out of use after Alfonso's change - July 2016
public class BaselineProfileStreamOptionsWidgetQuery implements BaselineProfileStreamOptions {

    private final BaselineExperiment baselineExperiment;
    private final Set<Factor> selectedFilterFactors;

    public BaselineProfileStreamOptionsWidgetQuery(BaselineExperiment baselineExperiment, String species) {
        this.baselineExperiment = baselineExperiment;
        String mappedSpecies = baselineExperiment.getSpecies().mappedName;
        this.selectedFilterFactors = StringUtils.isBlank(mappedSpecies) ? Collections.<Factor>emptySet() : ImmutableSet.of(new Factor("ORGANISM", mappedSpecies));
    }

    @Override
    public boolean isSpecific() {
        return true;
    }

    @Override
    public Set<Factor> getSelectedQueryFactors() {
        return Collections.emptySet();
    }

    @Override
    public Set<Factor> getAllQueryFactors() {
        return baselineExperiment.getExperimentalFactors().getComplementFactors(selectedFilterFactors);
    }

    @Override
    public String getExperimentAccession() {
        return baselineExperiment.getAccession();
    }

    @Override
    public double getCutoff() {
        return BaselineRequestPreferences.DEFAULT_CUTOFF;
    }

    @Override
    public String getQueryFactorType() {
        return baselineExperiment.getExperimentalFactors().getDefaultQueryFactorType();
    }

    @Override
    public Set<Factor> getSelectedFilterFactors() {
        return selectedFilterFactors;
    }

    @Override
    public Double getThresholdForPremium() {
        return null;
    }

    @Override
    public Double getFractionForPremium() {
        return null;
    }

    @Override
    public Integer getHeatmapMatrixSize() {
        return ExperimentPageRequestPreferences.DEFAULT_NUMBER_OF_RANKED_GENES;
    }

}
