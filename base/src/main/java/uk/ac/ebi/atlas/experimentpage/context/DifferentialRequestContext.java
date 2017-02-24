package uk.ac.ebi.atlas.experimentpage.context;

import com.google.common.base.MoreObjects;
import uk.ac.ebi.atlas.model.experiment.differential.Contrast;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.model.experiment.differential.Regulation;
import uk.ac.ebi.atlas.profiles.differential.DifferentialProfileStreamOptions;
import uk.ac.ebi.atlas.web.DifferentialRequestPreferences;

public class DifferentialRequestContext<E extends DifferentialExperiment> extends RequestContext<Contrast, DifferentialRequestPreferences> implements DifferentialProfileStreamOptions {

    private E experiment;

    public E getExperiment() {
        return experiment;
    }

    void setExperiment(E experiment) {
        this.experiment = experiment;
    }

    public Regulation getRegulation() {
        return getRequestPreferences().getRegulation();
    }

    @Override
    public String getExperimentAccession() {
        return experiment.getAccession();
    }

    @Override
    public double getPValueCutOff() {
        return getRequestPreferences().getCutoff();
    }

    @Override
    public double getFoldChangeCutOff() {
        return getRequestPreferences().getFoldChangeCutOff();
    }

    @Override
    protected void setRequestPreferences(DifferentialRequestPreferences requestPreferences) {
        super.setRequestPreferences(requestPreferences);
    }

    @Override
    protected DifferentialRequestPreferences getRequestPreferences() {
        return super.getRequestPreferences();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this.getClass())
                .addValue(super.toString())
                .add("regulation", getRegulation())
                .add("experiment", getExperiment())
                .toString();
    }

}
