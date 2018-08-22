package uk.ac.ebi.atlas.experimentpage.context;

import com.google.common.base.MoreObjects;
import org.apache.commons.lang3.StringUtils;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayExperiment;
import uk.ac.ebi.atlas.profiles.differential.microarray.MicroarrayProfileStreamOptions;
import uk.ac.ebi.atlas.web.MicroarrayRequestPreferences;

import java.util.Collections;

public class MicroarrayRequestContext
        extends DifferentialRequestContext<MicroarrayExperiment, MicroarrayRequestPreferences>
        implements MicroarrayProfileStreamOptions {

    public MicroarrayRequestContext(MicroarrayRequestPreferences requestPreferences, MicroarrayExperiment experiment) {
        super(requestPreferences, experiment);

    }

    @Override
    public Iterable<String> getArrayDesignAccessions() {
        String maybeChosenArrayDesign = requestPreferences.getArrayDesignAccession();
        return StringUtils.isNotBlank(maybeChosenArrayDesign) ?
                Collections.singleton(maybeChosenArrayDesign) :
                experiment.getArrayDesignAccessions();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(getClass())
                .addValue(super.toString())
                .add("arrayDesignAccessions", getArrayDesignAccessions()).toString();
    }

}
