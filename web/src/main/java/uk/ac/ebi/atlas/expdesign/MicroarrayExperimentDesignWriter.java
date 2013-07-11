package uk.ac.ebi.atlas.expdesign;

import com.google.common.collect.Lists;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.model.ExperimentDesign;

import javax.inject.Named;
import java.util.List;

@Named
@Scope("prototype")
public class MicroarrayExperimentDesignWriter extends ExperimentDesignWriter {

    private MicroarrayMageTabParser parser;


    public MicroarrayExperimentDesignWriter(MicroarrayMageTabParser parser) {
        this.parser = parser;
    }

    @Override
    protected String[] composeHeader(ExperimentDesign experimentDesign) {
        List<String> result = Lists.newArrayList("Assay", "Array");
        for (String characteristic : experimentDesign.getSampleHeaders()) {
            result.add("Sample Characteristics[" + characteristic + "]");
        }
        for (String factor : experimentDesign.getFactorHeaders()) {
            result.add("Factor Values[" + factor + "]");
        }
        return result.toArray(new String[result.size()]);
    }

    @Override
    protected MageTabParser getMageTabParser() {
        return parser;
    }
}
