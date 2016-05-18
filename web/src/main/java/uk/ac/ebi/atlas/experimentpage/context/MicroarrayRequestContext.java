
package uk.ac.ebi.atlas.experimentpage.context;

import com.google.common.base.Objects;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayExperiment;
import uk.ac.ebi.atlas.profiles.differential.microarray.MicroarrayProfileStreamOptions;

import javax.inject.Named;

@Named
@Scope("request")
public class MicroarrayRequestContext extends DifferentialRequestContext<MicroarrayExperiment> implements MicroarrayProfileStreamOptions {

    private String arrayDesignAccession;

    public void setArrayDesignAccession(String arrayDesignAccession) {
        this.arrayDesignAccession = arrayDesignAccession;
    }

    public String getArrayDesignAccession() {
        return arrayDesignAccession;
    }

    @Override
    public Iterable<String> getArrayDesignAccessions() {
        return getExperiment().getArrayDesignAccessions();
    }

    @Override
    public String toString(){
        return Objects.toStringHelper(getClass())
                .addValue(super.toString())
                .add("arrayDesignAccession", arrayDesignAccession).toString();
    }

}