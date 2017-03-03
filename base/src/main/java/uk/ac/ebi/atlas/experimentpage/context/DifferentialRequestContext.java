package uk.ac.ebi.atlas.experimentpage.context;

import com.google.common.base.MoreObjects;
import uk.ac.ebi.atlas.model.experiment.differential.Contrast;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.model.experiment.differential.Regulation;
import uk.ac.ebi.atlas.profiles.differential.DifferentialProfileStreamOptions;
import uk.ac.ebi.atlas.web.DifferentialRequestPreferences;

public class DifferentialRequestContext<E extends DifferentialExperiment, Preferences extends
        DifferentialRequestPreferences> extends RequestContext<Contrast,E, Preferences> implements
        DifferentialProfileStreamOptions {

    public DifferentialRequestContext(Preferences requestPreferences, E experiment) {
        super(requestPreferences, experiment);
    }

    @Override
    public String displayNameForColumn(Contrast contrast) {
        return contrast.getDisplayName();
    }

    public E getExperiment(){
        return experiment;
    }

    public Regulation getRegulation() {
        return requestPreferences.getRegulation();
    }

    @Override
    public double getPValueCutOff() {
        return requestPreferences.getCutoff();
    }

    @Override
    public double getFoldChangeCutOff() {
        return requestPreferences.getFoldChangeCutOff();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this.getClass())
                .addValue(super.toString())
                .add("regulation", getRegulation())
                .toString();
    }
}
