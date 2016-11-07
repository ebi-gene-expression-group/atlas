package uk.ac.ebi.atlas.experimentpage.context;

import com.google.common.base.Objects;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.model.differential.DifferentialExperiment;

import javax.inject.Named;

@Named
@Scope("request")
public class RnaSeqRequestContext extends DifferentialRequestContext<DifferentialExperiment> {

    public RnaSeqRequestContext() {
    }

    @Override
    public String toString(){
        return Objects.toStringHelper(getClass())
                .addValue(super.toString()).toString();
    }

}