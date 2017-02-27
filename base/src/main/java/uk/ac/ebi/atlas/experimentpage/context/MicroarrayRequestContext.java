package uk.ac.ebi.atlas.experimentpage.context;

import com.google.common.base.MoreObjects;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayExperiment;
import uk.ac.ebi.atlas.profiles.differential.microarray.MicroarrayProfileStreamOptions;
import uk.ac.ebi.atlas.web.MicroarrayRequestPreferences;

import javax.inject.Named;
import java.util.Collections;

@Named
@Scope("request")
public class MicroarrayRequestContext extends DifferentialRequestContext<MicroarrayExperiment, MicroarrayRequestPreferences> implements
        MicroarrayProfileStreamOptions {

    @Override
    public Iterable<String> getArrayDesignAccessions() {
        String maybeChosenArrayDesign = getRequestPreferences().getArrayDesignAccession();
        return StringUtils.isNotBlank(maybeChosenArrayDesign) ? Collections.singleton(maybeChosenArrayDesign) :
        getExperiment().getArrayDesignAccessions();
    }

    @Override
    public String toString(){
        return MoreObjects.toStringHelper(getClass())
                .addValue(super.toString())
                .add("arrayDesignAccessions", getArrayDesignAccessions()).toString();
    }

}